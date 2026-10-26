package cn.fly.smartshopping.security;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@RequireAdmin它相当于给 Controller 方法贴了一个标签:这个方法需要管理员权限
//注解本身只负责“标记”，不会自动阻止普通用户访问。
//后面还需要权限拦截器读取这个标记并进行判断。

@Target(ElementType.METHOD)//这个注解只能用在方法上,但是不能直接写在普通变量上
@Retention(RetentionPolicy.RUNTIME)//程序运行时仍然保留这个注解。
//这不是普通的 Java 类，而是自定义注解。
public @interface RequireAdmin {

}
