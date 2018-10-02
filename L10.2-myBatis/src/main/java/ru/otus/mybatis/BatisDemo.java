package ru.otus.mybatis;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author sergey
 * created on 02.10.18.
 */
public class BatisDemo {
    private final SqlSessionFactory sqlSessionFactory;

    public BatisDemo() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory =  new SqlSessionFactoryBuilder().build(inputStream);
    }

    public void start() {
        selectOne();
        System.out.println();
        selectAll();
        System.out.println();
        selectLike();
        System.out.println();
        selectForEach();
    }

    private void selectOne() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Test test = session.selectOne("testMapper.selectTestOne", 1);
            System.out.println("selected: " + test);
        }
    }

    private void selectAll() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, Integer> params = new HashMap<>();
            params.put("minId", 50);
            params.put("maxId", 55);
            List<Test> testList = session.selectList("testMapper.selectTestAll", params);
            System.out.println("selected: " + testList);
        }
    }

    private void selectLike() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Map<String, String> params = new HashMap<>();
            params.put("nameParam", "%2%");
            List<Test> testList = session.selectList("testMapper.selectTestLike", params);
            System.out.println("selected like with nameParam: " + testList);

            testList = session.selectList("testMapper.selectTestLike");
            System.out.println("selected like without nameParam: " + testList);
        }
    }

    private void selectForEach() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            List<Integer> params = Arrays.asList(1,2,3,4,5);
            List<Test> testList = session.selectList("testMapper.selectTestForEach", params);
            System.out.println("selectedForEach: " + testList);
        }
    }


}
