package cn.fly.smartshopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean//将这个方法返回的对象交给 Spring 管理。
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //passwordEncoder.encode(password)
    //将明文密码加密。
    //passwordEncoder.matches(rawPassword, encodedPassword)
    //验证用户输入的密码是否正确。matches(用户输入的明文密码, 数据库中的密文)

}

