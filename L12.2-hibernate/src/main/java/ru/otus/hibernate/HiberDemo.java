package ru.otus.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.ReturningWork;
import ru.otus.hibernate.model.Person;
import ru.otus.hibernate.model.Phone;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sergey
 * created on 08.10.18.
 */

public class HiberDemo {
    private static final String URL = "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1";
    private final SessionFactory sessionFactory;

    public static void main(String[] args) {
        HiberDemo demo = new HiberDemo();

        demo.entityExample();
       // demo.leakageExample();
       // demo.fetchExample();
       // demo.JPQLexample();
       // demo.nativeExample();

    }

    private HiberDemo() {
        Configuration configuration = new Configuration()
             .setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
             .setProperty("hibernate.connection.driver_class", "org.h2.Driver")
             .setProperty("hibernate.connection.url", URL)
             .setProperty("hibernate.show_sql", "true")
             .setProperty("hibernate.hbm2ddl.auto", "create");

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties()).build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Phone.class)
                .getMetadataBuilder()
                .build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    private void entityExample() {
        try(Session session = sessionFactory.openSession()) {
            Person person = new Person();
            person.setName("Ivan");
            person.setNickName("Durak");
            person.setAddress("derv str");
            session.persist(person);
            System.out.println(person);

            Person selected = session.load(Person.class, person.getId());
            System.out.println("selected:" + selected);
            System.out.println(">>> updating >>>");

            Transaction transaction = session.getTransaction();
            transaction.begin();
            person.setAddress("moved street");
            transaction.commit();

            Person updated = session.load(Person.class, person.getId());
            System.out.println("updated:" + updated);

            session.detach(updated);

            System.out.println(">>> updating detached>>>");

            Transaction transactionDetached = session.getTransaction();
            transactionDetached.begin();
            updated.setAddress("moved street NOT CHANGED");
            transactionDetached.commit();

            Person notUpdated = session.load(Person.class, person.getId());
            System.out.println("notUpdated:" + notUpdated);
        }
    }

    private void leakageExample() {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            Person person = new Person();
            person.setName("Ivan");
            person.setNickName("Durak");
            person.setAddress("derv str");
            session.persist(person);
            System.out.println(person);

            transaction.commit();

            //session.detach(person);
            deepInIn(person);

            Person selected = session.load(Person.class, person.getId());
            System.out.println("selected:" + selected);
        }
    }

    //Далекая часть программы
    private void deepInIn(Person person) {
        Person jon = person;
        jon.setName("jon");
        System.out.println("jon:" + jon);
    }

    private void fetchExample() {
        long personId;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            Person person = new Person();
            person.setName("Ivan");
            person.setNickName("Durak");
            person.setAddress("derv str");

            List<Phone> listPhone = new ArrayList<>();
            for (int idx = 0; idx < 100; idx++) {
                listPhone.add(new Phone("+" + idx, person));
            }
            person.setPhones(listPhone);

            System.out.println("persist...");
            session.save(person);
            personId = person.getId();

            System.out.println("commit...");
            transaction.commit();
        }
        try (Session session = sessionFactory.openSession()) {
            Phone selectedPhone = session.load(Phone.class, 3L);
            System.out.println("selectedPhone:" + selectedPhone);

            Person selected = session.load(Person.class, personId);
            System.out.println("selected person:" + selected.getName());
          //  System.out.println(selected.getPhones());
        }
    }

    private void JPQLexample() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            Person person = new Person();
            person.setName("Ivan");
            person.setNickName("Durak");
            person.setAddress("derv str");

            List<Phone> listPhone = new ArrayList<>();
            for (int idx = 0; idx < 100; idx++) {
                listPhone.add(new Phone("+" + idx, person));
            }
            person.setPhones(listPhone);
            session.save(person);
            System.out.println("commit...");
            transaction.commit();
        }

        EntityManager entityManager = sessionFactory.createEntityManager();

        System.out.println("select phone list:");

        List<Phone> selectedPhones = entityManager.createQuery(
                "select p from Phone p where p.id > :paramId", Phone.class)
                .setParameter( "paramId", 90L)
                .getResultList();

        System.out.println(selectedPhones);


        Person person = entityManager
                .createNamedQuery( "get_person_by_name", Person.class )
                .setParameter( "name", "Ivan" )
                .getSingleResult();

        System.out.println("selected person:" + person.getNickName());


        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = builder.createQuery( Person.class );
        Root<Person> root = criteria.from(Person.class);
        criteria.select( root );
        criteria.where(builder.equal(root.get("name"), "Ivan"));

        Person personCriteria = entityManager.createQuery(criteria).getSingleResult();
        System.out.println("selected personCriteria:" + personCriteria.getNickName());
    }

    private void nativeExample() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.getTransaction();
            transaction.begin();

            Person person = new Person();
            person.setName("Ivan");
            person.setNickName("Durak");
            person.setAddress("derv str");

            List<Phone> listPhone = new ArrayList<>();
            for (int idx = 0; idx < 100; idx++) {
                listPhone.add(new Phone("+" + idx, person));
            }
            person.setPhones(listPhone);
            session.save(person);
            System.out.println("commit...");
            transaction.commit();
        }

        try (Session session = sessionFactory.openSession()) {
            String name = session.doReturningWork(connection -> {
                try (PreparedStatement ps = connection.prepareStatement("select name from person where id = ?")) {
                    ps.setLong(1,1L);
                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();
                        return rs.getString("name");
                    }
                }
            });
            System.out.println("sqL name:" + name);
        }

    }


}

