package uz.zinnur.cleaning_carpet.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class CustomerPhoneNumberDto {
    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9]{12}$", message = "Invalid phone number format")
    private String phoneNumber;

    @Pattern(regexp = "^\\+?[0-9]{12}$", message = "Invalid extra phone number format")
    private String extraPhoneNumber;

    public CustomerPhoneNumberDto() {

    }
    public CustomerPhoneNumberDto(String phoneNumber, String extraPhoneNumber) {
        this.phoneNumber = phoneNumber;
        this.extraPhoneNumber = extraPhoneNumber;
    }
    public String getExtraPhoneNumber() {
        return extraPhoneNumber;
    }
    public void setExtraPhoneNumber(String extraPhoneNumber) {
        this.extraPhoneNumber = extraPhoneNumber;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
