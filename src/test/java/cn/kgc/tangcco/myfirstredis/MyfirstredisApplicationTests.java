package cn.kgc.tangcco.myfirstredis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



@RunWith(SpringRunner.class)
@SpringBootTest
public class MyfirstredisApplicationTests {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void contextLoads() {

        while (true) {
            Date date = new Date();
            String format = new SimpleDateFormat("HH:mm:ss").format(date);
            Calendar calendar = Calendar.getInstance();
            int day = date.getDay();
            System.out.println(day);
            int firstDayOfWeek = calendar.getFirstDayOfWeek();
            System.out.println(firstDayOfWeek);
            if ("08:55:40".equals(format) && day==firstDayOfWeek) {
                System.out.println("aaaaa");
                try {
                    Thread.sleep(1000*60*59*24*7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }


    }

}
