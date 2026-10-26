package cn.fly.smartshopping.mapper;

import cn.fly.smartshopping.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

//BaseMapper 是 MyBatis-Plus 提供的通用 Mapper 接口。
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
