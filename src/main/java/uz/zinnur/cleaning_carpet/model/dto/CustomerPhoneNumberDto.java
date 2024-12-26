package uz.zinnur.cleaning_carpet.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CustomerPhoneNumberDto {
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9]{12}$", message = "Invalid phone number format")
    private String phoneNumber;
    public CustomerPhoneNumberDto() {

    }
    public CustomerPhoneNumberDto(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
