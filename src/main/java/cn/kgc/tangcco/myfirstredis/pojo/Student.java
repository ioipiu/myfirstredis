package cn.kgc.tangcco.myfirstredis.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class Student {

  private int sid;
  private String sname;
  private int sage;
  private int gender;
  private int xuefen;
  private String pwd;
  private int team;
  private int role;
  private int status;
  private String photo;

}
