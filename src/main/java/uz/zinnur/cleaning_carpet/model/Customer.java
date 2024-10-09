package uz.zinnur.cleaning_carpet.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    private String name;
    private String surname;
    private String language;
    private String phoneNumber;
    private String extraPhoneNumber;
    private String address;
    // Additional Information
    @Column(length = 1000)
    private String notes;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Order> orders;

    public Customer(String name, String surname, String language, String phoneNumber, String extraPhoneNumber, String address, String notes, Set<Order> orders) {
        this.name = name;
        this.surname = surname;
        this.language = language;
        this.phoneNumber = phoneNumber;
        this.extraPhoneNumber = extraPhoneNumber;
        this.address = address;
        this.notes = notes;
        this.orders = orders;
    }

    public Customer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getExtraPhoneNumber() {
        return extraPhoneNumber;
    }

    public void setExtraPhoneNumber(String extraPhoneNumber) {
        this.extraPhoneNumber = extraPhoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
