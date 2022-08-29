package com.atguigu.myzhxy.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** INSERT语句 ( MyBatis方式 )
  INSERT INTO `tb_student`
        (`address`, `clazz_name`, `email`, `gender`, `id`, `introducation`, `name`, `password`, `portrait_path`, `sno`, `telephone`) 
  VALUES(#{address}, #{clazz_name}, #{email}, #{gender}, #{id}, #{introducation}, #{name}, #{password}, #{portrait_path}, #{sno}, #{telephone}) 

  自增主键: id
*/
@Data                      //@Data: lombok自动生成 Getter 和 Setter方法
@AllArgsConstructor        //@AllArgsConstructor: lombok自动生成 全参构造方法
@NoArgsConstructor         //@NoArgsConstructor: lombok自动生成 无参构造方法
@TableName("tb_student")     //@TableName("表名"): mybatis-plus 帮我们实现数据库表与POJO的映射
public class Student
{
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id ;
	private String address ;
	private String clazzName ;
	private String email ;
	private String gender ;
	private String introducation ;
	private String name ;
	private String password ;
	private String portraitPath ;
	private String sno ;
	private String telephone ;




} 
 