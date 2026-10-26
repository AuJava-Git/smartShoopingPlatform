package cn.fly.smartshopping.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import cn.fly.smartshopping.DTO.LoginRequest;
import cn.fly.smartshopping.DTO.LoginResponse;
import cn.fly.smartshopping.entity.User;
import cn.fly.smartshopping.mapper.UserMapper;
import cn.fly.smartshopping.service.UserService;

@Service//业务层组件，以后 Controller 需要调用 UserService 时，Spring 会自动找到这个实现类
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserMapper userMapper,
            PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User getById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public boolean create(User user) {
        //创建一个用于 User 查询的条件对象。
        LambdaQueryWrapper<User> queryWrapper =
                new LambdaQueryWrapper<>();
//查询 username 等于当前注册用户名的用户。
        queryWrapper.eq(User::getUsername, user.getUsername());
//根据查询条件查询一条用户记录。
        //查询到用户：返回 User 对象
        //没有查询到用户：返回 null
        User existingUser = userMapper.selectOne(queryWrapper);
//如果用户名已经存在，就不允许注册，直接返回失败。
        if (existingUser != null) {
            return false;
        }
        //1：正常
        //0：禁用
        if (user.getStatus() == null) {
            user.setStatus(1);
        }
         // 管理员账号应该由数据库管理员或后台管理功能单独设置。
         user.setRole(0);

//密码加密的过程
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        //1：新增成功；0：新增失败
        //会转换成：true 或 false
        return userMapper.insert(user) > 0;
    }
    @Override
    public LoginResponse login(LoginRequest request) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(User::getUsername, request.getUsername());

        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            return null;
        }
        boolean passwordCorrect =
//matches方法用于验证密码，因为一个是明文，一个是密文，永远也不可能自己用equals比较
                passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!passwordCorrect) {
            return null;
        }
        if(user.getStatus()==null||user.getStatus()!=1){
            return null;
    }
    // 如果数据库中的 role 没有值，就按照普通用户处理
        Integer role = user.getRole();

        if (role == null) {
            role = 0;
        }
        return new LoginResponse(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getEmail(),
                user.getPhone(),
            role);
    }
}
