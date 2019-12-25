package demo.controller.after;

import demo.model.User;
import demo.service.OrderService;
import demo.service.UserService;
import demo.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/manage/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    // 1.订单List
    @RequestMapping("/list")
    public ServerResponse list(HttpSession session, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                               @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(10,"用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))  //是否为管理员
        {
            return orderService.manageList(pageNum,pageSize);
        }
        else
        {
            return ServerResponse.createByError("无权操作");
        }
    }

    // 2.按订单号查询
    @RequestMapping("/search")
    public ServerResponse search(HttpSession session,Long orderNo,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize",defaultValue = "10")int pageSize)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(10,"用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))  //是否为管理员
        {
            return orderService.manageSearch(orderNo,pageNum,pageSize);
        }
        else
        {
            return ServerResponse.createByError("无权操作");
        }
    }

    // 3.订单详情
    @RequestMapping("/detail")
    public ServerResponse detail(HttpSession session,Long orderNo)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(10,"用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))  //是否为管理员
        {
            return orderService.manageDetail(orderNo);
        }
        else
        {
            return ServerResponse.createByError("无权操作");
        }
    }

    // 4.订单发货
    @RequestMapping("/send_goods")
    public ServerResponse send_goods(HttpSession session,Long orderNo)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(10,"用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))  //是否为管理员
        {
            return orderService.manageSendGoods(orderNo);
        }
        else
        {
            return ServerResponse.createByError("无权操作");
        }
    }
}
