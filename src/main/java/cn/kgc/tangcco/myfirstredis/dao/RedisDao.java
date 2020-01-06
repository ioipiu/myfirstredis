package cn.kgc.tangcco.myfirstredis.dao;

import cn.kgc.tangcco.myfirstredis.pojo.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RedisDao {
    Student getStudent(@Param("stu") Student student);
}
