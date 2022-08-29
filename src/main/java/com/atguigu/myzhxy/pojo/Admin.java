package com.atguigu.myzhxy.pojo; 

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/** INSERT语句 ( MyBatis方式 ) 
  INSERT INTO `tb_admin`
        (`address`, `email`, `gender`, `id`, `name`, `password`, `portrait_path`, `telephone`) 
  VALUES(#{address}, #{email}, #{gender}, #{id}, #{name}, #{password}, #{portrait_path}, #{telephone}) 

  自增主键: id
*/
@Data                      //@Data: lombok自动生成 Getter 和 Setter方法
@AllArgsConstructor        //@AllArgsConstructor: lombok自动生成 全参构造方法
@NoArgsConstructor         //@NoArgsConstructor: lombok自动生成 无参构造方法
@TableName("tb_admin")     //@TableName("表名"): mybatis-plus 帮我们实现数据库表与POJO的映射
public class Admin
{
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id ;
	private String name ;
	private Character gender;
	private String password ;
	private String address ;
	private String email ;
	private String portraitPath ; //头像的图片路径
	private String telephone ;




} 
 