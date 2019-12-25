package demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import demo.model.User;
import demo.service.UserService;
import demo.service.base.BaseServiceImpl;
import demo.utils.ServerResponse;
import demo.utils.TokenCache;
import demo.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("userService")
@Transactional  // 回滚(事务)
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {


    @Override
    public ServerResponse insert(User user) {
        try
        {
            userMapper.insert(user);
            return ServerResponse.createBySuccessMsg("注册成功");
        }catch (Exception e)
        {
            return ServerResponse.createByError("用户已存在");
        }
    }

    @Override
    public ServerResponse delete(int id) {
        return null;
    }

    @Override
    public ServerResponse update(User user) {
        try
        {
            userMapper.updateByPrimaryKey(user);
            return ServerResponse.createBySuccessMsg("更新个人信息成功");
        }catch (Exception e)
        {
            return ServerResponse.createByError("更新失败");
        }
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
    public ServerResponse login(String username, String password) {
        try
        {
            User user = userMapper.login(username,password);
            if (user==null)
            {
                return ServerResponse.createByError("密码错误");
            }
//            System.out.println("login: "+user);
            return ServerResponse.createBySuccess(user);
        }catch (Exception e)
        {
            return ServerResponse.createByError("密码错误");

        }
    }

    @Override
    public ServerResponse getOkUsername(String username) {
        int sum = userMapper.getOkUsername(username);
        if(sum!=0)
        {
            return ServerResponse.createBySuccessMsg("校验成功");
        }
        return ServerResponse.createByError("校验失败");
    }

    @Override
    public ServerResponse getOkEmail(String email) {
        int sum = userMapper.getOkEmail(email);
        if(sum!=0)
        {
            return ServerResponse.createBySuccessMsg("校验成功");
        }
        return ServerResponse.createByError("校验失败");
    }

    @Override
    public ServerResponse forget_pw(String username) {
        String question = userMapper.forget_pw(username);
        System.out.println("username: "+username+" question: "+question);
        if(question!=null)
        {
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByError("该用户未设置找回密码问题");
    }

    //我自己的方法
//    @Override
//    public ServerResponse check_answer(String username, String question, String answer) {
//        try
//        {
//            int id = userMapper.check_answer(username,question,answer);
//            if(id>0)
//            {
//                String token = ""+id;
//                try
//                {
//                    // 把文件的名称设置唯一值，uuid
//                    String uuid = UUID.randomUUID().toString().replace("-", "");
//                    token = uuid+"-"+token;
//                    userMapper.setToken(id,token);
//                }catch (Exception e)
//                {
//                    return ServerResponse.createByError("问题答案错误");
//                }
//                return ServerResponse.createBySuccess(token);
//            }
//        }catch (Exception e)
//        {
//            return ServerResponse.createByError("问题答案错误");
//        }
//        return ServerResponse.createByError("问题答案错误");
//    }
    //我自己的方法

    // token
    @Override
    public ServerResponse check_answer(String username, String question, String answer) {
        try
        {
            int id = userMapper.check_answer(username,question,answer);
            if(id>0)
            {
                //说明问题及问题答案是这个用户的,并且是正确的
                String forgetToken = UUID.randomUUID().toString();
                TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
                return ServerResponse.createBySuccess(forgetToken);
            }
        }catch (Exception e)
        {
            return ServerResponse.createByError("问题答案错误");
        }
        return ServerResponse.createByError("问题答案错误");
    }
    // token

    @Override
    public void setToken(int id, String token) {

    }
    //我自己的方法
//    @Override
//    public ServerResponse reset_password(String username, String password, String token) {
//        int count = 0;
//        count = userMapper.getCountByToken(token);
//        if(count==0)
//        {
//            return ServerResponse.createByError("token已经失效");
//        }
//        int cnt=0;
//        cnt = userMapper.getOkUsername(username);
//        if(cnt==0)
//        {
//            return ServerResponse.createByError("修改密码操作失效");
//        }
//        try
//        {
//            userMapper.reset_password(username,password,token);
//            return ServerResponse.createBySuccessMsg("修改密码成功");
//        }catch (Exception e)
//        {
//            return ServerResponse.createByError("修改密码操作失效");
//        }
//    }
    //我自己的方法

    // token
    @Override
    public ServerResponse reset_password(String username, String password, String token) {
        if(org.apache.commons.lang3.StringUtils.isBlank(token)){
            return ServerResponse.createByError("参数错误,token需要传递");
        }
        int cnt=0;
        cnt = userMapper.getOkUsername(username);
        if(cnt==0)
        {
            return ServerResponse.createByError("用户名不存在");
        }
        String token2 = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
        if(org.apache.commons.lang3.StringUtils.isBlank(token2)){
            return ServerResponse.createByError("token无效或者过期");
        }
        if(org.apache.commons.lang3.StringUtils.equals(token2,token)){
            try
            {
                userMapper.reset_password(username,password);
                return ServerResponse.createBySuccessMsg("修改密码成功");
            }catch (Exception e)
            {
                return ServerResponse.createByError("修改密码失败");
            }

        }else{
            return ServerResponse.createByError("token错误,请重新获取重置密码的token");
        }
    }
    // token

    @Override
    public ServerResponse OnUpPw(String password, String passwordNew,User user) {

        if (user==null)
        {
            return ServerResponse.createByError("未登录");
        }
        if(!user.getPassword().equals(password))
        {
            return ServerResponse.createByError("旧密码输入错误");
        }
        try
        {
            userMapper.OnUpPw(user.getId(),password,passwordNew);
            return ServerResponse.createBySuccessMsg("修改密码成功");
        }catch (Exception e)
        {
            e.printStackTrace();
            return ServerResponse.createByError("修改失败");
        }

    }

    // 检验是否是管理原
    public boolean checkAdmin(int id)
    {
        if(id==1)     //是管理员
        {
            return true;
        }
        return false;
    }

    @Override
    public ServerResponse<PageInfo> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = userMapper.list();
        List<UserVo> userVoList =Lists.newArrayList();
        assemble(userList,userVoList);  //封装到UserVo中
        PageInfo pageInfo = new PageInfo(userList);
        pageInfo.setList(userVoList);
        return ServerResponse.createBySuccess(pageInfo);

    }

    @Override
    public int getCount() {
        int cnt = 0;
        cnt = userMapper.getCount();
        return cnt;
    }

    private void assemble(List<User> userList, List<UserVo> userVoList) {
        for (User user : userList)
        {
            UserVo userVo = new UserVo();
            userVo.setId(user.getId());
            userVo.setUsername(user.getUsername());
            userVo.setPassword(user.getPassword());
            userVo.setEmail(user.getEmail());
            userVo.setPhone(user.getPhone());
            userVo.setQuestion(user.getQuestion());
            userVo.setAnswer(user.getAnswer());
            userVo.setRole(user.getRole());
            userVo.setCreateTime(user.getCreateTime());
            userVo.setUpdateTime(user.getUpdateTime());
            userVoList.add(userVo);
        }

    }

}
