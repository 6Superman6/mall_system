package demo.service;

import demo.model.Category;
import demo.service.base.BaseService;
import demo.utils.ServerResponse;

import java.util.List;

public interface CategoryService extends BaseService<Category> {

    // 1.获取品类子节点(平级)
    ServerResponse get_category(int parentId);

    // 2.增加节点
    ServerResponse add_category(String parentId,String categoryName);

    // 4.获取当前分类id及递归子节点categoryId
    ServerResponse selectChildId(int categoryId);
}
