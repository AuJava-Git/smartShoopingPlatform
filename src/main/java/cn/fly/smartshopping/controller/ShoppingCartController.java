package cn.fly.smartshopping.controller;


import cn.fly.smartshopping.DTO.AddCartRequest;
import cn.fly.smartshopping.DTO.LoginResponse;
import cn.fly.smartshopping.DTO.UpdateCartQuantityRequest;
import cn.fly.smartshopping.common.Result;
import cn.fly.smartshopping.entity.ShoppingCart;
import cn.fly.smartshopping.service.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车接口控制器。
 */
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    /**
     * 将商品加入当前登录用户的购物车。
     */
    @PostMapping
    public Result<ShoppingCart> add(
            //@Valid根据 DTO 中的校验注解检查请求数据。
            //例如@Min(value = 1, message = "商品数量至少为1")
            //如果前端发送：POST /cart {"productId": 1, "quantity": 0}
            //就会触发参数校验错误。
            @Valid @RequestBody AddCartRequest request,
            HttpSession session) {

        LoginResponse loginUser = getLoginUser(session);
        if (loginUser == null) {
            return Result.unauthorized("请先登录");
        }

        ShoppingCart cart = shoppingCartService.add(
                loginUser.getId(),
                request.getProductId(),
                request.getQuantity());

        return Result.success("加入购物车成功", cart);
    }

    /**
     * 查询当前登录用户自己的购物车。
     */
    @GetMapping
    public Result<List<ShoppingCart>> getMyCart(HttpSession session) {
        LoginResponse loginUser = getLoginUser(session);
        if (loginUser == null) {
            return Result.unauthorized("请先登录");
        }

        List<ShoppingCart> carts =
                shoppingCartService.getByUserId(loginUser.getId());

        return Result.success(carts);
    }

    /**
     * 修改当前登录用户某条购物车记录的数量。
     */
    @PutMapping("/{cartId}")//表示接收：PUT /cart/购物车编号
    public Result<ShoppingCart> updateQuantity(
            @PathVariable Long cartId,//@PathVariable作用是从 URL 路径中获取购物车编号。
            @Valid @RequestBody UpdateCartQuantityRequest request,
            HttpSession session) {

        LoginResponse loginUser = getLoginUser(session);
        if (loginUser == null) {
            return Result.unauthorized("请先登录");
        }

        ShoppingCart cart = shoppingCartService.updateQuantity(
                loginUser.getId(),
                cartId,
                request.getQuantity());

        return Result.success("购物车数量修改成功", cart);
    }

    /**
     * 删除当前登录用户自己的购物车记录。
     */
    @DeleteMapping("/{cartId}")
    public Result<Boolean> delete(
            @PathVariable Long cartId,
            HttpSession session) {

        LoginResponse loginUser = getLoginUser(session);
        if (loginUser == null) {
            return Result.unauthorized("请先登录");
        }

        boolean deleted =
                shoppingCartService.delete(loginUser.getId(), cartId);

        return Result.success("购物车商品删除成功", deleted);
    }

    /**
     * 从 Session 中读取当前登录用户。
     */
    private LoginResponse getLoginUser(HttpSession session) {
        return (LoginResponse) session.getAttribute("loginUser");
    }
}