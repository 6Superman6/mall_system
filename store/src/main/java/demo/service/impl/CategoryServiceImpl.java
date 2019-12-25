package demo.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import demo.model.Category;
import demo.service.CategoryService;
import demo.service.base.BaseServiceImpl;
import demo.utils.ServerResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service("categoryService")
@Transactional
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

    @Override
    public ServerResponse get_category(int parentId) {

        List<Category> list = null;
        list = categoryMapper.get_category(parentId);
        if(list==null)
        {
            return ServerResponse.createByError("未找到该品类");
        }
        return ServerResponse.createBySuccess(list);
    }

    @Override
    public ServerResponse add_category(String parentId, String categoryName) {
        if(parentId == null||parentId.length()==0 || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByError("添加品类参数错误");
        }
        Category category = new Category();
        category.setParentId(Integer.parseInt(parentId));
        category.setName(categoryName);
        category.setStatus(true);
        try
        {
            categoryMapper.insert(category);
            return ServerResponse.createBySuccessMsg("添加品类成功");
        }catch (Exception e)
        {
            e.printStackTrace();
            return ServerResponse.createByError("添加品类失败");
        }
    }

    @Override
    public ServerResponse insert(Category category) {
        return null;
    }

    @Override
    public ServerResponse delete(int id) {
        return null;
    }

    @Override
    public ServerResponse update(Category category) {

        try
        {
            categoryMapper.updateByPrimaryKey(category);
            return ServerResponse.createBySuccessMsg("更新品类名字成功");
        }catch (Exception e)
        {
            e.printStackTrace();
            return ServerResponse.createByError("更新品类名字失败");
        }
    }

    @Override
    public ServerResponse get(int id) {
        Category category = null;
        category = categoryMapper.selectByPrimaryKey(id);
        return ServerResponse.createBySuccess(category);
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

    /**
     * 递归查询本节点的id及孩子节点的id
     * @param categoryId
     * @return
     */
    @Override
    public ServerResponse selectChildId(int categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet,categoryId);

        List<Integer> list = Lists.newArrayList();
        Integer id = new Integer(categoryId);
        if(id!=null)
        {
            for (Category category : categorySet)
            {
                list.add(category.getId());
            }
        }
        if(list.isEmpty())
        {
            return ServerResponse.createBySuccessMsg("无子节点信息");
        }
        return ServerResponse.createBySuccess(list);

    }

    //利用递归算出子节点
    private  Set<Category> findChildCategory(Set<Category> categorySet,int categoryId)
    {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category!=null)
        {
            categorySet.add(category);
        }
        //查找子节点，递归
        List<Category> categoryList = categoryMapper.get_category(categoryId);
        for(Category category1 : categoryList)
        {
            findChildCategory(categorySet,category1.getId());
        }
        return categorySet;
    }


}
