package uz.zinnur.cleaning_carpet.model.dto;

public class EmployeeRoleDto {
    private String role;
    public EmployeeRoleDto() {

    }
    public EmployeeRoleDto(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
