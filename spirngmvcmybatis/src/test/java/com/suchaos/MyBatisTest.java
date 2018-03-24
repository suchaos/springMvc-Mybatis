package com.suchaos;

import com.suchaos.datasource.DataConnection;
import com.suchaos.po.User;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;

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
    public void TestIdeaGit() {
        System.out.println("使用 idea 提交至 github");
    }

    @After
    public void tearDown() {
        sqlSession.close();
    }
}
