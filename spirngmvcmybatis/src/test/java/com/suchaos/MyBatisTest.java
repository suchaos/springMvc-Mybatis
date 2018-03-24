package com.suchaos;

import com.suchaos.datasource.DataConnection;
import com.suchaos.po.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyBatisTest {
    private SqlSession sqlSession;

    @Before
    public void setUp() throws IOException {
        DataConnection dataConn = new DataConnection();
        sqlSession = dataConn.getSqlSession();
    }

    @Test
    public void TestSelect() throws IOException {
        User user = sqlSession.selectOne("test.findUserById", 1);
        Assert.assertEquals("张三", user.getUsername());
        System.out.println("姓名:" + user.getUsername());
        System.out.println("性别:" + user.getGender());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("生日:" + sdf.format(user.getBirthday()));
        System.out.println("所在地:" + user.getProvince() + user.getCity());
    }

    @Test
    public void TestInsert() throws IOException, ParseException {
        User user = new User();
        user.setUsername("suchao");
        user.setGender("male");
        user.setPassword("5555");
        user.setEmail("5555@126.com");
        user.setProvince("222");
        user.setCity("22");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        user.setBirthday(sdf.parse("1994-05-22"));
        int id;
        sqlSession.insert("test.insertUser", user);
        id = user.getId();
        sqlSession.commit();
        sqlSession.close();
        Assert.assertEquals(12, id);
    }

    @Test
    public void TestFuzzySearch() throws IOException {
        List<User> userList = sqlSession.selectList("test.findUsersByUsername", "丽");
        for (User user : userList) {
            System.out.println("姓名:" + user.getUsername());
            System.out.println("性别:" + user.getGender());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("生日:" + sdf.format(user.getBirthday()));
            System.out.println("所在地:" + user.getProvince() + user.getCity());
        }
    }

    @Test
    public void TestDelete() {
        int flag;
        flag = sqlSession.delete("test.deleteUser", 10);
        sqlSession.commit();
        sqlSession.close();
        Assert.assertEquals(1, flag);
    }

    @Test
    public void TestUpdateName() {
        int flag;
        User user = new User();
        user.setId(11);
        user.setUsername("updateName");
        flag = sqlSession.update("test.updateUserName", user);
        sqlSession.commit();
        sqlSession.close();
        Assert.assertEquals(1, flag);
    }


    @Test
    public void TestIdeaGit() {
        System.out.println("使用 idea 提交至 github");
    }

    @After
    public void tearDown() {
        sqlSession.close();
    }
}
