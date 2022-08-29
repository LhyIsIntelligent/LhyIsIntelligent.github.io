package com.atguigu.myzhxy.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** INSERT语句 ( MyBatis方式 )
  INSERT INTO `tb_teacher`
        (`address`, `clazz_name`, `email`, `gender`, `id`, `name`, `password`, `portrait_path`, `telephone`, `tno`) 
  VALUES(#{address}, #{clazz_name}, #{email}, #{gender}, #{id}, #{name}, #{password}, #{portrait_path}, #{telephone}, #{tno}) 

  自增主键: id
*/
@Data                      //@Data: lombok自动生成 Getter 和 Setter方法
@AllArgsConstructor        //@AllArgsConstructor: lombok自动生成 全参构造方法
@NoArgsConstructor         //@NoArgsConstructor: lombok自动生成 无参构造方法
@TableName("tb_teacher")     //@TableName("表名"): mybatis-plus 帮我们实现数据库表与POJO的映射
public class Teacher
{
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id ;

	private String address ;
	private String clazzName ;
	private String email ;
	private String gender ;
	private String name ;
	private String password ;
	private String portraitPath ;
	private String telephone ;
	private String tno ;




} 
 