package demo.model;

import java.util.Date;

// 购物车
public class Cart {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.id
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.user_id
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.product_id
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    private Integer productId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.quantity
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    private Integer quantity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.checked
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    private Integer checked;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.create_time
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cart.update_time
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.id
     *
     * @return the value of cart.id
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.id
     *
     * @param id the value for cart.id
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.user_id
     *
     * @return the value of cart.user_id
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.user_id
     *
     * @param userId the value for cart.user_id
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.product_id
     *
     * @return the value of cart.product_id
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.product_id
     *
     * @param productId the value for cart.product_id
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.quantity
     *
     * @return the value of cart.quantity
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.quantity
     *
     * @param quantity the value for cart.quantity
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.checked
     *
     * @return the value of cart.checked
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    public Integer getChecked() {
        return checked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.checked
     *
     * @param checked the value for cart.checked
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.create_time
     *
     * @return the value of cart.create_time
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.create_time
     *
     * @param createTime the value for cart.create_time
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column cart.update_time
     *
     * @return the value of cart.update_time
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column cart.update_time
     *
     * @param updateTime the value for cart.update_time
     *
     * @mbggenerated Thu Nov 28 09:31:39 CST 2019
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Cart()
    {

    }

    public Cart(Integer id, Integer userId, Integer productId, Integer quantity, Integer checked, Date createTime, Date updateTime) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.checked = checked;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", checked=" + checked +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}