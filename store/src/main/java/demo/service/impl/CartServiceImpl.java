package demo.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import demo.model.Cart;
import demo.model.Product;
import demo.service.CartService;
import demo.service.base.BaseServiceImpl;
import demo.utils.BigDecimalUtil;
import demo.utils.Const;
import demo.utils.ServerResponse;
import demo.vo.CartProductVo;
import demo.vo.CartVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CartServiceImpl extends BaseServiceImpl<Cart> implements CartService {
    // list
    @Override
    public ServerResponse<CartVo> selectCartByUserId(int id) {
        CartVo cartVo = this.getCartVoLimit(id);
        return ServerResponse.createBySuccess(cartVo);
    }

    // add
    @Override
    public ServerResponse add(Integer userId, Integer productId, Integer count) {
        if (productId==null||count==null||productMapper.selectByPrimaryKey(productId)==null)
        {
            return ServerResponse.createByError("参数错误");
        }
        Cart cart = cartMapper.selectCartByProductIdAndUserId(userId,productId);
        if (cart==null)   // 还没有加入购物车，加入购物车
        {
            Cart cart1 = new Cart();
            cart1.setUserId(userId);
            cart1.setProductId(productId);
            cart1.setQuantity(count);
            cart1.setChecked(Const.Cart.CHECKED);   // 1
            cart1.setCreateTime(new Date());
            cart1.setUpdateTime(new Date());
            cartMapper.insert(cart1);
        }
        else   // 已经加入购物车
        {
            count = count + cart.getQuantity();  // 在原有数量的基础上加上count
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);   //更新购物车
        }

        return this.selectCartByUserId(userId);
    }

    // update
    @Override
    public ServerResponse updateCountByProductID(Integer userId, Integer productId, Integer count) {
        if (productId==null||count==null)
        {
            return ServerResponse.createByError("参数错误");
        }
        Cart cart = cartMapper.selectCartByProductIdAndUserId(userId,productId);
        if (cart==null)
        {
            return ServerResponse.createByError("productId不存在，更新购物车失败");
        }
        cart.setQuantity(count);
        try
        {
            cartMapper.updateByPrimaryKey(cart);
        }catch (Exception e)
        {
            return ServerResponse.createByError("更新失败");
        }
        return this.selectCartByUserId(userId);
    }

    // delete_product
    @Override
    public ServerResponse deleteProducts(Integer userId, String productIds) {
        if (productIds==null||productIds.length()==0)
        {
            return ServerResponse.createByError("参数错误");
        }
        List<String> productList = Splitter.on(",").splitToList(productIds);  //以逗号分割
        try
        {
            int cnt = cartMapper.deleteProducts(userId,productList);   //若删除出现错误则cnt=0
            int n = 10/cnt;  //若出现异常，主动创造异常
        }catch (Exception e)
        {
            return ServerResponse.createByError("productId错误，删除异常");
        }
        return this.selectCartByUserId(userId);
    }

    // select
    @Override
    public ServerResponse selectChecked(Integer userId, Integer productId,Integer checked) {
        try {
            int cnt = cartMapper.selectChecked(userId,productId,checked,new Date());  // 1
            int a = 10/cnt;
        }catch (Exception e)
        {
            return ServerResponse.createByError("productId不存在");
        }
        return this.selectCartByUserId(userId);
    }

    // get_cart_product_count
    @Override
    public ServerResponse getCountByUserId(Integer userId) {
        int cnt = 0;
        try
        {
            cnt = cartMapper.getCountByUserId(userId);
            if (cnt>0)
            {
                return ServerResponse.createBySuccess(cnt);
            }
        }catch (Exception e)
        {
            return ServerResponse.createByError(10,"出现异常");
        }
        return ServerResponse.createBySuccess(0);
    }


    private CartVo getCartVoLimit(Integer userId)
    {
        CartVo cartVo = new CartVo();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVo> cartProductVoList = Lists.newArrayList();

        BigDecimal cartTotalPrice = new BigDecimal("0");

        if(!cartList.isEmpty())
        {
            for(Cart cart : cartList)
            {
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setId(cart.getId());
                cartProductVo.setUserId(cart.getUserId());
                cartProductVo.setProductId(cart.getProductId());

                Product product = productMapper.selectByPrimaryKey(cart.getProductId());
                if(product!=null)
                {
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if(product.getStock()>=cart.getQuantity())   //库存充足的时候
                    {
                        buyLimitCount = cart.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);  // LIMIT_NUM_SUCCESS
                    }else
                    {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);   // LIMIT_NUM_FAIL
                        //购物车中更新有效库存
                        Cart cart1 = new Cart();
                        cart1.setId(cart.getId());
                        cart1.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cart1);
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    //计算总和   商品单价×产品数量=当前商品总钱数
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVo.getQuantity()));
                    cartProductVo.setProductChecked(cart.getChecked());
                }
                if (cart.getChecked()==Const.Cart.CHECKED)   // 1 即购物车选中状态
                {
                    //如果已经勾选,增加到整个的购物车总价中
                    // cartTotalPrice为所有购物车中的总金额
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVo.getProductTotalPrice().doubleValue());
                }
                cartProductVoList.add(cartProductVo);
            }
        }
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        cartVo.setAllChecked(this.getAllCheckedStatus(userId));
        cartVo.setImageHost("localhost:8081/uploads/");
        return cartVo;

    }

    private boolean getAllCheckedStatus(Integer userId)
    {
        if (userId==null)
        {
            return false;
        }                                                           //非0，说明有未打勾的购物车
        return cartMapper.selectCartProductCheckedStatusByUserId(userId)==0;  //0 未打勾的购物车数量为0，返回true
    }

    @Override
    public ServerResponse insert(Cart cart) {
        return null;
    }

    @Override
    public ServerResponse delete(int id) {
        return null;
    }

    @Override
    public ServerResponse update(Cart cart) {
        return null;
    }

    @Override
    public ServerResponse get(int id) {
        return null;
    }

    @Override
    public ServerResponse findAll() {
        return null;
    }

    @Override
    public int getCountById(int id) {
        return 0;
    }

    @Override
    public ServerResponse getyName(String name) {
        return null;
    }

}
