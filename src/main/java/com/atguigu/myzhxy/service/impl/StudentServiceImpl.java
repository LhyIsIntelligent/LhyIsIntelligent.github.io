package com.atguigu.myzhxy.service.impl;

import com.atguigu.myzhxy.mapper.StudentMapper;
import com.atguigu.myzhxy.pojo.Admin;
import com.atguigu.myzhxy.pojo.LoginForm;
import com.atguigu.myzhxy.pojo.Student;
import com.atguigu.myzhxy.service.StudentService;
import com.atguigu.myzhxy.util.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service("studentServiceImpl")  //让Spring初始化Service层    //AdminService接口中的IService接口有若干抽象方法（为mybatis 的底层代码）
@Transactional//事务控制                  //让ServiceImpl<AdminMapper, Admin>接口帮我们实现(其中要求我们传两个泛型<Mapper,Pojo>)
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public Student login(LoginForm loginForm) {
        QueryWrapper<Student> querywrapper =new QueryWrapper<>();
        querywrapper.eq("name",loginForm.getUsername());
        querywrapper.eq("password", MD5.encrypt(loginForm.getPassword()));

        Student student = baseMapper.selectOne(querywrapper);
        return student;
    }

    @Override
    public Student getStudentById(Long userId) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<Student>();
        queryWrapper.eq("id",userId);
        return  baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Student> getStudentByOpr(Page<Student> pageParam, Student student) {

        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(student.getClazzName()))
        {
            studentQueryWrapper.like("clazz_name",student.getClazzName());
        }

        if (!StringUtils.isEmpty(student.getName()))
        {
            studentQueryWrapper.like("name",student.getName());
        }
        studentQueryWrapper.orderByDesc("id");
        Page<Student> studentPage = baseMapper.selectPage(pageParam, studentQueryWrapper);
        return studentPage;
    }
}
