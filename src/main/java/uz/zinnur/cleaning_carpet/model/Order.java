package uz.zinnur.cleaning_carpet.model;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity{
    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;
    @Column(nullable = false)
    private String orderStatus;  // e.g., PENDING, COMPLETED, CANCELLED
    @Column(nullable = false)
    private Double totalPrice;
    @Column(length = 1000)
    private String notes;

    public Order(Customer customer, String orderStatus, Double totalPrice, String notes) {
        this.customer = customer;
        this.notes = notes;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
    }

    public Order() {

    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
