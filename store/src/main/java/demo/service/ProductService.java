package demo.service;

import com.github.pagehelper.PageInfo;
import demo.model.Product;
import demo.model.ProductWithBLOBs;
import demo.service.base.BaseService;
import demo.utils.ServerResponse;
import demo.vo.ProductDetailVo;

public interface ProductService extends BaseService<Product> {

    // 1.产品list
    ServerResponse<PageInfo> getProductList(int pageNum,int pageSize);

    // 2.产品搜索
    ServerResponse<PageInfo> searchProduct(String productName,Integer productId,int pageNum,int pageSize);

    // 4.产品详情
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    // 5.产品上下架
    ServerResponse setStatus(Integer productId,Integer status);

    // 6--add
    ServerResponse add(ProductWithBLOBs productWithBLOBs);

    // 7.富文本上传图片
    public ServerResponse updateImageByid(Integer id,String mainImage);
    int getCounyByid(int id);

    // 1.产品搜索及动态排序List
    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy);

    // 2.产品detail
    public ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    //统计个数
    int getCount();

}
