package demo.controller.before;

import demo.model.User;
import demo.service.ProductService;
import demo.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 前台产品接口
 */
@RestController
@RequestMapping(path = "/product")
public class BeforeProductController {

    @Autowired
    private ProductService productService;

    // 1.产品搜索及动态排序List
    @RequestMapping(path = "/list")
    public ServerResponse list(HttpSession session, @RequestParam(value = "categoryId",required = false) Integer categoryId,@RequestParam(value = "keyword",required = false) String keyword,
                               @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize,
                               @RequestParam(value = "orderBy",defaultValue = "") String orderBy)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError(10,"用户未登录，请登录");
        }
        return productService.getProductByKeywordCategory(keyword,categoryId,pageNum,pageSize,orderBy);

    }

    // 2.产品detail
    @RequestMapping(path = "/detail")
    public ServerResponse detail(HttpSession session,Integer productId)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError("用户未登录,请登录");
        }
        return productService.getProductDetail(productId);



    }


}
