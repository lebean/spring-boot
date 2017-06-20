package leix.lebean.sweb.auth;

import java.io.Serializable;

/**
 * Name:Authentication
 * Description: 用户请求认证时发送的数据模型
 * Author:leix
 * Time: 2017/6/12 09:53
 */
public class Authentication implements Serializable{


    /**
     * 用户名
     */
    private String name;

    /**
     * 登录密码
     */
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
