package uz.zinnur.cleaning_carpet.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderDoubleBlanketPriceDto {
    @NotNull(message = "Double blanket price is required")
    @Positive(message = "Double blanket price must be a positive value.")
    private Double doubleBlanketPrice;
    public OrderDoubleBlanketPriceDto() {

    }
    public OrderDoubleBlanketPriceDto(Double doubleBlanketPrice) {
        this.doubleBlanketPrice = doubleBlanketPrice;
    }
    public Double getDoubleBlanketPrice() {
        return doubleBlanketPrice;
    }
    public void setDoubleBlanketPrice(Double doubleBlanketPrice) {
        this.doubleBlanketPrice = doubleBlanketPrice;
    }
}

