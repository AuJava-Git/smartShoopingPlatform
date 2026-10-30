package cn.fly.smartshopping.service.impl;

import cn.fly.smartshopping.entity.Product;
import cn.fly.smartshopping.entity.ShoppingCart;
import cn.fly.smartshopping.mapper.ProductMapper;
import cn.fly.smartshopping.mapper.ShoppingCartMapper;
import cn.fly.smartshopping.service.ShoppingCartService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 购物车业务实现类。
 */
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    //查询、新增、修改、删除购物车记录
    private final ShoppingCartMapper shoppingCartMapper;
    //查询商品是否存在 检查商品库存
    private final ProductMapper productMapper;

    public ShoppingCartServiceImpl(
            ShoppingCartMapper shoppingCartMapper,
            ProductMapper productMapper) {
        this.shoppingCartMapper = shoppingCartMapper;
        this.productMapper = productMapper;
    }
//加入购物车方法
    @Override
    public ShoppingCart add(Long userId, Long productId, Integer quantity) {
        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException("商品数量至少为1");
        }

        Product product = productMapper.selectById(productId);
        if (product == null) {
            throw new IllegalArgumentException("商品不存在");
        }

        LambdaQueryWrapper<ShoppingCart> queryWrapper =
                new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, userId)
                .eq(ShoppingCart::getProductId, productId);

        ShoppingCart existingCart = shoppingCartMapper.selectOne(queryWrapper);
        int newQuantity = quantity;

        if (existingCart != null) {
            newQuantity = existingCart.getQuantity() + quantity;
        }

        if (product.getStock() != null && newQuantity > product.getStock()) {
            throw new IllegalArgumentException("商品库存不足");
        }

        if (existingCart == null) {
            ShoppingCart cart = new ShoppingCart();
            cart.setUserId(userId);//表示购物车属于哪个用户。
            cart.setProductId(productId);//表示购物车中是哪一个商品。
            cart.setQuantity(quantity);//表示商品数量。
            shoppingCartMapper.insert(cart);//把数据保存到数据库。
            return fillProductInfo(cart);
        }

        existingCart.setQuantity(newQuantity);
        shoppingCartMapper.updateById(existingCart);
        return fillProductInfo(existingCart);
    }

    //查询当前用户购物车方法
    @Override
    public List<ShoppingCart> getByUserId(Long userId) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper =
                new LambdaQueryWrapper<>();

        queryWrapper.eq(ShoppingCart::getUserId, userId)
                .orderByDesc(ShoppingCart::getUpdateTime);

        List<ShoppingCart> carts =
                shoppingCartMapper.selectList(queryWrapper);

        // 给每条购物车记录补充商品名称、价格和描述
        carts.forEach(this::fillProductInfo);

        return carts;
    }

    //修改购物车数量方法
    @Override
    public ShoppingCart updateQuantity(
            Long userId, Long cartId, Integer quantity) {

        if (quantity == null || quantity < 1) {
            throw new IllegalArgumentException("商品数量至少为1");
        }

        LambdaQueryWrapper<ShoppingCart> queryWrapper =
                new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getId, cartId)
                .eq(ShoppingCart::getUserId, userId);
//查询不到购物车时 可能有两种情况
        //购物车编号确实不存在 购物车存在，但不属于当前用户
        ShoppingCart cart = shoppingCartMapper.selectOne(queryWrapper);
        if (cart == null) {
            throw new IllegalArgumentException("购物车记录不存在");
        }
//修改前再次检查库存
        Product product = productMapper.selectById(cart.getProductId());
        if (product == null) {
            throw new IllegalArgumentException("商品不存在");
        }

        if (product.getStock() != null && quantity > product.getStock()) {
            throw new IllegalArgumentException("商品库存不足");
        }

        cart.setQuantity(quantity);
        shoppingCartMapper.updateById(cart);
        return fillProductInfo(cart);
    }
//删除购物车方法
    @Override
    public boolean delete(Long userId, Long cartId) {
        //查询条件
        LambdaQueryWrapper<ShoppingCart> queryWrapper =
                new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getId, cartId)
                .eq(ShoppingCart::getUserId, userId);
        //MyBatis-Plus 的 delete 返回的是删除的记录数。 删除 1 条 → 返回 1
        return shoppingCartMapper.delete(queryWrapper) > 0;
    }
    /**
     * 查询商品信息，并补充到购物车对象中。
     */
    private ShoppingCart fillProductInfo(ShoppingCart cart) {
        Product product = productMapper.selectById(cart.getProductId());

        if (product != null) {
            cart.setProductName(product.getName());
            cart.setProductPrice(product.getPrice());
            cart.setProductDescription(product.getDescription());
        }

        return cart;
    }
}

