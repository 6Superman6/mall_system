package demo.controller.before;

import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.google.common.collect.Maps;
import demo.model.User;
import demo.service.OrderService;
import demo.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.security.krb5.Config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.Map;

/**
 * 门户_订单接口
 */
@RestController
@RequestMapping(path = "/order")
public class BeforeOrderController {

    @Autowired
    private OrderService orderService;


    // 1.创建订单
    @RequestMapping(path = "/create")
    public ServerResponse create(HttpSession session,Integer shippingId)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(1,"用户未登录,请登录");
        }
        return orderService.create(user.getId(),shippingId);
    }

    // 2.获取订单的商品信息
    @RequestMapping(path = "/get_order_cart_product")
    public ServerResponse get_order_cart_product(HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(1,"用户未登录,请登录");
        }
        return orderService.getOrderCartProduct(user.getId());
    }

    // 3.订单List
    @RequestMapping("/list")
    public ServerResponse list(HttpSession session, @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                               @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(1,"用户未登录,请登录");
        }
        return orderService.getOrderList(user.getId(),pageNum,pageSize);

    }

    // 4.订单详情detail
    @RequestMapping("detail")
    public ServerResponse detail(HttpSession session,Long orderNo)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(1,"用户未登录,请登录");
        }
        return orderService.getOrderDetail(user.getId(),orderNo);
    }

    // 5.取消订单
    @RequestMapping("/cancel")
    public ServerResponse cancel(HttpSession session,Long orderNo)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(1,"用户未登录,请登录");
        }
        return orderService.cancel(user.getId(),orderNo);
    }

    /************************支付***************************/

    // 1.支付
    @RequestMapping("/pay")
    public ServerResponse pay(HttpSession session,Long orderNo)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(1,"用户未登录,请登录");
        }
        return orderService.pay(orderNo,user.getId());
    }

    // 2.查询订单支付状态
    @RequestMapping("/query_order_pay_status")
    public ServerResponse query_order_pay_status(HttpSession session,Long orderNo)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(1,"用户未登录,请登录");
        }
       return orderService.queryOrderPayStatus(user.getId(),orderNo);
    }

    // 3.支付宝回调
    @RequestMapping("/alipay_callback")
    public Object alipay_callback(HttpServletRequest request)
    {
        Map<String,String> params = Maps.newHashMap();
        Map requestParams = request.getParameterMap();
        for (Iterator iterator = requestParams.keySet().iterator();iterator.hasNext();)
        {
            String name = (String) iterator.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i=0;i<values.length;i++)
            {
                valueStr = (i==values.length-1)?valueStr+values[i]:valueStr+values[i]+",";
            }
            params.put(name,valueStr);
        }

        params.remove("sign_type");
        try {
            boolean alipayRSACheckedV2 = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(),"utf-8", Configs.getSignType());
            if (!alipayRSACheckedV2)
            {
                return ServerResponse.createByError("非法请求,验证不通过,再恶意请求我就报警找网警了");
            }
        }catch (Exception e)
        {
            System.out.println("支付宝验证回调异常");
        }
        ServerResponse serverResponse = orderService.aliCallback(params);
        if (serverResponse.isSuccess())
        {
            return "success";
        }
        return "failed";
    }









    /************************支付***************************/

}
