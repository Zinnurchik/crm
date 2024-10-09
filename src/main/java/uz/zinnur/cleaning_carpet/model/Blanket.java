package uz.zinnur.cleaning_carpet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "blankets")
public class Blanket extends BaseEntity{
    private String size;
    private String blanketStatus;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public Blanket(String size, String blanketStatus, Order order) {
        this.order = order;
        this.size = size;
        this.blanketStatus = blanketStatus;
    }

    public Blanket() {

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getBlanketStatus() {
        return blanketStatus;
    }

    public void setBlanketStatus(String blanketStatus) {
        this.blanketStatus = blanketStatus;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
