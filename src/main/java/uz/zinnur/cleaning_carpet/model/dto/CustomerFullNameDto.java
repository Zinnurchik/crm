package uz.zinnur.cleaning_carpet.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerFullNameDto {
    @NotBlank(message = "Name cannot be blank")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @NotBlank(message = "Surname cannot be blank")
    @Size(max = 100, message = "Surname cannot exceed 100 characters")
    private String surname;
    public CustomerFullNameDto(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }
    public CustomerFullNameDto() {

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
