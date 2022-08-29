package com.atguigu.myzhxy.mapper;

import com.atguigu.myzhxy.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository  //方便Spring找到这个接口,并以防idea报错
public interface AdminMapper extends BaseMapper<Admin> {
}
