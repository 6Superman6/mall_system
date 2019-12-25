package demo.service;

import demo.model.Cart;
import demo.service.base.BaseService;
import demo.utils.ServerResponse;
import demo.vo.CartVo;

import java.util.List;

public interface CartService extends BaseService<Cart> {

    // 1.购物车List列表
    ServerResponse<CartVo> selectCartByUserId(int id);

    // 2.购物车添加商品
    ServerResponse add(Integer userId,Integer productId,Integer count);

    // 3.更新购物车某个产品数量
    ServerResponse updateCountByProductID(Integer userId,Integer productId,Integer count);

    // 4.移除购物车某个产品
    ServerResponse deleteProducts(Integer userId,String productIds);

    // 5.购物车选中某个商品
    ServerResponse selectChecked(Integer userId,Integer productId,Integer checked);

    // 7.查询在购物车里的产品数量
    ServerResponse getCountByUserId(Integer userId);

}
