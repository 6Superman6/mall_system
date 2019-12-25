package demo.controller.after;

import demo.model.User;
import demo.service.UserService;
import demo.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/user")
public class AfterUserController {

    @Autowired
    private UserService userService;

    // 1 管理员登录
    @RequestMapping("/login")
    public ServerResponse login(String username, String password, HttpServletRequest request)
    {
        if (username==null||username.length()==0||password==null||password.length()==0)
        {
            return ServerResponse.createByError("参数错误");
        }
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        User user = (User) userService.login(username,md5Password).getData();
        if(user!=null&&userService.checkAdmin(user.getRole()))
        {
            request.getSession().setAttribute("user",user);
        }
        else if(user!=null)
        {
            return ServerResponse.createByError("不是管理员,无法登录");
        }
        return userService.login(username,md5Password);
    }

    // 2 查询用户列表
    @RequestMapping("/list")
    public ServerResponse list(HttpSession session,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(10,"用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))  //是否为管理员
        {
            return userService.list(pageNum,pageSize);
        }
        else
        {
            return ServerResponse.createByError("无权操作");
        }
    }

}
