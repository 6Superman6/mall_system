package demo.controller.after;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
import com.google.common.collect.Maps;
import demo.model.Product;
import demo.model.ProductWithBLOBs;
import demo.model.User;
import demo.service.ProductService;
import demo.service.UserService;
import demo.utils.ImaTool;
import demo.utils.ServerResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;

/**
 * 后台产品接口
 */
@RestController
@RequestMapping(path = "/manage/product")
public class ProductController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    // 1.产品list
    @RequestMapping(path = "/list")
    public ServerResponse list(HttpSession session, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum, @RequestParam(value = "pageSize",defaultValue = "10") int pageSize)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError("用户未登录,请登录");
        }

        if(userService.checkAdmin(user.getRole()))   //检验是否为管理员
        {
            return productService.getProductList(pageNum,pageSize);
        }
        else
        {
            return ServerResponse.createByError("无权限操作,需要管理员权限");
        }

    }

    // 2.产品搜索
    @RequestMapping(path = "/search")
    public ServerResponse search(HttpSession session,String productName, Integer productId,
                                 @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                 @RequestParam(value = "pageSize",defaultValue = "10") int pageSize)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError("用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))   //检验是否为管理员
        {
            return productService.searchProduct(productName,productId,pageNum,pageSize);
        }
        else
        {
            return ServerResponse.createByError("无权限操作,需要管理员权限");
        }

    }

    // 3.图片上传
    @RequestMapping(path = "/upload",method = RequestMethod.POST)
    public ServerResponse upload(HttpSession session, HttpServletRequest request,@RequestParam(value = "upload_file",required = false) MultipartFile file)
    {
        User user = (User) session.getAttribute("user");
        User user1 = (User) request.getSession().getAttribute("user");

        if (user==null)
        {
            return ServerResponse.createByError("用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))   //检验是否为管理员
        {
            String filename = ImaTool.Imagetool(request,file);
            if (filename!=null)
            {
//                System.out.println("file: "+filename);
                String url = "localhost:8081/uploads/"+filename;
                Map fileMap = Maps.newHashMap();
                fileMap.put("uri",filename);
                fileMap.put("url",url);
                return ServerResponse.createBySuccess(fileMap);
            }
        }
        return ServerResponse.createByError("无权限操作,需要管理员权限");
    }

    // 4.产品详情
    @RequestMapping(path = "/detail")
    public ServerResponse detail(HttpSession session, @RequestParam("productId") Integer productId)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError("用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))   //检验是否为管理员
        {
            return productService.manageProductDetail(productId);
        }
        else
        {
            return ServerResponse.createByError("无权限操作,需要管理员权限");
        }
    }

    // 5.产品上下架
    @RequestMapping(path = "/set_sale_status")
    public ServerResponse set_sale_status(HttpSession session,@RequestParam("productId") Integer productId,@RequestParam("status") Integer status)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError("用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))   //检验是否为管理员
        {
            if(productId==null||status==null)
            {
                return ServerResponse.createByError("参数错误");
            }

            return productService.setStatus(productId,status);
        }
        else
        {
            return ServerResponse.createByError("无权限操作,需要管理员权限");
        }
    }

    // 6.新增OR更新产品
    @RequestMapping(path = "/save",method = RequestMethod.POST)
    public ServerResponse save(HttpSession session, @Valid ProductWithBLOBs productWithBLOBs)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError("用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))   //检验是否为管理员
        {
            if(productWithBLOBs==null)
            {
                return ServerResponse.createByError("参数错误");
            }
            return productService.add(productWithBLOBs);
        }
        else
        {
            return ServerResponse.createByError("无权限操作,需要管理员权限");
        }
    }

    // 7.富文本上传图片
    @RequestMapping(path = "/richtext_img_upload",method = RequestMethod.POST)
    public ServerResponse richtext_img_upload(HttpSession session, HttpServletRequest request,@RequestParam("id") Integer id,
                                              @RequestParam(value = "upload_file",required = false) MultipartFile file)
    {
        User user = (User) session.getAttribute("user");
        if (user==null)
        {
            return ServerResponse.createByError("用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))   //检验是否为管理员
        {
            if(id!=null)
            {
                int cnt = productService.getCounyByid(id);
                if (cnt==0)
                {
                    return ServerResponse.createByError("Id不存在");
                }
            }
            String filename = ImaTool.Imagetool(request,file);
            if (filename!=null)
            {
//                System.out.println("file: "+filename);
                String url = "localhost:8081/uploads/"+filename;
                return productService.updateImageByid(id,url);
            }
        }
        else
        {
            return ServerResponse.createByError("无权限操作,需要管理员权限");
        }
        Map fileMap = Maps.newHashMap();
        fileMap.put("file_path","[real file path]");
        fileMap.put("msg","error message");
        fileMap.put("success",false);
        return ServerResponse.createByError(fileMap);
    }




}
