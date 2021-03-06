package demo.mapper;

import demo.model.User;
import demo.model.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    int countByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    int deleteByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    List<User> selectByExample(UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    User selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    int updateByPrimaryKey(User record);

    //验证登录
    User login(@Param("username") String username,@Param("password") String password);

    //检查用户名是否有效----username
    int getOkUsername(String username);

    //检查用户名是否有效----password
    int getOkEmail(String email);

    //忘记密码--5
    String forget_pw(String username);

    //6.提交问题答案
    int check_answer(@Param("username") String username,@Param("question") String question,@Param("answer") String answer);

    //提交答案成功之后插入一个token值
    void setToken(@Param("id") int id,@Param("token") String token);

    int getCountByToken(String token);

    //忘记密码的重设密码
    void reset_password(@Param("username") String username,@Param("password") String password);

    //8.登录中状态重置密码
    void OnUpPw(@Param("id") int id,@Param("password") String password,@Param("passwordNew") String passwordNew);

    //9.登录状态更新个人信息   updateByPrimaryKey

    //10.获取当前登录用户的详细信息，并强制登录
//    {
//        "status": 10,
//         msg": "用户未登录,无法获取当前用户信息,status=10,强制登录"
//    }

    // 11.退出登录 /user/logout.do

    // 管理员查看用户列表
    List<User> list();

    int getCount();



}