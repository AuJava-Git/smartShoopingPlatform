package cn.fly.smartshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

import java.time.LocalDateTime;

@TableName("shopping_cart")  // 对应表名 shopping_cart
public class ShoppingCart {
    @TableId(type = IdType.AUTO)
    private Long id;//购物车记录编号

    @NotNull(message = "用户编号不能为空")
    private Long userId;//用户编号

    @NotNull(message = "商品编号不能为空")
    private Long productId;//商品编号

    @NotNull(message = "商品数量不能为空")
    @Min(value = 1, message = "商品数量至少为1")
    private Integer quantity;//商品数量

    @TableField("create_time")
    private LocalDateTime createTime;//创建时间

    @TableField("update_time")
    private LocalDateTime updateTime;//更新时间
    /**
     * 以下字段不是 shopping_cart 表中的字段，
     * 只是查询商品信息后返回给前端使用。
     * @TableField(exist = false)非常重要。它告诉 MyBatis-Plus：
     * 这些字段不是数据库 shopping_cart 表中的列，不要尝试把它们保存到数据库。
     * 它们只是后端查询商品后，临时放到返回对象中给前端使用。
     */
    @TableField(exist = false)
    private String productName;

    @TableField(exist = false)
    private BigDecimal productPrice;

    @TableField(exist = false)
    private String productDescription;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
