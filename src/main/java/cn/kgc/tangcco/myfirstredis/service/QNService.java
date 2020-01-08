package cn.kgc.tangcco.myfirstredis.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.InputStream;

public interface QNService {
    Response uploadFile(InputStream inputStream)throws QiniuException;
}
