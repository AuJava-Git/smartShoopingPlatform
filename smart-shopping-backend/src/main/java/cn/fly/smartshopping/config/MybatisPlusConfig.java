package cn.fly.smartshopping.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration//这是一个配置类，里面的方法用于配置项目功能
public class MybatisPlusConfig {

    @Bean//请执行这个方法，并把方法返回的对象交给 Spring 管理

    //会创建一个 MyBatis-Plus 拦截器对象。
    //这个对象负责拦截分页查询，并自动生成适合数据库的分页 SQL。
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor =
                new MybatisPlusInterceptor();
//这是 MyBatis-Plus 的分页插件。
        interceptor.addInnerInterceptor(
                new PaginationInnerInterceptor());

            return interceptor;
    }

}
