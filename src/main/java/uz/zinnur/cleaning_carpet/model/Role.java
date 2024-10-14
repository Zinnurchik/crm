package uz.zinnur.cleaning_carpet.model;

import jakarta.persistence.*;
import java.util.Arrays;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    @Column(name = "role", nullable = false)
    private String role;

    // Assuming each role can have multiple permissions
    @ManyToMany(fetch = FetchType.EAGER)
    // Adjust to Lazy if performance issues arise
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions;

    public Role() {
    }

    public Role(String role, Set<Permission> permissions) {
        this.role = role;
        this.permissions = permissions;
    }



    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role + ", permissions=" + Arrays.toString(permissions.toArray());
    }

    //    // Map role and permissions to Spring Security's authorities
//    public List<SimpleGrantedAuthority> getAuthorities() {
//        var list = getPermissions()
//                .stream()
//                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//                .collect(Collectors.toList());
//
//        // Add the role as a SimpleGrantedAuthority with prefix ROLE_
//        list.add(new SimpleGrantedAuthority("ROLE_" + getRole()));
//        return list;
//    }
}
