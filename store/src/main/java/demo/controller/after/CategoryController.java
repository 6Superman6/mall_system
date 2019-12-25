package demo.controller.after;

import demo.model.Category;
import demo.model.User;
import demo.service.CategoryService;
import demo.service.UserService;
import demo.utils.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.PipedOutputStream;

/**
 * 后台_品类接口
 */
@RestController
@RequestMapping(path = "/manage/category")
public class CategoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    // 1.获取品类子节点(平级)
    //要想获取子节点的产品，只需要让它的父母parenti=当前传过来的id即可
    @RequestMapping(path = "/get_category")
    public ServerResponse get_category(@RequestParam(value = "categoryId",defaultValue = "0") String categoryId, HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user==null)
        {
            return ServerResponse.createByError(10,"用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))   //检验是否为管理员
        {
            if(categoryId==null||categoryId.length()==0)
            {
                return ServerResponse.createByError("参数错误");
            }

            return categoryService.get_category(Integer.parseInt(categoryId));
        }
        else
        {
            return ServerResponse.createByError("无权限操作,需要管理员权限");
        }
    }

    // 2.增加节点
    @RequestMapping(path = "/add_category",method = RequestMethod.POST)
    public ServerResponse add_category(@RequestParam("parentId") String parentId,@RequestParam("categoryName") String categoryName,
                                       HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user==null)
        {
            return ServerResponse.createByError(10,"用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))   //检验是否为管理员
        {
            return categoryService.add_category(parentId,categoryName);
        }
        else
        {
            return ServerResponse.createByError("无权限操作,需要管理员权限");
        }
    }

    // 3.修改品类名字
    @RequestMapping(path = "/set_category_name",method = RequestMethod.POST)
    public ServerResponse set_category_name(@RequestParam("categoryId") String categoryId,@RequestParam("categoryName") String categoryName,
                                            HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user==null)
        {
            return ServerResponse.createByError(10,"用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))   //检验是否为管理员
        {
            if(categoryId==null||categoryId.length()==0)
            {
                return ServerResponse.createByError("参数错误");
            }
            ServerResponse serverResponse = categoryService.get(Integer.parseInt(categoryId));
            Category category = (Category) serverResponse.getData();
            if(category!=null)
            {
                if(categoryName!=null&&categoryName.length()!=0&&!categoryName.equals(category.getName()))
                {
                    category.setName(categoryName);
                    return categoryService.update(category);
                }
            }
        }
        else
        {
            return ServerResponse.createByError("无权限操作,需要管理员权限");
        }
        return ServerResponse.createByError("更新品类名字失败");
    }

    // 4.获取当前分类id及递归子节点categoryId
    @RequestMapping(path = "get_deep_category")
    public ServerResponse get_deep_category(@RequestParam("categoryId") String categoryId,HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user==null)
        {
            return ServerResponse.createByError(10,"用户未登录,请登录");
        }
        if(userService.checkAdmin(user.getRole()))   //检验是否为管理员
        {
            if(categoryId==null||categoryId.length()==0)
            {
                return ServerResponse.createByError("参数错误");
            }
            return categoryService.selectChildId(Integer.parseInt(categoryId));
        }
        else
        {
            return ServerResponse.createByError("无权限操作,需要管理员权限");
        }
    }

}
