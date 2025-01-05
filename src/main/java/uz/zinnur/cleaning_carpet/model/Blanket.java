package uz.zinnur.cleaning_carpet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "blankets")
public class Blanket extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;


    @NotBlank(message = "Size cannot be blank")
    @Size(max = 50, message = "Size must be at most 50 characters")
    @Column(nullable = false, length = 50)
    private String size;


    @Size(max = 50, message = "Status must be at most 50 characters")
    @Column(nullable = false, length = 50)
    private String status;

    // Default Constructor
    public Blanket() {}

    // Parameterized Constructor
    public Blanket(String size, String status) {
        this.size = size;
        this.status = status;
    }

    // Getter for size
    public String getSize() {
        return size;
    }

    // Setter for size
    public void setSize(String size) {
        this.size = size;
    }

    // Getter for status
    public String getStatus() {
        return status;
    }

    // Setter for status
    public void setStatus(String status) {
        this.status = status;
    }
}
