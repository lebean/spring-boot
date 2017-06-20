package leix.lebean.sweb.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Name:User
 * Description:
 * Author:leix
 * Time: 2017/6/12 10:28
 */

@Entity
@Table(name = "city")
public class User implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", insertable = true)
    private String name;
    @Column(name = "password", insertable = true)
    private String password;
    @Column(name = "email", insertable = true)
    private String email;
    @Column(name = "password_reset_date", insertable = true)
    private Date lastPasswordResetDate;
    @Column(name = "roles", insertable = true)
    private String roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Date lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
