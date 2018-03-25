package com.suchaos;

import com.suchaos.datasource.DataConnection;
import com.suchaos.mybatis.CartObjectFactory;
import com.suchaos.po.*;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyBatisTest {
    private SqlSession sqlSession;

    @Before
    public void setUp() throws IOException {
        DataConnection dataConn = new DataConnection();
        sqlSession = dataConn.getSqlSession();
    }

    @Test
    public void TestSelect() {
        User user = sqlSession.selectOne("test.findUserById", 1);
        Assert.assertEquals("张三", user.getUsername());
        System.out.println("姓名:" + user.getUsername());
        System.out.println("性别:" + user.getGender());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("生日:" + sdf.format(user.getBirthday()));
        System.out.println("所在地:" + user.getProvince() + user.getCity());
    }

    @Test
    public void TestInsert() throws ParseException {
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
        //Assert.assertEquals(12, id);
    }

    @Test
    public void TestFuzzySearch() {
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

    @Test
    public void TestObjectFactory() {
        CartObjectFactory e = new CartObjectFactory();
        List constructorAryTypes = new ArrayList();
        constructorAryTypes.add(int.class);
        constructorAryTypes.add(String.class);
        constructorAryTypes.add(int.class);
        constructorAryTypes.add(double.class);
        constructorAryTypes.add(double.class);

        List construcArgs = new ArrayList();
        construcArgs.add(1);
        construcArgs.add("杯子");
        construcArgs.add(12);
        construcArgs.add(5.0);
        construcArgs.add(0.0);

        ShoppingCart shoppingCart = (ShoppingCart) e.create(ShoppingCart.class, constructorAryTypes, construcArgs);
        System.out.println(shoppingCart.getTotalAmount());
    }

    @Test
    public void testFindUserList() {
        // 创建包装对象，设置查询条件
        UserQueryInfo userQueryInfo = new UserQueryInfo();
        UserInstance userInstance = new UserInstance();
        userInstance.setGender("男");
        userInstance.setUsername("张");
        userQueryInfo.setUserInstance(userInstance);

        // 调用 userMapper 的方法
        List<UserInstance> userList = sqlSession.selectList("test.findUserList", userQueryInfo);
        for (UserInstance user : userList) {
            System.out.println(user.getId() + ":" + user.getUsername());
        }
    }

    @Test
    public void testBatchCustomer() {
        List<BatchCustomer> batchCustomerList = sqlSession.selectList("test.findBatchCustoemr");
        if (batchCustomerList != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (BatchCustomer batchCustomer : batchCustomerList) {
                System.out.println("卡号为" + batchCustomer.getAcno() + "的名为"
                        + batchCustomer.getUsername() + "的客户:\n于"
                        + sdf.format(batchCustomer.getCreatetime()) + "采购了批次号为"
                        + batchCustomer.getNumber() + "的一批理财产品   " + batchCustomer.getBatchId() + "   " + batchCustomer.getCusId());
            }
        }
    }

    @Test
    public void testBatchCustomerToMap() {
        List<BatchItem> batchItemList = sqlSession.selectList("test.findBatchCustomerToMap");
        if (batchItemList != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (BatchItem batchItem : batchItemList) {
                Customer customer = batchItem.getCustomer();
                System.out.println("卡号为" + customer.getAcno() + "的名为"
                        + customer.getUsername() + "的客户:\n于"
                        + sdf.format(batchItem.getCreatetime()) + "采购了批次号为"
                        + batchItem.getNumber() + "的一批理财产品   " + batchItem.getBatchId() + "   " + batchItem.getCusId());
            }
        }
    }

    @After
    public void tearDown() {
        sqlSession.close();
    }
}
