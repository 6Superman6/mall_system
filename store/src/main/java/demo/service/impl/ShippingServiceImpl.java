package demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import demo.model.Shipping;
import demo.service.ShippingService;
import demo.service.base.BaseServiceImpl;
import demo.utils.ServerResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShippingServiceImpl extends BaseServiceImpl<Shipping> implements ShippingService {
    @Override
    public ServerResponse insert(Shipping shipping) {
        try
        {
            shippingMapper.insert(shipping);
            int cnt = 0;
            cnt = shippingMapper.MaxId();
            return ServerResponse.createBySuccess("新建地址成功",cnt);
        }catch (Exception e)
        {
            return ServerResponse.createByError(1,"新建地址失败");
        }
    }

    @Override
    public ServerResponse delete(int id) {
        int cnt =0;
        cnt = shippingMapper.getCountById(id);
        if(cnt==0)
        {
            return ServerResponse.createByError("该shippingid不存在");
        }
        try {
            shippingMapper.deleteByPrimaryKey(id);
            return ServerResponse.createBySuccessMsg("删除地址成功");
        }catch (Exception e)
        {
            return ServerResponse.createByError("删除地址失败");
        }
    }

    @Override
    public ServerResponse update(Shipping shipping) {
        int cnt = 0;
        cnt = shippingMapper.getCountById(shipping.getId());
        if (cnt==0)
        {
            return ServerResponse.createByError("该shippingid不存在");
        }
        try
        {
            shippingMapper.updateByPrimaryKeySelective(shipping);
            return ServerResponse.createBySuccessMsg("更新地址成功");
        }catch (Exception e)
        {
            return ServerResponse.createByError("更新地址失败");
        }
    }

    @Override
    public ServerResponse get(int id) {
        int cnt = 0;
        cnt = shippingMapper.getCountById(id);
        if (cnt==0)
        {
            return ServerResponse.createByError("该shippingid不存在");
        }
        Shipping shipping = shippingMapper.selectByPrimaryKey(id);
        return ServerResponse.createBySuccess(shipping);

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

    @Override
    public ServerResponse<PageInfo> list(Integer userId,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> list = Lists.newArrayList();
        list = shippingMapper.list(userId);
        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setList(list);
        return ServerResponse.createBySuccess(pageInfo);
    }
}
