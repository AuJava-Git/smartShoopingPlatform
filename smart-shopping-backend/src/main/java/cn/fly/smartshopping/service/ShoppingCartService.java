package cn.fly.smartshopping.service;

import cn.fly.smartshopping.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    /**
     * 为指定用户加入购物车；同一用户再次加入同一商品时累加数量。
     */
    ShoppingCart add(Long userId, Long productId, Integer quantity);

    /**
     * 查询指定用户自己的购物车。
     */
    List<ShoppingCart> getByUserId(Long userId);

    /**
     * 修改指定用户自己的购物车商品数量。
     */
    ShoppingCart updateQuantity(Long userId, Long cartId, Integer quantity);

    /**
     * 删除指定用户自己的购物车记录。
     */
    boolean delete(Long userId, Long cartId);
}
