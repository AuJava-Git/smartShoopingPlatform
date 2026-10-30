package cn.fly.smartshopping.controller;

import cn.fly.smartshopping.DTO.LoginRequest;
import cn.fly.smartshopping.DTO.LoginResponse;
import cn.fly.smartshopping.common.Result;
import cn.fly.smartshopping.entity.User;
import cn.fly.smartshopping.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/users")//表示当前 Controller 中接口的基础路径是：/users
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")//完整请求路径：POST http://localhost:8080/users/register
    //@Valid 按照 User 类上的校验注解检查请求数据。
    public Result<Boolean> register(@Valid @RequestBody User user) {
        boolean success = userService.create(user);

        if (!success) {
            return Result.error("用户名已存在");
        }

        return Result.success("注册成功", true);
    }

    @PostMapping("/login")
    public Result<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            //HttpSession 表示服务器为当前浏览器保存的会话
            HttpSession session) {

        LoginResponse response =
                userService.login(request);

        if (response == null) {
//代码中统一返回用户名或密码错误，而不是分别提示：用户名不存在 密码错误
//如果分别提示，别人可以通过接口判断某个用户名是否已经注册，减少用户信息泄露
            return Result.error("用户名或密码错误");
        }
        //登录成功后，将当前用户的必要信息保存到 Session
        //把登录成功后的用户信息，以 loginUser 这个名字保存到当前 Session 中。
        session.setAttribute("loginUser", response);
        return Result.success("登录成功", response);
    }

    @GetMapping("/me")
    public Result<LoginResponse> getCurrentUser(HttpSession session) {
        //表示从 Session 中取出之前保存的登录用户。
        LoginResponse loginUser = (LoginResponse) session.getAttribute("loginUser");

        if (loginUser == null) {
            throw new RuntimeException("请先登录");
        }

        return Result.success( loginUser);
    }

    @PostMapping("/logout")
    public Result<String> logout(HttpSession session) {
//销毁当前登录会话，使当前用户退出登录
        session.invalidate();

        return Result.success("退出登录成功", null);
    }



}
