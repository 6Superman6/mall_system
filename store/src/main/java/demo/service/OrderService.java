package demo.service;

import com.github.pagehelper.PageInfo;
import demo.model.Ordered;
import demo.service.base.BaseService;
import demo.utils.ServerResponse;
import demo.vo.OrderVo;

import java.util.Map;

public interface OrderService extends BaseService<Ordered> {

    // 1.创建订单
    ServerResponse create(Integer userId,Integer shippingId);

    // 2.获取订单的商品信息
    public ServerResponse getOrderCartProduct(Integer userId);

    // 3.订单List
    public ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);

    // 4.订单详情detail
    public ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);

    // 5.取消订单
    public ServerResponse<String> cancel(Integer userId,Long orderNo);

    // 1.订单List  -- 后台
    public ServerResponse<PageInfo> manageList(Integer pageNum,Integer pageSize);

    // 2.按订单号查询  -- 后台
    public ServerResponse<PageInfo> manageSearch(Long orderNo,int pageNum,int pageSize);

    // 3.订单详情  -- 后台
    public ServerResponse<OrderVo> manageDetail(Long orderNo);

    // 4.定单发货  -- 后台
    public ServerResponse<String> manageSendGoods(Long orderNo);

    //统计个数
    int getCount();

    // 1.支付
    ServerResponse pay(Long orderNo, Integer id);

    // 2.查询订单支付状态
    public ServerResponse queryOrderPayStatus(Integer userId,Long orderNo);

    // 3.支付宝回调
    public ServerResponse aliCallback(Map<String,String> params);
}
