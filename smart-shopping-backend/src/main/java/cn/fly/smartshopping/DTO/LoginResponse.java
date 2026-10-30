package cn.fly.smartshopping.DTO;
//这是返回给前端的数据对象，不会将密码发送给前端
public class LoginResponse {
    private Long id;

    private String username;

    private String nickname;

    private String email;

    private String phone;
     /**
     * 角色信息供前端展示使用，真正的权限判断仍然必须由后端完成。
     */
     private Integer role;

    public LoginResponse(){}

    public LoginResponse(Long id, String username, String nickname, String email, String phone, Integer role) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;

        
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
