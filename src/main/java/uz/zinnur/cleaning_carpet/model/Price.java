package uz.zinnur.cleaning_carpet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "prices")
public class Price extends BaseEntity {
    private Double bigCarpetPrice;
    private Double carpetPrice;
    private Double smallBlanketPrice;
    private Double bigBlanketPrice;

    public Price(Double bigCarpetPrice, Double carpetPrice, Double smallBlanketPrice, Double bigBlanketPrice) {
        this.bigCarpetPrice = bigCarpetPrice;
        this.carpetPrice = carpetPrice;
        this.smallBlanketPrice = smallBlanketPrice;
        this.bigBlanketPrice = bigBlanketPrice;
    }

    public Price() {

    }

    public Double getBigCarpetPrice() {
        return bigCarpetPrice;
    }

    public void setBigCarpetPrice(Double bigCarpetPrice) {
        this.bigCarpetPrice = bigCarpetPrice;
    }

    public Double getCarpetPrice() {
        return carpetPrice;
    }

    public void setCarpetPrice(Double carpetPrice) {
        this.carpetPrice = carpetPrice;
    }

    public Double getSmallBlanketPrice() {
        return smallBlanketPrice;
    }

    public void setSmallBlanketPrice(Double smallBlanketPrice) {
        this.smallBlanketPrice = smallBlanketPrice;
    }

    public Double getBigBlanketPrice() {
        return bigBlanketPrice;
    }

    public void setBigBlanketPrice(Double bigBlanketPrice) {
        this.bigBlanketPrice = bigBlanketPrice;
    }
}
