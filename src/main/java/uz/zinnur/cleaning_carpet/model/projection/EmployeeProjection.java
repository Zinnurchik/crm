package uz.zinnur.cleaning_carpet.model.projection;

import uz.zinnur.cleaning_carpet.model.Role;

import java.util.UUID;

public class EmployeeProjection {

    private UUID id;
    private String name;
    private String surname;
    private String username;
    private String phoneNumber;
    private Role role;

    // Constructor
    public EmployeeProjection(UUID id, String name, String surname, String username, String phoneNumber, Role role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // Getters (Setters are optional if the projection is read-only)
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

