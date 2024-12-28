package uz.zinnur.cleaning_carpet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "carpets")
public class Carpet extends BaseEntity {

    @NotNull(message = "Height cannot be null")
    @DecimalMin(value = "0.1", inclusive = true, message = "Height must be greater than 0")
    @Column(nullable = false)
    private Double height;

    @NotNull(message = "Width cannot be null")
    @DecimalMin(value = "0.1", inclusive = true, message = "Width must be greater than 0")
    @Column(nullable = false)
    private Double width;

    @NotBlank(message = "Status cannot be blank")
    @Column(length = 50, nullable = false)
    private String status;

    // Constructors
    public Carpet() {}

    public Carpet(Double height, Double width, String status) {
        this.height = height;
        this.width = width;
        this.status = status;
    }

    // Getters and Setters
    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
