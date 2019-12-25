package demo.controller.before;

import demo.model.Shipping;
import demo.model.User;
import demo.service.ShippingService;
import demo.utils.ServerResponse;
import org.hibernate.validator.constraints.pl.REGON;
import org.omg.PortableServer.SERVANT_RETENTION_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    // 1.添加地址
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ServerResponse add(HttpSession session,@Valid Shipping shipping)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(1,"请登录之后查询");
        }
        if(shipping==null)
        {
            return ServerResponse.createByError(1,"新建地址失败");
        }
        if(shipping.getUserId()==null||shipping.getReceiverName()==null||shipping.getReceiverPhone()==null
                ||shipping.getReceiverMobile()==null||shipping.getReceiverProvince()==null||
                shipping.getReceiverCity()==null||shipping.getReceiverAddress()==null||shipping.getReceiverZip()==null)
        {
            return ServerResponse.createByError("缺少参数");
        }
        if(shipping.getReceiverName().length()==0||shipping.getReceiverPhone().length()==0
                ||shipping.getReceiverMobile().length()==0||shipping.getReceiverProvince().length()==0||
                shipping.getReceiverCity().length()==0||shipping.getReceiverAddress().length()==0||shipping.getReceiverZip().length()==0)
        {
            return ServerResponse.createByError("缺少参数");
        }
//        System.out.println("shipping: "+shipping);
        shipping.setCreateTime(new Date());
        shipping.setUpdateTime(new Date());
        if (user.getId()!=shipping.getUserId())
        {
            shipping.setUserId(user.getId());
        }
        return shippingService.insert(shipping);


    }

    // 2.删除地址
    @RequestMapping("/del")
    public ServerResponse del(HttpSession session,Integer shippingId)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(1,"请登录之后查询");
        }
        if (shippingId==null)
        {
            return ServerResponse.createByError("参数错误");
        }
        return shippingService.delete(shippingId);
    }

    // 3.登录状态修改地址
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public ServerResponse update(HttpSession session,@Valid Shipping shipping)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(1,"请登录之后查询");
        }
        if (shipping==null||shipping.getId()==null)
        {
            return ServerResponse.createByError("参数错误");
        }
        shipping.setUpdateTime(new Date());
        return shippingService.update(shipping);
    }

    // 4.选中查看具体的地址
    @RequestMapping(path = "/select")
    public ServerResponse select(HttpSession session,Integer shippingId)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(1,"请登录之后查询");
        }
        if(shippingId==null)
        {
            return ServerResponse.createByError("参数异常");
        }
        return shippingService.get(shippingId);
    }

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public String test(@RequestParam("a") String a, @RequestParam("b") String b)
    {
        System.out.println(a+"*"+b);
        return "hello";
    }

    // 5.地址列表
    @RequestMapping("/list")
    public ServerResponse list(HttpSession session,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(1,"请登录之后查询");
        }
        return shippingService.list(user.getId(),pageNum,pageSize);
    }

}
