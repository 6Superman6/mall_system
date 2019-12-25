package demo.service;

import com.github.pagehelper.PageInfo;
import demo.model.User;
import demo.service.base.BaseService;
import demo.utils.ServerResponse;

public interface UserService extends BaseService<User>
{
    //验证登录
    ServerResponse login(String username,String password);

    //检查用户名是否有效----username
    ServerResponse getOkUsername(String username);

    //检查用户名是否有效----passwork
    ServerResponse getOkEmail(String email);

    //忘记密码--5
    ServerResponse forget_pw(String username);

    //6.提交问题答案
    ServerResponse check_answer(String username,String question,String answer);

    //提交答案成功之后插入一个token值
    void setToken(int id,String token);

    //忘记密码的重设密码
    ServerResponse reset_password( String username,String password,String token);

    //8.登录中状态重置密码
    ServerResponse OnUpPw(String password,String passwordNew,User user);

    //9.登录状态更新个人信息   updateByPrimaryKey

    //10.获取当前登录用户的详细信息，并强制登录
//    {
//        "status": 10,
//         msg": "用户未登录,无法获取当前用户信息,status=10,强制登录"
//    }

    // 11.退出登录 /user/logout.do

    // 检验是否为管理员
    public boolean checkAdmin(int id);

    //管理员查看用户列表
    public ServerResponse<PageInfo> list(int pageNum, int pageSize);

    // 统计用户个数
    int getCount();





}
