package uz.zinnur.cleaning_carpet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "carpets")
public class Carpet extends BaseEntity{
    private Double height;
    private Double weight;
    private String carpetStatus;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public Carpet(Double height, Double weight, String carpetStatus, Order order) {
        this.order = order;
        this.carpetStatus = carpetStatus;
        this.height = height;
        this.weight = weight;
    }

    public Carpet() {

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getCarpetStatus() {
        return carpetStatus;
    }

    public void setCarpetStatus(String carpetStatus) {
        this.carpetStatus = carpetStatus;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
