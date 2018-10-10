package ru.otus.connectionPool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;

/**
 * @author sergey
 * created on 08.10.18.
 */
public class Demo {
    private static final String URL = "jdbc:h2:mem:testDB;DB_CLOSE_DELAY=-1";
    private final HikariDataSource dataSource;

    public static void main(String[] args) throws SQLException {
        Demo demo = new Demo();
        demo.createTable();
        demo.insertRecords();
        demo.useConnectionPool();
    }

    public Demo() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setConnectionTimeout(3000); //ms
        config.setIdleTimeout(60000); //ms
        config.setMaxLifetime(600000);//ms
        config.setAutoCommit(false);
        config.setMinimumIdle(5);
        config.setMaximumPoolSize(10);
        config.setPoolName("DemoHiPool");
        config.setRegisterMbeans(true);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    private void createTable() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pst = connection.prepareStatement("create table test(id int, name varchar(50))")) {
            pst.executeUpdate();
        }
    }

    private void insertRecords() throws SQLException {
        try (Connection connection = dataSource.getConnection();
                PreparedStatement pst = connection.prepareStatement("insert into test(id, name) values (?, ?)")) {
            Savepoint savePoint = connection.setSavepoint("savePointName");
            try {
                int rowCount = 0;
                for (int idx = 0; idx < 100; idx++) {
                    pst.setInt(1, idx);
                    pst.setString(2, "NameValue_" + idx);
                    rowCount += pst.executeUpdate();
                }
                connection.commit();
                System.out.println("inserted rowCount:" + rowCount);
            } catch (SQLException ex) {
                connection.rollback(savePoint);
                System.out.println(ex.getMessage());
            }
        }
    }

    private void useConnectionPool() {
        new Thread(this::doSelect).start();
        new Thread(this::doSelect).start();
        new Thread(this::doSelect).start();
    }

    private void doSelect() {
        try (Connection connection = dataSource.getConnection();
            PreparedStatement pst = connection.prepareStatement("select count(*) as counter from test")) {
            while(true) {
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        System.out.println(Thread.currentThread().getName() + "  " + rs.getString("counter"));
                    }
                }
                Thread.sleep(3_000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
