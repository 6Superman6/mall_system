package demo.service.impl;

import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import demo.mapper.ItemMapper;
import demo.model.*;
import demo.service.OrderService;
import demo.service.base.BaseServiceImpl;
import demo.utils.BigDecimalUtil;
import demo.utils.Const;
import demo.utils.DateTimeUtil;
import demo.utils.ServerResponse;
import demo.vo.OrderItemVo;
import demo.vo.OrderProductVo;
import demo.vo.OrderVo;
import demo.vo.ShippingVo;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
@Transactional
public class OrderServiceImpl extends BaseServiceImpl<Ordered> implements OrderService {

    private static AlipayTradeService tradeService;

    static
    {
        try
        {
            /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
             *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
             */
            Configs.init("zfbinfo.properties");

            /** 使用Configs提供的默认参数
             *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
             */
            tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public ServerResponse insert(Ordered order) {
        return null;
    }

    @Override
    public ServerResponse delete(int id) {
        return null;
    }

    @Override
    public ServerResponse update(Ordered order) {
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

    // 根据购物车和一个购物车相对应的产品信息封装订单列表
    private ServerResponse getCartOrderItem(Integer userId,List<Cart> cartList)
    {
        List<Item> orderItemList = Lists.newArrayList();
        if(CollectionUtils.isEmpty(cartList))   //如果购物车中的数据为空
        {
            return ServerResponse.createByError("购物车为空");
        }
        //校验购物车的数据，包括产品的状态和数量
        for (Cart cart : cartList)
        {
            Item orderItem = new Item();
            Product product = productMapper.selectByPrimaryKey(cart.getProductId());  //根据购物车中的产品号获取产品
            if(product.getStatus()!=1)    //如果商品不是在售状态
            {
                return ServerResponse.createByError("产品"+product.getName()+"不是在线售卖状态");
            }
            // 检验库存
            if(cart.getQuantity()>product.getStock())  // 库存量大于实际商品数量
            {
                return ServerResponse.createByError("产品"+product.getName()+"库存不足");
            }

            // 订单赋值
            orderItem.setUserId(userId);
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setProductImage(product.getMainImage());
            orderItem.setCurrentUnitPrice(product.getPrice());
            orderItem.setQuantity(cart.getQuantity());
            // 产品价格×订单中的数量
            orderItem.setTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cart.getQuantity()));
            orderItem.setCreateTime(new Date());
            orderItem.setUpdateTime(new Date());

            orderItemList.add(orderItem);
        }
        return ServerResponse.createBySuccess(orderItemList);
    }

    // 统计所有订单的总金额
    private BigDecimal getOrderTotalPrice(List<Item> orderItemList)
    {
        BigDecimal sum = new BigDecimal(("0"));  // 总金额先初始化为0
        System.out.println("getOrderTotalPrice: "+orderItemList);
        for (Item orderItem : orderItemList)
        {    // 累加每个订单的金额
            sum = BigDecimalUtil.add(sum.doubleValue(),orderItem.getTotalPrice().doubleValue());
        }
        return sum;
    }

    // 生成一个唯一的订单号    =当前时间（long）+随机数（小于100）
    private long generateOrderNo(){
        long currentTime =System.currentTimeMillis();
        return currentTime+new Random().nextInt(100);
    }

    //创建订单
    private Ordered assembleOrder(Integer userId,Integer shippingId,BigDecimal sum)
    {
        Ordered order = new Ordered();
        long orderNo = this.generateOrderNo();  // 生成一个唯一的订单号
        order.setOrderNo(orderNo);
        order.setStatus(10);   // 10 未支付
        order.setPostage(0);
        order.setPaymentType(1);  // (1,"在线支付");
        order.setPayment(sum);   //设置总金额
        order.setUserId(userId);
        order.setShippingId(shippingId);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        System.out.println(order);
        int cnt = 0;
        try {
            cnt = orderMapper.insert(order);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        if(cnt>0)
        {
            return order;
        }
        return null;

    }

    // 订单完成之后，更新一下product产品表中的数量
    private void reduceProductStock(List<Item> orderItemList){
        for(Item orderItem : orderItemList){
            Product product = productMapper.selectByPrimaryKey(orderItem.getProductId());
            product.setStock(product.getStock()-orderItem.getQuantity());
            productMapper.updateByPrimaryKeySelective((ProductWithBLOBs) product);
        }
    }

    private void cleanCart(List<Cart> cartList){
        for(Cart cart : cartList){
            cartMapper.deleteByPrimaryKey(cart.getId());
        }
    }

    @Override
    public ServerResponse create(Integer userId, Integer shippingId) {
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);  // 从购物车中获取数据

        //计算订单的总价
        ServerResponse serverResponse = this.getCartOrderItem(userId,cartList);
        if(!serverResponse.isSuccess())  //说明创建订单列表失败
        {
            return serverResponse;
        }
        List<Item> orderItemList = (List<Item>) this.getCartOrderItem(userId,cartList).getList(); // 把订单表拿出来
        BigDecimal sum = this.getOrderTotalPrice(orderItemList);  //获取所有订单的总金额

        //生成订单
        Ordered order = this.assembleOrder(userId,shippingId,sum);
        if(order==null)
        {
            return ServerResponse.createByError("生成订单错误");
        }
        if (CollectionUtils.isEmpty(orderItemList))
        {
            return ServerResponse.createByError("购物车为空");
        }
        for (Item orderItem : orderItemList)
        {
            orderItem.setOrderNo(order.getOrderNo());
        }
        // 批量插入订单明细表
        itemMapper.batchInsert(orderItemList);

        // 生成订单成功，更新一下产品的库存信息，应为产品数量减少了
        this.reduceProductStock(orderItemList);

        //清空一下购物车,把购物车的内容删除
        this.cleanCart(cartList);
        OrderVo orderVo = assembleOrderVo(order,orderItemList);
        return ServerResponse.createBySuccess(orderVo);
    }

    private ShippingVo assembleShippingVo(Shipping shipping){
        ShippingVo shippingVo = new ShippingVo();
        shippingVo.setReceiverName(shipping.getReceiverName());
        shippingVo.setReceiverAddress(shipping.getReceiverAddress());
        shippingVo.setReceiverProvince(shipping.getReceiverProvince());
        shippingVo.setReceiverCity(shipping.getReceiverCity());
        shippingVo.setReceiverDistrict(shipping.getReceiverDistrict());
        shippingVo.setReceiverMobile(shipping.getReceiverMobile());
        shippingVo.setReceiverZip(shipping.getReceiverZip());
        shippingVo.setReceiverPhone(shippingVo.getReceiverPhone());
        return shippingVo;
    }

    private OrderItemVo assembleOrderItemVo(Item orderItem){
        OrderItemVo orderItemVo = new OrderItemVo();
        orderItemVo.setOrderNo(orderItem.getOrderNo());
        orderItemVo.setProductId(orderItem.getProductId());
        orderItemVo.setProductName(orderItem.getProductName());
        orderItemVo.setProductImage(orderItem.getProductImage());
        orderItemVo.setCurrentUnitPrice(orderItem.getCurrentUnitPrice());
        orderItemVo.setQuantity(orderItem.getQuantity());
        orderItemVo.setTotalPrice(orderItem.getTotalPrice());

        orderItemVo.setCreateTime(DateTimeUtil.dateToStr(orderItem.getCreateTime()));
        return orderItemVo;
    }

    private OrderVo assembleOrderVo(Ordered order,List<Item> orderItemList){
        OrderVo orderVo = new OrderVo();
        orderVo.setOrderNo(order.getOrderNo());
        orderVo.setPayment(order.getPayment());
        orderVo.setPaymentType(order.getPaymentType());
        orderVo.setPaymentTypeDesc(Const.PaymentTypeEnum.codeOf(order.getPaymentType()).getValue());

        orderVo.setPostage(order.getPostage());
        orderVo.setStatus(order.getStatus());
        orderVo.setStatusDesc(Const.OrderStatusEnum.codeOf(order.getStatus()).getValue());

        orderVo.setShippingId(order.getShippingId());
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        if(shipping != null){
            orderVo.setReceiverName(shipping.getReceiverName());
            orderVo.setShippingVo(assembleShippingVo(shipping));
        }

        orderVo.setPaymentTime(DateTimeUtil.dateToStr(order.getPaymentTime()));
        orderVo.setSendTime(DateTimeUtil.dateToStr(order.getSendTime()));
        orderVo.setEndTime(DateTimeUtil.dateToStr(order.getEndTime()));
        orderVo.setCreateTime(DateTimeUtil.dateToStr(order.getCreateTime()));
        orderVo.setCloseTime(DateTimeUtil.dateToStr(order.getCloseTime()));


        orderVo.setImageHost("localhost:8081/uploads/");


        List<OrderItemVo> orderItemVoList = Lists.newArrayList();

        for(Item orderItem : orderItemList){
            OrderItemVo orderItemVo = assembleOrderItemVo(orderItem);
            orderItemVoList.add(orderItemVo);
        }
        orderVo.setOrderItemVoList(orderItemVoList);
        return orderVo;
    }

    // 2.获取订单的商品信息
    @Override
    public ServerResponse getOrderCartProduct(Integer userId) {
        OrderProductVo orderProductVo = new OrderProductVo();
        //获取当前用户的购物车信息
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        ServerResponse serverResponse = this.getCartOrderItem(userId,cartList);// 根据购物车和一个购物车相对应的产品信息封装订单列表
        if(!serverResponse.isSuccess())
        {
            return serverResponse;
        }
        List<Item> orderItemList = serverResponse.getList();  //拿出订单表来
        List<OrderItemVo> orderItemVoList = Lists.newArrayList();
        BigDecimal payment = new BigDecimal("0"); // 累加订单表里面的总金额
        for (Item orderItem : orderItemList)
        {
            payment = BigDecimalUtil.add(payment.doubleValue(),orderItem.getTotalPrice().doubleValue()); //累加
            orderItemVoList.add(assembleOrderItemVo(orderItem));
        }
        orderProductVo.setProductTotalPrice(payment);  //设置总金额
        orderProductVo.setOrderItemVoList(orderItemVoList);
        orderProductVo.setImageHost("localhost:8081/uploads/");
        return ServerResponse.createBySuccess(orderProductVo);
    }

    @Override
    public ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        // 获取当前用户的全部订单信息
        List<Ordered> orderedList = orderMapper.selectByUserId(userId);
        List<OrderVo> orderVoList = assembleOrderVoList(orderedList,userId);  // 查询所有的订单以及订单详情信息
        PageInfo pageInfo = new PageInfo(orderedList);
        pageInfo.setList(orderVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    // 查询所有的订单以及订单详情信息
    private List<OrderVo> assembleOrderVoList(List<Ordered> orderedList, Integer userId) {
        List<OrderVo> orderVoList = Lists.newArrayList();
        for (Ordered order : orderedList)
        {
            List<Item> orderItemList = Lists.newArrayList();
            if (userId==null)
            {   //管理员查询的时候，不需要传userId
                // 既然userid==null，所以根据orderNo来查询具体某一个订单详情
                orderItemList = itemMapper.getByOrderNo(order.getOrderNo()); // 根据订单号查询订单详情表中的详细信息
            }
            else
            {   // 根据订单号和userid查询订单详情表中的详细信息
                orderItemList = itemMapper.getByOrderNoUserId(order.getOrderNo(),userId);
            }
            OrderVo orderVo = assembleOrderVo(order,orderItemList);
            orderVoList.add(orderVo);
        }
        return orderVoList;
    }

    @Override
    public ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo) {
        // 根据userid和orderNo订单Id查询某一具体订单
        Ordered ordered = orderMapper.selectByUserIdAndOrderNo(userId,orderNo);
        if(ordered!=null)
        {   // 根据orderNo和userid获取该orderNo订单号对应的具体详情信息(从item表中获取)
            List<Item> orderItemList = itemMapper.getByOrderNoUserId(orderNo,userId);
            OrderVo orderVo = assembleOrderVo(ordered,orderItemList);
            return ServerResponse.createBySuccess(orderVo);
        }
        return ServerResponse.createByError("没有找到该订单");
    }

    @Override
    public ServerResponse<String> cancel(Integer userId, Long orderNo) {
        // 根据userid和orderNo订单Id查询某一具体订单
        Ordered order =  orderMapper.selectByUserIdAndOrderNo(userId,orderNo);
        if (order==null)
        {
            return ServerResponse.createByError("该用户此订单不存在");
        }
        else if(order.getStatus()!=10)  //   (10,"未支付")
        {
            return ServerResponse.createByError("已付款,无法取消订单");
        }
        Ordered updateOrder = new Ordered();
        updateOrder.setId(order.getId());
        updateOrder.setStatus(0);  //   0,"已取消"
        int cnt = orderMapper.updateByPrimaryKeySelective(updateOrder);
        if (cnt>0)
        {
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse<PageInfo> manageList(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Ordered> orderedList = orderMapper.selectAllOrder();  // 查询订单表里的所有属性
        List<OrderVo> orderVoList = this.assembleOrderVoList(orderedList,null); //管理员不需要
        PageInfo pageInfo = new PageInfo(orderedList);
        pageInfo.setList(orderVoList);
        return ServerResponse.createBySuccess(pageInfo);

    }

    @Override
    public ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Ordered order = orderMapper.selectByOrderNo(orderNo); //根据订单号查询订单信息
        if(order!=null)
        {
            List<Item> itemList = itemMapper.getByOrderNo(orderNo); //根据订单号查询item表中订单详情信息
            OrderVo orderVo = assembleOrderVo(order,itemList);
            PageInfo pageInfo = new PageInfo(Lists.newArrayList());
            pageInfo.setList(Lists.newArrayList(orderVo));
            return ServerResponse.createBySuccess(pageInfo);
        }
        return ServerResponse.createByError("订单不存在");
    }

    @Override
    public ServerResponse<OrderVo> manageDetail(Long orderNo) {
        // 现根据orderNo获取订单信息
        Ordered ordered = orderMapper.selectByOrderNo(orderNo);
        if(ordered!=null)
        {   // 再根据订单orderNo查询订单详情信息(item表)
            List<Item> itemList = itemMapper.getByOrderNo(orderNo);
            OrderVo orderVo = assembleOrderVo(ordered,itemList);
            return ServerResponse.createBySuccess(orderVo);
        }
        return ServerResponse.createByError("订单不存在");
    }

    @Override
    public ServerResponse<String> manageSendGoods(Long orderNo) {
        // 现根据orderNo获取订单信息(ordered表)
        Ordered ordered = orderMapper.selectByOrderNo(orderNo);
        if(ordered!=null)
        {
            if (ordered.getStatus()==20)   // 20,"已付款"
            {
                ordered.setStatus(40);  // 40,"已发货"
                ordered.setSendTime(new Date());
                orderMapper.updateByPrimaryKeySelective(ordered);
                return ServerResponse.createBySuccessMsg("发货成功");
            }
            else{  // 订单未付款，不能发货
                return ServerResponse.createByError("订单未付款，不能发货");
            }
        }
        return ServerResponse.createByError("订单不存在");
    }

    @Override
    public int getCount() {
        int cnt = 0;
        cnt = orderMapper.getCount();
        return cnt;
    }

    @Override
    public ServerResponse pay(Long orderNo, Integer id) {
        Map<String,String> resultMap = Maps.newHashMap();
        Ordered ordered = orderMapper.selectByUserIdAndOrderNo(id,orderNo);
        if(ordered==null)
        {
            return ServerResponse.createByError("用户没有该订单");
        }
        resultMap.put("orderNo",String.valueOf(ordered.getOrderNo()));

        // 订单号(必填)
        String outTradeNo = ordered.getOrderNo().toString();

        // 订单标题（必填）
        String subject = new StringBuilder().append("扫码支付，订单号:").append(outTradeNo).toString();

        // 订单总金额（必填）
        String totalAmount = ordered.getPayment().toString();

        // 订单不可打折金额
        String undisableAmount = "0";

        // 支付宝账号ID
        String sellerId="";

        // 订单描述
        String body = new StringBuilder().append("订单").append(outTradeNo).append("购买商品共").append(totalAmount).append("元").toString();

        // 商户操作员编号
        String operatorId = "test_operator_id";

        // 商户门店编号(必填)
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，填写购买商品详细信息
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();

        List<Item> itemList = itemMapper.getByOrderNoUserId(orderNo,id);
        for(Item item : itemList)
        {   // id、name、当前价格、数量
            GoodsDetail goods = GoodsDetail.newInstance(item.getProductId().toString(),item.getProductName(),
                    BigDecimalUtil.mul(item.getCurrentUnitPrice().doubleValue(),new Double(100).doubleValue()).longValue(),
                    item.getQuantity());
            goodsDetailList.add(goods);
        }
        // 创建扫码支付请求builder，设置请求参数
        // 订单标题、订单总金额、订单号、订单不可打折金额、支付宝账号ID、订单描述、商户操作员编号
        // 商户门店编号、业务扩展参数、支付超时、定义为120分钟、goodsDetailList
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder().
                setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undisableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl("支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置")
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
//        System.out.println("result: "+result);
//        System.out.println("response: "+result.getResponse());
        switch (result.getTradeStatus()){
            case SUCCESS:
                AlipayTradePrecreateResponse response = result.getResponse();
                // 需要修改运行机器上的路径
                // 细节调整
                String path = "F:/home/capture/uploads/";
                String qrPath = String.format(path+"/qr-%s.png",response.getOutTradeNo());
                String qrFileName = String.format("qr-%s.png",response.getOutTradeNo());
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, qrPath);  //生成一个二维码

                File targetFile = new File(path,qrFileName);
                String qrUrl = "localhost:8081/uploads/"+targetFile.getName();
                resultMap.put("qrUrl",qrUrl);
                return ServerResponse.createBySuccess(resultMap);
            case FAILED:
                return ServerResponse.createByError(1,"支付宝生成订单失败");
            case UNKNOWN:
                return ServerResponse.createByError(1,"系统异常，预下单状态未知!!!");
            default:
                return ServerResponse.createByError("不支持的交易状态，交易返回异常!!!");
        }

    }

    @Override
    public ServerResponse queryOrderPayStatus(Integer userId, Long orderNo) {
        Ordered ordered = orderMapper.selectByUserIdAndOrderNo(userId,orderNo);
        if(ordered==null)
        {
            return ServerResponse.createByError("用户没有该订单");
        }
        if (ordered.getStatus()>=20)  //  (20,"已付款")
        {
            return ServerResponse.createBySuccess(true);
        }
        return ServerResponse.createByError(false);
    }

    @Override
    public ServerResponse aliCallback(Map<String, String> params) {
        try
        {
            Long orderNo = Long.parseLong(params.get("out_trade_no"));
            String tradeNo = params.get("trade_no");
            String tradeStatus = params.get("trade_status");
            Ordered ordered = orderMapper.selectByOrderNo(orderNo);
            if (ordered==null)
            {
                return ServerResponse.createByError("非快乐慕商城的订单,回调忽略");
            }
            if (ordered.getStatus()>=20)  //   (20,"已付款")
            {
                return ServerResponse.createBySuccessMsg("支付包重复调用");
            }
            if("TRADE_SUCCESS".equals(tradeStatus));  // TRADE_SUCCESS
            {
                ordered.setPaymentTime(new Date());
                ordered.setStatus(20);   // (20,"已付款")
                orderMapper.updateByPrimaryKeySelective(ordered);
            }
            Pay payInfo = new Pay();
            payInfo.setUserId(ordered.getUserId());  // 该订单所属的用户
            payInfo.setOrderNo(ordered.getOrderNo());  // 该订单的订单号
            payInfo.setPayPlatform(1);  // (1,"支付宝")
            payInfo.setPlatformNumber(tradeNo); // 支付宝支付流水号
            payInfo.setPlatformStatus(tradeStatus);  // 支付宝支付状态
            payInfo.setCreateTime(new Date());
            payInfo.setUpdateTime(new Date());

            payMapper.insert(payInfo);
        }catch (Exception e)
        {
            return ServerResponse.createByError();
        }
        return ServerResponse.createBySuccess();

    }


}
