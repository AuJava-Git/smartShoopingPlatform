package cn.fly.smartshopping.mapper;

import cn.fly.smartshopping.entity.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
//BaseMapper 已经提供了基础方法，如增删改查等，因此这里不需要再写额外的方法，直接使用即可
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
