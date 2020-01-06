package cn.kgc.tangcco.myfirstredis.service.impl;

import cn.kgc.tangcco.myfirstredis.dao.RedisDao;
import cn.kgc.tangcco.myfirstredis.pojo.Student;
import cn.kgc.tangcco.myfirstredis.service.RedisService;
import cn.kgc.tangcco.myfirstredis.utils.RedisUtils;
import cn.kgc.tangcco.myfirstredis.utils.UserAgentUtils;
import com.alibaba.fastjson.JSON;
import cz.mallat.uasparser.UserAgentInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @program: myfirstredis
 * @description: TODO
 * @author: cuihao
 * @create: 2020-01-04 09:09
 * @version: V1.0
 **/
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private RedisDao redisDao;

    @Value("${header}")
    private String header;

    @Override
    public Map<String,Object> login(Student student, String ua) {
        HashMap<String, Object> map = new HashMap<>();
        Student stu = redisDao.getStudent(student);
        String type = null;
        if (null == stu) {
            return null;
        }
        try {
            UserAgentInfo userAgentInfo= UserAgentUtils.uaSparser.parse(ua);
            type = userAgentInfo.getDeviceType();
        } catch (IOException e) {
            e.printStackTrace();
            type = "PC";
        }
        String token = createToken(stu, type);
        boolean flag = saveToken(stu, token);
        if (flag) {
            map.put("stu",stu);
            map.put("token",token);
            return map;
        }
        map.put("stu",stu);
        return map;
    }

    public String createToken(Student stu, String type) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(header).append(type).append("-");
        String info = DigestUtils.md5Hex(JSON.toJSONString(stu))+"-";
        String time = System.currentTimeMillis()+"";
        String uuid = UUID.randomUUID().toString().substring(0,8);
        stringBuilder.append(info).append(time).append(uuid);
        return stringBuilder.toString();
    }

    public boolean saveToken(Student stu, String token) {
        String key = "stu"+stu.getSid();
        String value = (String) redisUtils.get(key);
        if (null != value) {
            redisUtils.del(key);
            redisUtils.del(value);
        }
        try {
            redisUtils.set(key, token, 3000);
            redisUtils.set(token,JSON.toJSONString(stu),3000);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
