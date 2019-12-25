package demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import demo.model.Category;
import demo.model.Product;
import demo.model.ProductWithBLOBs;
import demo.service.CategoryService;
import demo.service.ProductService;
import demo.service.base.BaseServiceImpl;
import demo.utils.Const;
import demo.utils.DateTimeUtil;
import demo.utils.ServerResponse;
import demo.vo.ProductDetailVo;
import demo.vo.ProductListVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {

    @Autowired
    private CategoryService categoryService;

    @Override
    public ServerResponse insert(Product product) {
        return null;
    }

    @Override
    public ServerResponse delete(int id) {
        return null;
    }

    @Override
    public ServerResponse update(Product product) {

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

    @Override
    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectList();
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product product : productList)
        {
            ProductListVo productListVo = assembleProductListVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);

    }

    @Override
    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        if(StringUtils.isNotBlank(productName))
        {
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName,productId);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for(Product product : productList)
        {
            ProductListVo productListVo = assembleProductListVo(product);
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    private ProductListVo assembleProductListVo(Product product) {
        ProductListVo productListVo = new ProductListVo();
        productListVo.setId(product.getId());
        productListVo.setName(product.getName());
        productListVo.setCategoryId(product.getCategoryId());
        productListVo.setImageHost("localhost:8081/uploads/");  //设置图片的访问路径
        productListVo.setMainImage(product.getMainImage());
        productListVo.setPrice(product.getPrice());
        productListVo.setSubtitle(product.getSubtitle());
        productListVo.setStatus(product.getStatus());
        return productListVo;
    }

    @Override
    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId) {
        if (productId==null)
        {
            return ServerResponse.createByError("参数错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product==null)
        {
            return ServerResponse.createByError("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVo);
    }

    private ProductDetailVo assembleProductDetailVo(Product product){
        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImages(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHost("localhost:8081/uploads/");

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId(0);//默认根节点
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }

    @Override
    public ServerResponse setStatus(Integer productId, Integer status) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product==null)
        {
            return ServerResponse.createByError("修改产品状态失败,productId不存在");
        }
        product.setStatus(status);
        try
        {
            productMapper.updateByPrimaryKey(product);
            return ServerResponse.createBySuccessMsg("修改产品状态成功");
        }catch (Exception e)
        {
            return ServerResponse.createByError("修改产品状态失败");
        }
    }

    @Override
    public ServerResponse add(ProductWithBLOBs productWithBLOBs) {
        if(StringUtils.isNotBlank(productWithBLOBs.getSubImages()))
        {
            String[] subImageArray= productWithBLOBs.getSubImages().split(",");
            if(subImageArray.length>0)
            {
                productWithBLOBs.setMainImage(subImageArray[0]);
            }
        }
        if(productWithBLOBs.getId()==null)
        {
            productWithBLOBs.setCreateTime(new Date());
            productWithBLOBs.setUpdateTime(new Date());
            int count = 0;
            try {
                count = productMapper.insert(productWithBLOBs);
            }catch (Exception e)
            {
                return ServerResponse.createByError("categoryId不存在");
            }
            if (count>0)
            {
                return ServerResponse.createBySuccessMsg("新增产品成功");
            }
            return ServerResponse.createByError("新增产品失败");
        }
        else
        {
            int count = productMapper.getCountById(productWithBLOBs.getId());
            if(count==0)
            {
                return ServerResponse.createByError("更新产品失败,id不存在");
            }
            Product product = productMapper.selectByPrimaryKey(productWithBLOBs.getId());
            if (productWithBLOBs.getCategoryId()!=null&&product.getCategoryId()!=productWithBLOBs.getCategoryId())
            {
                product.setCategoryId(productWithBLOBs.getCategoryId());
            }
            if(productWithBLOBs.getName()!=null&&!product.getName().equals(productWithBLOBs.getName()))
            {
                product.setName(productWithBLOBs.getName());
            }
            if(productWithBLOBs.getSubtitle()!=null&&!productWithBLOBs.getSubtitle().equals(product.getSubtitle()))
            {
                product.setSubtitle(productWithBLOBs.getSubtitle());
            }
            if(productWithBLOBs.getMainImage()!=null&&!productWithBLOBs.getMainImage().equals(product.getMainImage()))
            {
                product.setMainImage(productWithBLOBs.getMainImage());
            }
            if (productWithBLOBs.getSubImages()!=null&&!product.getSubImages().equals(productWithBLOBs.getSubImages()))
            {
                product.setSubImages(productWithBLOBs.getSubImages());
            }
            if(productWithBLOBs.getDetail()!=null&&!product.getDetail().equals(productWithBLOBs.getDetail()))
            {
                product.setDetail(productWithBLOBs.getDetail());
            }
            if(productWithBLOBs.getPrice()!=null)
            {
                product.setPrice(productWithBLOBs.getPrice());
            }
            if(productWithBLOBs.getStock()!=null)
            {
                product.setStock(productWithBLOBs.getStock());
            }
            if(productWithBLOBs.getStatus()!=null)
            {
                product.setStatus(productWithBLOBs.getStatus());
            }
            product.setUpdateTime(new Date());
            int cnt = 0;
            try
            {
                cnt = productMapper.updateByPrimaryKey(product);
            }catch (Exception e)
            {
                return ServerResponse.createByError("categoryId不存在");
            }
            if (cnt>0)
            {
                return ServerResponse.createBySuccessMsg("更新产品成功");
            }
            return ServerResponse.createByError("更新产品失败");
        }
    }

    @Override
    public ServerResponse updateImageByid(Integer id, String mainImage) {
        if(id==null||mainImage==null)
        {
            return ServerResponse.createByError("参数错误，图片上传失败");
        }
        int cnt = 0;
        try
        {
            cnt = productMapper.updateImageByid(id,mainImage);
        }catch (Exception e)
        {
            return ServerResponse.createByError("图片上传失败");
        }
        if (cnt>0)
        {
            Map fileMap = Maps.newHashMap();
            fileMap.put("file_path",mainImage);
            fileMap.put("msg","上传成功");
            fileMap.put("success",true);
            return ServerResponse.createBySuccess(fileMap);
        }
        Map fileMap = Maps.newHashMap();
        fileMap.put("file_path","[real file path]");
        fileMap.put("msg","error message");
        fileMap.put("success",false);
        return ServerResponse.createByError(fileMap);
    }

    @Override
    public int getCounyByid(int id) {
        return productMapper.getCountById(id);
    }

    // 1.产品搜索及动态排序List
    @Override                                                   // keyword 关键字
    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId, int pageNum, int pageSize, String orderBy) {
        if(StringUtils.isBlank(keyword)&&categoryId==null)  //关键字keyword不存在并且categoryId为null
        {
            return ServerResponse.createByError("参数错误");
        }
        List<Integer> categoryIdList = new ArrayList<Integer>();
        if (categoryId!=null)
        {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);   // 获取分类表中的数据
            if(category==null&&StringUtils.isBlank(keyword))  // 关键字keyword为空
            {
                //没有该分类,并且还没有关键字,这个时候返回一个空的结果集,不报错
                PageHelper.startPage(pageNum,pageSize);  //设置页数和数据数量
                List<ProductListVo> productListVoList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVoList);
                return ServerResponse.createBySuccess(pageInfo);  //返回的pageInfo内容为空
            }
            // 获取当前categoryId的子集
            categoryIdList = (List<Integer>) categoryService.selectChildId(category.getId()).getData();
        }
        if(StringUtils.isNotBlank(keyword))  // 如果关键字keyword为空  ，赋值
        {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }

        PageHelper.offsetPage(pageNum,pageSize);
        //排序处理
        if(StringUtils.isNotBlank(orderBy))  // orderBy不为空
        {       //PRICE_ASC_DESC的内容为"price_desc","price_asc"
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) // orderBy不为空时，判断是否包含其中
            {
                String[] orderByArray = orderBy.split("_");   // 将orderBy根据“-”分割
                PageHelper.orderBy(orderByArray[0]+" "+orderByArray[1]);
            }
        }                                                                       //keyword 相当于 productName
        List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword)?null:keyword,categoryIdList.size()==0?null:categoryIdList);
        List<ProductListVo> productListVoList = Lists.newArrayList();
        for (Product product : productList)  //封装vo
        {
            ProductListVo productListVo = assembleProductListVo(product);//封装进vo
            productListVoList.add(productListVo);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVoList);
        return ServerResponse.createBySuccess(pageInfo);
    }

    @Override
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId) {
        if (productId==null)
        {
            return ServerResponse.createByError("参数错误");
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product==null)
        {
            return ServerResponse.createByError("产品已下架或者删除");
        }   //也就是status!=1说明产品已下架，ON_SALE（1,"在线"）
        else if(product.getStatus()!=Const.ProductStatusEnum.ON_SALE.getCode())  //code=1
        {
            return ServerResponse.createByError("产品已下架或者删除");
        }
        ProductDetailVo productDetailVo =assembleProductDetailVo(product);  //将product数据封装到ProductDetailVo中
        return ServerResponse.createBySuccess(productDetailVo);
    }

    @Override
    public int getCount() {
        int cnt = 0;
        cnt = productMapper.getCount();
        return cnt;
    }


}
