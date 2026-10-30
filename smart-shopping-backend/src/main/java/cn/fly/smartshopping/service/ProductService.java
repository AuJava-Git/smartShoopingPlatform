package cn.fly.smartshopping.service;

import cn.fly.smartshopping.entity.Product;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface ProductService {
    Product getById(Long id);

    List<Product> getAll();

    Product create(Product product);//接收一个 Product 对象，保存后返回保存结果

    Product update(Long id, Product product);//根据 URL 中的 id，修改请求体中的商品信息，并返回修改后的商品。

    boolean delete(Long id);

    List<Product> searchByName(String name);//根据商品名查询商品

    IPage<Product> getPage(Long current, Long size);//根据页码和页大小查询商品分页列表


}

