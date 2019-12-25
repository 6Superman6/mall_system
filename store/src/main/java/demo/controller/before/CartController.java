package demo.controller.before;

import demo.model.User;
import demo.service.CartService;
import demo.utils.Const;
import demo.utils.ServerResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 门户_购物车接口
 */
@RestController
@RequestMapping(path = "/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // 1.购物车List列表
    @RequestMapping(path = "/list")
    public ServerResponse list(HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(10,"用户未登录,请登录");
        }
        return cartService.selectCartByUserId(user.getId());

    }

    // 2.购物车添加商品
    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public ServerResponse add(HttpSession session,Integer productId,Integer count)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(10,"用户未登录，请登录");
        }
        return cartService.add(user.getId(),productId,count);
    }

    // 3.更新购物车某个产品数量
    @RequestMapping(path = "/update",method = RequestMethod.POST)
    public ServerResponse update(HttpSession session,Integer productId,Integer count)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(10,"用户未登录，请登录");
        }
        return cartService.updateCountByProductID(user.getId(),productId,count);
    }


    // 4.移除购物车某个产品
    @RequestMapping(path = "/delete_product")
    public ServerResponse delete_product(HttpSession session,String productIds)
    {
        User user = (User) session.getAttribute("user");
        if(user==null)
        {
            return ServerResponse.createByError(10,"用户未登录，请登录");
        }
        return cartService.deleteProducts(user.getId(),productIds);
    }

    // 5.购物车选中某个商品
    @RequestMapping(path = "/select")
    public ServerResponse select(HttpSession session,Integer productId)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(10,"用户未登录，请登录");
        }
        return cartService.selectChecked(user.getId(),productId, Const.Cart.CHECKED);  // 该产品checked为1
    }

    // 6.购物车取消选中某个商品
    @RequestMapping(path = "/un_select")
    public ServerResponse un_select(HttpSession session,Integer productId)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(10,"用户未登录，请登录");
        }
        return cartService.selectChecked(user.getId(),productId, Const.Cart.UN_CHECKED);  // 该产品checked为0
    }

    // 7.查询在购物车里的产品数量
    @RequestMapping(path = "/get_cart_product_count")
    public ServerResponse get_cart_product_count(HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(10,"用户未登录，请登录");
        }
        return cartService.getCountByUserId(user.getId());
    }

    // 8.购物车全选
    @RequestMapping(path = "/select_all")
    public ServerResponse select_all(HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(10,"用户未登录，请登录");
        }
        return cartService.selectChecked(user.getId(),null, Const.Cart.CHECKED);  // checked全都为1
    }

    // 9.购物车取消全选
    @RequestMapping(path = "/un_select_all")
    public ServerResponse un_select_all(HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(10,"用户未登录，请登录");
        }
        return cartService.selectChecked(user.getId(),null, Const.Cart.UN_CHECKED);  // checked全都为0
    }



}
