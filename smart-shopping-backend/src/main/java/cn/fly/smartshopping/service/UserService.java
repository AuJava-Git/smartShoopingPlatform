package cn.fly.smartshopping.service;

import cn.fly.smartshopping.DTO.LoginRequest;
import cn.fly.smartshopping.DTO.LoginResponse;
import cn.fly.smartshopping.entity.User;

public interface UserService {

    User getById(Long id);//根据用户 id 查询一个用户对象。

    boolean create(User user);//接收一个 User 对象，尝试将它保存到数据库，并返回是否成功。
//接收登录请求数据，验证成功后返回安全的登录信息。
    LoginResponse login(LoginRequest Request);

    

}
