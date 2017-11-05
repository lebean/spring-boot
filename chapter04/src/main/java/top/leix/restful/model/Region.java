package top.leix.restful.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Name: ${Name}
 * Description:
 * Author: leix
 * Time: 2017/3/28 14:02
 */
@Entity
public class Region {
    @Id
    private int id;
    private int parentId;
    private String name;
    private String fullName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
