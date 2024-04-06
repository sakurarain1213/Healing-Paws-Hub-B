package com.example.hou.controller;

import com.example.hou.entity.UserInfo;
import com.example.hou.result.Result;
import com.example.hou.util.ResultUtil;
import com.example.hou.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



/*


IP:8080/userInfo/login    登录 强制需要所有信息
{
    "username":"1",
    "password":"123"
}

IP:8080/userInfo/register   注册  强制需要所有信息
{
    "username":"1",
    "password":"123"
}

IP:8080/userInfo/update  更新用户信息  只需要用户名 别的信息可有可无
{
    "username":"1",
    "password":"123",
     "gender":"women",
     "phone":"13301234566",
      "email":"1889900@163.com"
}

IP:8080/userInfo/get    拿到用户信息
{
    "username":"1"
}

localhost
*/

@SuppressWarnings({"all"}) //控制台输出过滤掉警告信息
@RestController//控制层标志，等价于@Controller+@ResponseBody
@Slf4j//lombok用于日志输出
@RequestMapping("/userInfo")//这里是浏览器8080后的地址 也就是对外接口地址

public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;
    //添加@ResponseBody注解将返回的数据自动转化为json, @RequestBody将接收的数据转化为json
    @RequestMapping("/login")


    /*
   失去类型安全但是可以自由接收body里不是param的请求字段的方法
    @RequestBody Map<String, Object> data) {
    String name = (String) data.get("name");

     */
//@ResponseBody//重点debug区域  即只能在params不能在body传递json的问题  这一行无所谓
    //下面把requestbody删除 则至少用params可以 这个符合简单教程
   public Result login(@RequestBody UserInfo userInfo) {
        UserInfo u = userInfoService.loginService(userInfo);
        if (u.getUsername().equals("密码错误")) {
            return ResultUtil.error("密码错误");
        } else if(u==null) {
            return ResultUtil.error("此用户不存在");
        }
        else{
            Result r = new Result();
            r.setCode(200);
            r.setMsg("登录成功");
            r.setData(u);



            return r;
        }
    }
    /*   登录用string的版本  作为参考 最好全用对象
      public Result login(/*@RequestBody UserInfo userInfo String username,/*@RequestParam String password) {
        String msg = userInfoService.loginService(username,password);
        if (("SUCCESS").equals(msg)) {
            return ResultUtil.success("登录成功");
        } else {
            return ResultUtil.error(msg);
        }
    } */
    /*
    @ResponseBody
    public Result login( String username, String password, HttpServletRequest request, HttpSession session, HttpServletResponse response) {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUsername, username).eq(UserInfo::getPassword, password);
        UserInfo user = userInfoService.getOne(queryWrapper);

        if(user == null){
            //登录失败
            return ResultUtil.error("用户名或密码错误");
        }
        //登录成功
        session.setAttribute("loginUser", user);

        //如果需要记住密码，则添加cookie
        /*
        if("true".equals(isRemPwd)){
            Cookie cookie = new Cookie("username", username);
            cookie.setMaxAge(7*24*60*60);         //cookie保存7天
            cookie.setPath(request.getContextPath()+"/");    //设置cookie存在的路径
            response.addCookie(cookie);
        }else{
            //如果不需要记住密码，则将以前创建的cookie删掉
            Cookie cookie = new Cookie("username", null);
            cookie.setMaxAge(0);   //删除cookie
            cookie.setPath(request.getContextPath()+"/");    //设置cookie存在的路径
            response.addCookie(cookie);
        }

        return ResultUtil.success("登录成功");

    }
*/
    /*加@RequestParam注解：url必须带有参数。
    也就是说直接输入localhost:8080/userInfo/login 会报错，
    不会执行方法。只能输入localhost:8080/userInfo/login?username=xxx&password=XXX 才能执行相应的方法*/
    //添加@ResponseBody注解将返回的数据自动转化为json, @RequestBody将接收的数据转化为json
//重点debug  这边是可以用json传递的
    @RequestMapping("/register")
    public Result register(@RequestBody UserInfo userInfo) {
        String msg = userInfoService.registerService(userInfo);
        if (("SUCCESS").equals(msg)) {
            return ResultUtil.success("注册成功");
        } else {
            return ResultUtil.error(msg);
        }
    }

    @RequestMapping("/update")
    public Result update(@RequestBody UserInfo userInfo) {
        String msg = userInfoService.updateService(userInfo);
        if (("SUCCESS").equals(msg)) {
            return ResultUtil.success("修改成功");}
        else{ return ResultUtil.error(msg);}
    }


    @RequestMapping("/get")
    public Result getuser(@RequestBody UserInfo userInfo) {
        UserInfo u = userInfoService.getuserService(userInfo);
        if (u!=null) {
            return ResultUtil.success(u);
        } else {
            return ResultUtil.error("用户名不存在");
        }
    }

      /*
    @RequestMapping("/register")
    @ResponseBody
    public Result register(UserInfo userInfo){
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(UserInfo::getUsername, userInfo.getUsername());
        UserInfo registerUser = userInfoService.getOne(queryWrapper);

        if(registerUser != null){
            return ResultUtil.error("用户名已存在");
        }
        userInfoService.save(userInfo);
        return ResultUtil.success(userInfo);
    }*/





/*
    @Autowired
    private UserInfoService userInfoService;

    //增加
    @RequestMapping(value="/add",method = RequestMethod.POST)//增加了method选项
    public UserInfo add(@RequestBody UserInfo userInfo) {
        userInfo.setCreateTime(LocalDateTime.now());
        return userInfoService.save(userInfo);
    }

    //删除
    @RequestMapping("delete/{id}")
    public int delete(@PathVariable Integer id) {
        return userInfoService.deleteById(id);
    }

    //修改
    @RequestMapping("/update")
    public int update(@RequestBody UserInfo userInfo) {
        return userInfoService.Update(userInfo);
    }

    //查询
    @RequestMapping("/get/{id}")
    public UserInfo get(@PathVariable Integer id) {
        return userInfoService.getUserInfo(id);
    }

    //查询全部
    @RequestMapping("/list")
    public List<UserInfo> list() {
        return userInfoService.selectAll();
    }
*/
}
