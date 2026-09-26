package Game1.src.com.xiedongGame.domain;

//用户类：一个 User 对象就代表一个注册用户
public class User {

    private String username;   //用户名
    private String password;   //密码

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
