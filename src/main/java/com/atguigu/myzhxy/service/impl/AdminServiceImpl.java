package com.atguigu.myzhxy.service.impl;

import com.atguigu.myzhxy.mapper.AdminMapper;
import com.atguigu.myzhxy.pojo.Admin;
import com.atguigu.myzhxy.pojo.LoginForm;
import com.atguigu.myzhxy.service.AdminService;
import com.atguigu.myzhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

//adminServiceImpl:当前实现类的ID
@Service("adminServiceImpl")  //让Spring初始化Service层    //AdminService接口中的IService接口有若干抽象方法（为mybatis 的底层代码）
@Transactional//事务控制                  //让ServiceImpl<AdminMapper, Admin>接口帮我们实现(其中要求我们传两个泛型<Mapper,Pojo>)
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements  AdminService {
    @Override
    public Admin login(LoginForm loginForm) {
        QueryWrapper<Admin> querywrapper =new QueryWrapper<>();
        querywrapper.eq("name",loginForm.getUsername());
        querywrapper.eq("password", MD5.encrypt(loginForm.getPassword()));

        Admin admin = baseMapper.selectOne(querywrapper);

        return admin;
    }

    @Override
    public Admin getAdminById(Long userId) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<Admin>();
        queryWrapper.eq("id",userId);
        return  baseMapper.selectOne(queryWrapper);

    }

    @Override
    public IPage<Admin> getAdminsByOpr(Page<Admin> pageParam, String adminName) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(adminName)) {
            queryWrapper.like("name",adminName);
        }
        queryWrapper.orderByDesc("id");

        Page<Admin> Page = baseMapper.selectPage(pageParam, queryWrapper);
        return Page;
    }
}
