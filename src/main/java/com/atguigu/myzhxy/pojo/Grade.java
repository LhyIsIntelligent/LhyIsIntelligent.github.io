package com.atguigu.myzhxy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** INSERT语句 ( MyBatis方式 )
  INSERT INTO `tb_grade`
        (`email`, `id`, `introducation`, `manager`, `name`, `telephone`) 
  VALUES(#{email}, #{id}, #{introducation}, #{manager}, #{name}, #{telephone}) 

  自增主键: id
*/
@Data                      //@Data: lombok自动生成 Getter 和 Setter方法
@AllArgsConstructor        //@AllArgsConstructor: lombok自动生成 全参构造方法
@NoArgsConstructor         //@NoArgsConstructor: lombok自动生成 无参构造方法
@TableName("tb_grade")     //@TableName("表名"): mybatis-plus 帮我们实现数据库表与POJO的映射
public class Grade
{
	@TableId(value = "id",type = IdType.AUTO)
	private Integer id ;
	private String email ;
	private String introducation ;
	private String manager ;
	private String name ;
	private String telephone ;




} 
 