package demo.controller.before;

import demo.mapper.UserMapper;
import demo.model.User;
import demo.service.UserService;
import demo.utils.ServerResponse;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * 用户接口
 */
@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 登录
    @RequestMapping(path = "/login",method = RequestMethod.POST)
    public ServerResponse login(@RequestParam("username") String username, @RequestParam("password") String password,
                                HttpServletRequest request)
    {
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
//        System.out.println(md5Password);
        User user = (User) userService.login(username,md5Password).getData();
        if(user!=null)
        {
            request.getSession().setAttribute("user",user);
        }
        return userService.login(username,md5Password);
    }

    // 注册
    @RequestMapping(path = "/register",method = RequestMethod.POST)
    public ServerResponse register(@Valid User user,@RequestParam(name = "role",defaultValue = "1",required = false) int role)
    {
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        user.setRole(role);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return userService.insert(user);
    }

    // 检查用户名是否有效
    /// check_valid.do?str=admin&type=username就是检查用户名。
    // str可以是用户名也可以是email。对应的type是username和email
    @RequestMapping(path = "/check_valid",method = RequestMethod.POST)
    public ServerResponse check_valid(@RequestParam("str") String str,@RequestParam("type") String type)
    {
        if (type.equals("username"))
        {
            return userService.getOkUsername(str);
        }
        else if(type.equals("email"))
        {
            return userService.getOkEmail(str);
        }
        else
        {
            return ServerResponse.createByError("校验失败");
        }
    }

    // 获取登录用户信息
    @RequestMapping("/get_user_info")
    public ServerResponse get_user_info(HttpServletRequest request)
    {
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        if(user!=null)
        {
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByError("用户未登录,无法获取当前用户信息");
    }

    // 退出登录
    @RequestMapping("logout")
    public ServerResponse logout(HttpServletRequest request)
    {
        if(request.getSession().getAttribute("user")!=null)
        {
            request.getSession().setAttribute("user",null);
            return ServerResponse.createBySuccessMsg("退出成功");
        }
        return ServerResponse.createByError("服务端异常");
    }

    // 忘记密码---根据用户名查看问题
    @RequestMapping("/forget_get_question")
    public ServerResponse forget_get_question(@RequestParam("username") String username)
    {
        return userService.forget_pw(username);
    }

    // 提交问题答案--username,question,answer  答案正确得到一个token值
    @RequestMapping(path = "/forget_check_answer",method = RequestMethod.POST)
    public ServerResponse forget_check_answer(@RequestParam("username") String username,
                                              @RequestParam("question")String question,@RequestParam("answer") String answer)
    {
        return userService.check_answer(username,question,answer);
    }

    // 忘记密码的重设密码
    @RequestMapping(path = "/forget_reset_password",method = RequestMethod.POST)
    public ServerResponse forget_reset_password(@RequestParam("username") String username,@RequestParam("passwordNew") String passwordNew,
                                                @RequestParam("forgetToken") String forgetToken)
    {
        System.out.println(username);
        String md5Password = DigestUtils.md5DigestAsHex(passwordNew.getBytes());
        System.out.println(md5Password);
        return userService.reset_password(username,md5Password,forgetToken);
    }

    // 登录中状态重置密码
    @RequestMapping(path = "/reset_password",method = RequestMethod.POST)
    public ServerResponse reset_password(@RequestParam("passwordOld") String passwordOld,@RequestParam("passwordNew") String passwordNew,
                                         HttpServletRequest request)
    {
        String md5Password = DigestUtils.md5DigestAsHex(passwordNew.getBytes());
        String md5PasswordOld = DigestUtils.md5DigestAsHex(passwordOld.getBytes());
        System.out.println(md5PasswordOld+" "+md5Password);
        User user = (User) request.getSession().getAttribute("user");
        return userService.OnUpPw(md5PasswordOld,md5Password,user);

    }

    // 登录状态更新个人信息-- email,phone,question,answer
    @RequestMapping(path = "/update_information",method = RequestMethod.POST)
    public ServerResponse update_information(@Valid User user,HttpServletRequest request)
    {
        User user2 = (User) request.getSession().getAttribute("user");
        if (user2==null)
        {
            return ServerResponse.createByError("用户未登录");
        }
        if (user.getEmail()!=null&&!user.getEmail().equals(user2.getEmail()))
        {
            user2.setEmail(user.getEmail());
        }
        if(user.getPhone()!=null&&!user.getPhone().equals(user2.getPhone()))
        {
            user2.setPhone(user.getPhone());
        }
        if(user.getQuestion()!=null&&!user.getQuestion().equals(user2.getQuestion()))
        {
            user2.setQuestion(user.getQuestion());
        }
        if(user.getAnswer()!=null&&!user.getAnswer().equals(user2.getAnswer()))
        {
            user2.setAnswer(user.getAnswer());
        }
        user2.setUpdateTime(new Date());
        return userService.update(user2);

    }

    // 获取当前登录用户的详细信息
    @RequestMapping(path = "/get_information")
    public ServerResponse get_information(HttpServletRequest request)
    {
        User user = (User) request.getSession().getAttribute("user");
        if(user==null)
        {
            return ServerResponse.createByError("用户未登录,无法获取当前用户信息,status=10,强制登录");
        }
        return ServerResponse.createBySuccess(user);
    }




}
