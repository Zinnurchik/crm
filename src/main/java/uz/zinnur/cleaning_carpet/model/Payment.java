package uz.zinnur.cleaning_carpet.model;

import jakarta.persistence.*;

@Entity
@Table(name = "payments")
public class Payment extends BaseEntity{
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "payment_amount", nullable = false)
    private Double paymentAmount;

    public Payment(Order order, Double paymentAmount) {
        this.order = order;
        this.paymentAmount = paymentAmount;
    }

    public Payment() {

    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
