package uz.zinnur.cleaning_carpet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonBackReference
    private Customer customer;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_drivers",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> drivers;

    private Double carpetPrice;

    private Double singleBlanketPrice;

    private Double doubleBlanketPrice;

    @Column(length = 500)
    private String notes;

    private String status;

    @Column(unique = true, nullable = false)
    private Long orderNumber;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Carpet> carpets;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Blanket> blankets;
    private LocalDateTime deadline;
    private Double givenPrice;
    private Double totalPrice;



    // Getters and Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<Employee> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<Employee> drivers) {
        this.drivers = drivers;
    }

    public List<Carpet> getCarpets() {
        return carpets;
    }

    public void setCarpets(List<Carpet> carpets) {
        this.carpets = carpets;
    }

    public List<Blanket> getBlankets() {
        return blankets;
    }

    public void setBlankets(List<Blanket> blankets) {
        this.blankets = blankets;
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

    public Double getGivenPrice() {
        return givenPrice;
    }

    public void setGivenPrice(Double givenPrice) {
        this.givenPrice = givenPrice;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
