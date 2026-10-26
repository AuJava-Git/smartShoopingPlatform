package cn.fly.smartshopping.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.fly.smartshopping.common.Result;
import cn.fly.smartshopping.entity.Product;
import cn.fly.smartshopping.security.RequireAdmin;
import cn.fly.smartshopping.service.ProductService;
import jakarta.validation.Valid;

@RestController//这是一个接收网络请求、返回数据的控制器。
@RequestMapping("/products")//这个 Controller 中所有接口的基础路径都是 /products

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    //查询 id 为 1 的商品
    @GetMapping("/{id}")//这个接口接收一个路径参数 id
                        // /products/1 获取 id 为 1 的商品
    public Result<Product> getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return Result.success(product);
    }

    //查询所有商品
    @GetMapping//这里的 @GetMapping 没有写路径，表示使用 Controller 的基础路径 /products
    public Result<List<Product>> getAll() {
        List<Product> products = productService.getAll();
        return Result.success(products);
    }

    //新增商品
    @PostMapping//使用 HTTP 的 POST 请求访问 /products 时，执行下面的方法
    @RequireAdmin 
    public Result<Product> create(@Valid @RequestBody Product product) {//从请求体中读取 JSON，并转换成一个 Product 对象
        Product createdProduct = productService.create(product);
        return Result.success("商品新增成功",createdProduct);
        /*  @PathVariable` | URL 路径   | `/products/1
            @RequestBody`  | 请求体 JSON | `{ "name": "机械键盘" }
        */
    }

    //修改商品
    @PutMapping("/{id}")//使用 PUT 请求访问 /products/{id} 时，执行下面的方法。
    @RequireAdmin 
    public Result<Product> update(@PathVariable Long id, @Valid @RequestBody Product product) {
        Product result = productService.update(id, product);
        return Result.success("商品修改成功",result);
    }

    //删除商品
    @DeleteMapping("/{id}")
    @RequireAdmin 
    public Result<Boolean> delete(@PathVariable Long id) {
        boolean result = productService.delete(id);
        return Result.success(result);
    }

    @GetMapping("/search")
    //@RequestParam 从 URL 的查询参数中获取数据
    public Result<List<Product>> searchByName(@RequestParam String name) {
        List<Product> products = productService.searchByName(name);
        return Result.success(products);
    }

    @GetMapping("/page")
    public Result<IPage<Product>> getPage(
            //如果请求中没有传 page，就使用默认值 1。
            @RequestParam(defaultValue = "1")Long page,
            @RequestParam(defaultValue = "10") Long size) {
        IPage<Product> result = productService.getPage(page, size);
        return Result.success(result);
    }



}
