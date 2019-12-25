package demo.controller.after;

import com.google.common.collect.Maps;
import demo.mapper.UserMapper;
import demo.model.User;
import demo.service.OrderService;
import demo.service.ProductService;
import demo.service.UserService;
import demo.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/manage/statistic")
public class Statistic {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/base_count")
    public ServerResponse serverResponse(HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(10,"用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))  //是否为管理员
        {
            Map<String,Integer> map = Maps.newHashMap();
            map.put("userCount",userService.getCount());
            map.put("productCount",productService.getCount());
            map.put("orderCount",orderService.getCount());
            return ServerResponse.createBySuccess(map);
        }
        else
        {
            return ServerResponse.createByError("无权操作");
        }
    }



}
