package cn.kgc.tangcco.myfirstredis.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: myfirstredis
 * @description: TODO
 * @author: cuihao
 * @create: 2020-01-04 11:13
 * @version: V1.0
 **/
@Data
@ApiModel(description = "这是返回的对象")
public class Result {
    @ApiModelProperty(value = "这是状态码")
    private Integer code;
    @ApiModelProperty(value = "这是提示信息")
    private String message;
    @ApiModelProperty(value = "这是返回数据")
    private String data;
}
