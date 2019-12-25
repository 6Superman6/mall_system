package demo.service;

import com.github.pagehelper.PageInfo;
import demo.model.Shipping;
import demo.service.base.BaseService;
import demo.utils.ServerResponse;

public interface ShippingService extends BaseService<Shipping> {

    // 查看当前用户的地址列表
    ServerResponse<PageInfo> list(Integer userId,int pageNum,int pageSize);

}
