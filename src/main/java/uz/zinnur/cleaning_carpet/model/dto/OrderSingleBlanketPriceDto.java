package uz.zinnur.cleaning_carpet.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderSingleBlanketPriceDto {
    @NotNull(message = "Single blanket price is required")
    @Positive(message = "Single blanket price must be a positive value.")
    private Double singleBlanketPrice;
    public OrderSingleBlanketPriceDto() {

    }
    public OrderSingleBlanketPriceDto(Double singleBlanketPrice) {
        this.singleBlanketPrice = singleBlanketPrice;
    }
    public Double getSingleBlanketPrice() {
        return singleBlanketPrice;
    }
    public void setSingleBlanketPrice(Double singleBlanketPrice) {
        this.singleBlanketPrice = singleBlanketPrice;
    }
}
