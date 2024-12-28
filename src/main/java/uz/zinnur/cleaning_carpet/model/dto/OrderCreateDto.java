package uz.zinnur.cleaning_carpet.model.dto;

import jakarta.validation.constraints.*;
import java.util.Set;
import java.util.UUID;

public class OrderCreateDto {

    @NotNull(message = "Customer ID is required.")
    private UUID customerId;

    @Size(min = 1, message = "There must be at least one driver.")
    private Set<UUID> driverIds;

    @Positive(message = "Carpet price must be a positive value.")
    private Double carpetPrice;

    @Positive(message = "Single blanket price must be a positive value.")
    private Double singleBlanketPrice;

    @Positive(message = "Double blanket price must be a positive value.")
    private Double doubleBlanketPrice;

    @Size(max = 500, message = "Notes must be at most 500 characters.")
    private String notes;

    // Getters and Setters

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public Set<UUID> getDriverIds() {
        return driverIds;
    }

    public void setDriverIds(Set<UUID> driverIds) {
        this.driverIds = driverIds;
    }

    public Double getCarpetPrice() {
        return carpetPrice;
    }

    public void setCarpetPrice(Double carpetPrice) {
        this.carpetPrice = carpetPrice;
    }

    public Double getSingleBlanketPrice() {
        return singleBlanketPrice;
    }

    public void setSingleBlanketPrice(Double singleBlanketPrice) {
        this.singleBlanketPrice = singleBlanketPrice;
    }

    public Double getDoubleBlanketPrice() {
        return doubleBlanketPrice;
    }

    public void setDoubleBlanketPrice(Double doubleBlanketPrice) {
        this.doubleBlanketPrice = doubleBlanketPrice;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
