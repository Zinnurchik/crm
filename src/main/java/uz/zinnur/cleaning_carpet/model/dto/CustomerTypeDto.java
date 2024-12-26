package uz.zinnur.cleaning_carpet.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CustomerTypeDto {
    @NotBlank(message = "Type cannot be blank")
    @Size(max = 50, message = "Type cannot exceed 50 characters")
    private String type;

    public CustomerTypeDto() {

    }
    public CustomerTypeDto(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
