# springMvc-Mybatis
Spirng Mvc + Mybatis 学习

问题记录：
了解 log4j 配置文件的加载
sqlSession.commit(); 数据库存储过程 commit close rollback 等

知识点记录：
mybatis insert 返回 id：
````
<insert id="insertUser" parameterType="com.suchaos.po.User">
    <selectKey keyProperty="id" order="AFTER" resultType="java.lang.Integer">
        SELECT LAST_INSERT_ID()
    </selectKey>
    INSERT INTO user(username, password, gender, email, province, city, birthday)
    VALUE (#{username}, #{password}, #{gender}, #{email}, #{province}, #{city}, #{birthday, jdbcType=DATE})
</insert>

<insert id="insertUser" parameterType="com.suchaos.po.User" useGeneratedKeys="true" keyProperty="id">
    INSERT INTO user(username, password, gender, email, province, city, birthday)
    VALUE (#{username}, #{password}, #{gender}, #{email}, #{province}, #{city}, #{birthday, jdbcType=DATE})
</insert>
````
