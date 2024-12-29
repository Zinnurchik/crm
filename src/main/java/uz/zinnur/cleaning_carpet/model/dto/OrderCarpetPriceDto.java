package uz.zinnur.cleaning_carpet.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderCarpetPriceDto {
    @NotNull(message = "Carpet price is required")
    @Positive(message = "Carpet price must be a positive value.")
    private Double carpetPrice;

    public OrderCarpetPriceDto() {

    }
    public OrderCarpetPriceDto(Double carpetPrice) {
        this.carpetPrice = carpetPrice;
    }
    public Double getCarpetPrice() {
        return carpetPrice;
    }
    public void setCarpetPrice(Double carpetPrice) {
        this.carpetPrice = carpetPrice;
    }

}
