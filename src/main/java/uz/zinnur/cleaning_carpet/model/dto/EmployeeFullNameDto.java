package uz.zinnur.cleaning_carpet.model.dto;

import jakarta.validation.constraints.NotBlank;
public class EmployeeFullNameDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Surname cannot be empty")
    private String surname;
    public EmployeeFullNameDto(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public EmployeeFullNameDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
