package demo.service.base;

import demo.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    public UserMapper userMapper;

    @Autowired
    public CategoryMapper categoryMapper;

    @Autowired
    public ProductMapper productMapper;

    @Autowired
    public CartMapper cartMapper;

    @Autowired
    public OrderedMapper orderMapper;

    @Autowired
    public ItemMapper itemMapper;

    @Autowired
    public ShippingMapper shippingMapper;

    @Autowired
    public PayMapper payMapper;




}
