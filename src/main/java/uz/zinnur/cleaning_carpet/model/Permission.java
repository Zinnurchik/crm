package uz.zinnur.cleaning_carpet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity{
    private String permission;

    public Permission() {
    }
    public Permission(String permission) {
        this.permission = permission;
    }
    public String getPermission() {
        return permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "'" + permission + "'";
    }
}
