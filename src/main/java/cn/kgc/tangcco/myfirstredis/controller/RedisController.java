package cn.kgc.tangcco.myfirstredis.controller;

import cn.kgc.tangcco.myfirstredis.pojo.Result;
import cn.kgc.tangcco.myfirstredis.pojo.Student;
import cn.kgc.tangcco.myfirstredis.service.RedisService;
import cn.kgc.tangcco.myfirstredis.utils.RedisUtils;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @program: myfirstredis
 * @description: TODO
 * @author: cuihao
 * @create: 2020-01-03 10:04
 * @version: V1.0
 **/
@Controller
@Api(tags = "这是练习写的登录的类")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisUtils redisUtils;


    @PostMapping("/login")
    @ResponseBody
    @ApiOperation(value = "输入学号和密码，验证信息",notes = "对了就给学生数据，错了就返回字符串")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Student",value = "student",dataType = "Student")
    })
    @ApiResponses({
            @ApiResponse(code = 2001,message = "登录失败"),
            @ApiResponse(code = 2002,message = "登录成功")
    })
    public Result login(Student student, HttpServletRequest request) {
        String ua = request.getHeader("User-Agent");
        Result result = new Result();
        Map<String, Object> map = redisService.login(student, ua);
        if (null == map) {
            result.setCode(2001);
            result.setMessage("登录失败");
            return result;
        }
        result.setCode(2002);
        Student stu = (Student) map.get("stu");
        result.setMessage("登录成功，欢迎您："+stu.getSname());
        result.setData(JSON.toJSONString(map));
        return result;
    }

}
