package uz.zinnur.cleaning_carpet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity implements UserDetails {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Surname is required")
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    @Column(nullable = false)
    private String surname;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must have at least 6 characters")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{12}$", message = "Phone number must be valid (e.g., +1234567890)")
    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "employee_roles",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public Employee(String name, String surname, String username, String password, String phoneNumber, Set<Role> roles) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }

    public Employee() {

    }
    //Setter
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @NotBlank(message = "Phone number is required") @Pattern(regexp = "^\\+?[0-9]{12}$", message = "Phone number must be valid (e.g., +1234567890)") String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NotBlank(message = "Phone number is required") @Pattern(regexp = "^\\+?[0-9]{12}$", message = "Phone number must be valid (e.g., +1234567890)") String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    // UserDetails interface methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .flatMap(role -> Stream.concat(
                        // Add the role itself as a GrantedAuthority with "ROLE_" prefix
                        Stream.of(new SimpleGrantedAuthority("ROLE_" + role.getRole())),

                        // Add each permission as a separate GrantedAuthority
                        role.getPermissions().stream()
                                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                ))
                .collect(Collectors.toSet()); // Collect as a Set to avoid duplicates
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roles=" + Arrays.toString(roles.toArray()) +
                '}';
    }
}
