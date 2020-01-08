package cn.kgc.tangcco.myfirstredis.service.impl;

import cn.kgc.tangcco.myfirstredis.service.QNService;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @program: myfirstredis
 * @description: TODO
 * @author: cuihao
 * @create: 2020-01-08 11:08
 * @version: V1.0
 **/
@Service
public class QNServiceImpl implements QNService, InitializingBean {

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;

    @Value("${qiniu.Bucket}")
    private String bucket;

    @Autowired
    private StringMap putPolicy;

    @Override
    public Response uploadFile(InputStream inputStream) throws QiniuException {
        Response response = uploadManager.put(inputStream,null,getToken(),putPolicy,null);
        int trytimes=0;
        while (response.needRetry()&&trytimes<3){
            response=uploadManager.put(inputStream,null,getToken(),putPolicy,null);
            trytimes++;
        }
        return response;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        putPolicy.put("callbackUrl", "http://localhost:8080/test/");
//        putPolicy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
//        putPolicy.put("callbackBodyType", "application/json");
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
    }

    private String getToken(){
        return auth.uploadToken(bucket, null, 3600, putPolicy, true);
    }
}
