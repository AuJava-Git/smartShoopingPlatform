package cn.fly.smartshopping.security;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import cn.fly.smartshopping.DTO.LoginResponse;
import cn.fly.smartshopping.common.Result;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//它负责在 Controller 方法执行之前检查
//当前用户有没有登录？当前用户的 role 是否为 1？
//检查 create 方法是否有 @RequireAdmin
//检查 Session 中有没有 loginUser
//检查 loginUser.role 是否等于 1
public class PermissionInterceptor implements HandlerInterceptor {

//    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
         // 判断当前请求是否对应一个 Controller 方法
        if(!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }


       // 查找当前 Controller 方法上的 @RequireAdmin 注解
       RequireAdmin requireAdmin =
       handlerMethod.getMethodAnnotation(RequireAdmin.class);

        // 如果没有 @RequireAdmin，说明当前接口不需要管理员权限
        if (requireAdmin == null) {
           return true;
        }

        // 从 Session 中取出登录用户
        LoginResponse loginUser =
               (LoginResponse) request.getSession()
                       .getAttribute("loginUser");

        // 没有登录
        if (loginUser == null) {
           writeJson(response, Result.unauthorized("请先登录"));
           return false;
        }

        // 已经登录，但不是管理员
        if (loginUser.getRole() == null || loginUser.getRole() != 1) {
           writeJson(response, Result.forbidden("没有管理员权限"));
           return false;
        }

        // 登录且角色为管理员，放行
        return true;
        }

        private void writeJson(
            HttpServletResponse response,
            Result<?> result) throws Exception {
    
        response.setStatus(result.getCode());
        response.setContentType("application/json;charset=UTF-8");
    
        String json = String.format(
                "{\"code\":%d,\"message\":\"%s\",\"data\":null}",result.getCode(),result.getMessage());
    
        response.getWriter().write(json);
    }
    
}
