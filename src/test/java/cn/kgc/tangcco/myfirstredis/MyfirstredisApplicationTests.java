package cn.kgc.tangcco.myfirstredis;

import cn.kgc.tangcco.myfirstredis.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MyfirstredisApplicationTests {


    @Autowired
    private RedisUtils redisUtils;

    @Test
    public void contextLoads() {
        /*String name = stringRedisTemplate.opsForValue().get("name2");
        System.out.println(name);*/
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwic3ViIjoie1xuICAgIFwicHdkXCI6IFwiMTExMTExXCIsXG4gICAgXCJzaWRcIjogXCIxXCJcbn0iLCJpYXQiOjE1NzgyODg4MTV9.xY2RijKndWeYZLKkUZOjInFnLvyiBIxqcJOy-YO1m1Y";
        redisUtils.set("name",token);
        String name = (String)redisUtils.get("name");
        System.out.println(name);
    }

}
