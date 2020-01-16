package cn.kgc.tangcco.myfirstredis.controller;

import cn.kgc.tangcco.myfirstredis.service.QNService;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @program: myfirstredis
 * @description: TODO
 * @author: cuihao
 * @create: 2020-01-08 11:25
 * @version: V1.0
 **/
@Controller
public class QNController {
    @Autowired
    private QNService qnService;

    @Value("${qiniu.path}")
    private String path;

    @RequestMapping("/")
    public String init(){
        return "house";
    }

    @PostMapping("/test")
    public String test(@RequestParam("file") MultipartFile file, Model model){
        String url = path+"/";
        try {
            Response response = qnService.uploadFile(file.getInputStream());
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            url +=putRet.key;
            System.out.println(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("path",url);
        return "aaa";
    }
}
