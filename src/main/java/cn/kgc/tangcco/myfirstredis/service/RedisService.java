package cn.kgc.tangcco.myfirstredis.service;


import cn.kgc.tangcco.myfirstredis.pojo.Student;

import java.util.Map;

/**
 * @program: myfirstredis
 * @description: TODO
 * @author: cuihao
 * @create: 2020-01-04 09:08
 * @version: V1.0
 **/
public interface RedisService {
    Map<String,Object> login(Student student, String ua);
}
