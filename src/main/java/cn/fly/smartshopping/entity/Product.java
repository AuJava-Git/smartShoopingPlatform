package cn.fly.smartshopping.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@TableName("product")
public class Product {
    @TableId(type = IdType.AUTO)//新增时不需要自己填写 id。
    private Long id;
//@NotBlank作用是检查字符串：不能是 null；不能是空字符串；不能全部是空格。
    @NotBlank(message = "名称不能为空")
    @Size(max = 100, message = "名称长度不能超过100字符")
    private String name;
//@NotNull表示字段不能是 null。
    @NotNull(message = "商品价格不能为空")
//  用于检查小数或金额的最小值
    @DecimalMin(value = "0.01", message = "商品价格必须大于0")
    private BigDecimal price;

    @NotNull(message = "商品库存不能为空")
    @Min(value = 0, message = "商品库存不能小于0")
    private Integer stock;

    @Size(max = 500 , message = "描述不能超过500个字符")
    private String description;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public String getDescription() {
        return description;
    }
}
