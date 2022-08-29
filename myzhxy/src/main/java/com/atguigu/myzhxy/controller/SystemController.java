package com.atguigu.myzhxy.controller;

import com.atguigu.myzhxy.pojo.Admin;
import com.atguigu.myzhxy.pojo.LoginForm;
import com.atguigu.myzhxy.pojo.Student;
import com.atguigu.myzhxy.pojo.Teacher;
import com.atguigu.myzhxy.service.AdminService;
import com.atguigu.myzhxy.service.StudentService;
import com.atguigu.myzhxy.service.TeacherService;
import com.atguigu.myzhxy.util.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.deploy.net.HttpResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Api(tags = "系统控制器")
@RestController
@RequestMapping("/sms/system")
public class SystemController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;


    @ApiOperation("更新用户密码密码的处理器")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(
          @ApiParam("token口令") @RequestHeader("token") String token,
          @ApiParam("旧密码") @PathVariable("oldPwd") String oldPwd,
          @ApiParam("新密码") @PathVariable("newPwd") String newPwd
    ){
        //检查token是否过期
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            //token过期
            return Result.fail().message("token失效,请重新登录后修改密码");
        }
        // 获取用户ID和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);

//        switch (userType){
//            case 1:
//                QueryWrapper<Admin> queryWrapper1 = new QueryWrapper<>();
//                queryWrapper1.eq("id",userId.intValue());
//                queryWrapper1.eq("passward",oldPwd);
//                Admin admin = adminService.getOne(queryWrapper1);
//                if (admin != null) {
//                    admin.setPassword(newPwd);
//                    adminService.saveOrUpdate(admin);
//                }else {
//                    return Result.fail().message("原密码有误");
//                }
//                break;
//
//            case 2:
//                QueryWrapper<Student> queryWrapper2 = new QueryWrapper<>();
//                queryWrapper2.eq("id",userId.intValue());
//                queryWrapper2.eq("passward",oldPwd);
//                Student student = studentService.getOne(queryWrapper2);
//                if (student != null) {
//                    //修改
//                    student.setPassword(newPwd);
//                    studentService.saveOrUpdate(student);
//                }else {
//                    return Result.fail().message("原密码有误!");
//                }
//                break;
//
//            case 3:
//                QueryWrapper<Teacher> queryWrapper3 = new QueryWrapper<>();
//                queryWrapper3.eq("id",userId.intValue());
//                queryWrapper3.eq("passward",oldPwd);
//                Teacher teacher = teacherService.getOne(queryWrapper3);
//                if (teacher != null) {
//                    //修改
//                    teacher.setPassword(newPwd);
//                    teacherService.saveOrUpdate(teacher);
//                }else {
//                    return Result.fail().message("原密码有误!");
//                }
//                break;
//        }
//        return Result.ok();
        if(userType == 1){
            QueryWrapper<Admin> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
            Admin admin = adminService.getOne(queryWrapper);
            if (null!=admin) {
                admin.setPassword(newPwd);
                adminService.saveOrUpdate(admin);
            }else{
                return Result.fail().message("原密码输入有误！");
            }
        }else if(userType == 2){
            QueryWrapper<Student> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
            Student student = studentService.getOne(queryWrapper);
            if (null!=student) {
                student.setPassword(newPwd);
                studentService.saveOrUpdate(student);
            }else{
                return Result.fail().message("原密码输入有误！");
            }
        }
        else if(userType == 3){
            QueryWrapper<Teacher> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
            Teacher teacher = teacherService.getOne(queryWrapper);
            if (null!=teacher) {
                teacher.setPassword(newPwd);
                teacherService.saveOrUpdate(teacher);
            }else{
                return Result.fail().message("原密码输入有误！");
            }
        }
        return Result.ok();
    }




    @ApiOperation("文件上传统一入口")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
         @ApiParam("头像文件") @RequestPart("multipartFile") MultipartFile multipartFile
    ){
        //为解决名字冲突,我们使用UUID随机生成新的文件名(并把文件名中所有的"-",替换为"")
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //通过(MultipartFile)获取原始文件名
        String originalFilename = multipartFile.getOriginalFilename();
        //拼凑出新的文件名({uuid} + "jpg")
        int i = originalFilename.lastIndexOf(".");
              // String newFileName= uuid+originalFilename.substring(i); 注:“+”或"concat"任选其一即可
        String newFileName= uuid.concat(originalFilename.substring(i));

        //保存文件(实际生产环境这里会使用真正的文件存储第三方服务器)
        String portraitPath = "D:/JavaWeb/myzhxy/target/classes/public/upload".concat(newFileName);
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //响应图片路径
        String path="upload/".concat(newFileName);
       return Result.ok(path);
    }



    @ApiOperation("通过token口令获取当前登录的用户信息的方法")
    @GetMapping("/getInfo")
    public  Result getInfoByToken(
           @ApiParam("token口令") @RequestHeader("token") String token
    ){
        boolean expiration = JwtHelper.isExpiration(token);
        if (expiration) {
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从token中解析出 用户id 和用户的类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);


        Map<String,Object> map = new LinkedHashMap<>();
        switch (userType){
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
                Student student = studentService.getStudentById(userId);
                map.put("userType",2);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType",3);
                map.put("user",teacher);
                break;

        }

        return Result.ok(map);
    }


    @ApiOperation("登录的方法")
     @PostMapping("/login")
     public Result login(
             @ApiParam("登录提交信息的Form表单")@RequestBody LoginForm loginForm,HttpServletRequest request){
         //验证码校验
         HttpSession session = request.getSession();
         String sessionVerifiCode = (String) session.getAttribute("verifiCode");
         String loginVerifiCode = loginForm.getVerifiCode();
         if ("".equals(sessionVerifiCode) || null==sessionVerifiCode ){
             return Result.fail().message("验证码失效,请刷新后重试");
         }
         if(!sessionVerifiCode.equalsIgnoreCase(loginVerifiCode)){
             return Result.fail().message("验证码有误,请小心输入后重试");
         }
         //从Session域中移除现有验证码
         session.removeAttribute("verifiCode");
         //分用户类型进行校验

         //准备一个map用户存放响应的数据
         Map<String,Object> map = new LinkedHashMap<>();
         switch (loginForm.getUserType()){
             case 1:
                 try {
                     Admin admin =adminService.login(loginForm);
                     if (null !=admin){
                         //用户的类型和用户id转成一个密文,以token的名称向客户端反馈
                         map.put("token", JwtHelper.createToken(admin.getId().longValue(),1));
                     }else {
                         throw new RuntimeException("用户名或密码错误");
                     }
                     return  Result.ok(map);
                 } catch (RuntimeException e) {
                     e.printStackTrace();
                     return Result.fail().message(e.getMessage());
                 }
             case 2:
                 try {
                     Student student = studentService.login(loginForm);
                     if (null !=student){
                         //用户的类型和用户id转成一个密文,以token的名称向客户端反馈
                         map.put("token", JwtHelper.createToken(student.getId().longValue(),2));
                     }else {
                         throw new RuntimeException("用户名或密码错误");
                     }
                     return  Result.ok(map);
                 } catch (RuntimeException e) {
                     e.printStackTrace();
                     return Result.fail().message(e.getMessage());
                 }
             case 3:
                 try {
                     Teacher teacher = teacherService.login(loginForm);
                     if (null !=teacher){
                         //用户的类型和用户id转成一个密文,以token的名称向客户端反馈
                         map.put("token", JwtHelper.createToken(teacher.getId().longValue(),3));
                     }else {
                         throw new RuntimeException("用户名或密码错误");
                     }
                     return  Result.ok(map);
                 } catch (RuntimeException e) {
                     e.printStackTrace();
                     return Result.fail().message(e.getMessage());
                 }

         }
     return Result.fail().message("查无此用户");
     }


    @ApiOperation("获取验证码图片")
    @GetMapping("/getVerifiCodeImage")
    public  void  getVerifiCodeImage(HttpServletRequest request
                                    , HttpSession session
                                    , HttpServletResponse response){

        //获取图片
        BufferedImage verifiCodeImage= CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片上的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //将验证码文本放入Session域，为下一次验证做准备
        session.setAttribute("verifiCode",verifiCode);
        //将图片验证码响应给浏览器
        try {
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
