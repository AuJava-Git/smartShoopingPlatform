package cn.fly.smartshopping.service.impl;

import cn.fly.smartshopping.entity.Product;
import cn.fly.smartshopping.mapper.ProductMapper;
import cn.fly.smartshopping.service.ProductService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service//请创建 ProductServiceImpl 的对象，并把它交给 Spring 管理。
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override//通过 id 查询单个产品的信息
    public Product getById(Long id) {
        return productMapper.selectById(id);
    }

    @Override//查看全部产品的信息放入集合
    public List<Product> getAll() {
        return productMapper.selectList(null);//这里的 null 表示：暂时不设置查询条件。
        // 如果以后要查询价格小于 300 元的商品，就不再使用 null，而会传入查询条件对象。这个后面再学习。
    }

    @Override
    public Product create(Product product) {
        productMapper.insert(product);//将传入的 Product 对象保存到数据库。
        return product;
    }

    @Override//通过id来修改产品的信息
    public Product update(Long id, Product product) {
        product.setId(id);//把 URL 中的商品 id 设置到要修改的 Product 对象中。
        productMapper.updateById(product);//根据 Product 对象中的 id 修改数据库记录
        return productMapper.selectById(id);
    }

    @Override
    public boolean delete(Long id) {
        return productMapper.deleteById(id) > 0;//MyBatis-Plus 的 deleteById 会返回一个整数。
        // 这个整数表示：实际删除了多少条记录。
        // 如果返回值大于 0，则表示删除成功。
    }

    @Override//根据商品名查询商品
    public List<Product> searchByName(String name) {
        //用 Java 代码组装 SQL 查询条件。
        //创建一个用于 Product 查询的条件对象，并将它保存到 queryWrapper 变量中。
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        //使用 Product 类中的 getName() 方法对应的字段。
        //like 方法表示模糊查询
        queryWrapper.like(Product::getName, name);
        //这次传入了查询条件，MyBatis-Plus 会根据 queryWrapper 中的条件进行查询。
        return productMapper.selectList(queryWrapper);
    }

    @Override
    //IPage<Product> 表示一个分页结果。
    //它不只包含商品列表，还包含分页信息 例如：当前页；每页数量；总记录数；总页数；当前页数据
    public IPage<Product> getPage(Long current, Long size) {
        Page<Product> page = new Page<>(current, size);//current = 1;size = 10 =>第 1 页每页 10 条
        //page表示分页信息，null表示不设置查询条件。
        return productMapper.selectPage(page, null);
    }
}
