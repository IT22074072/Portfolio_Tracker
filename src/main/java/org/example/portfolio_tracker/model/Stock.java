package org.example.portfolio_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;

@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String ticker;
    private int quantity;
    private BigDecimal buyPrice;
    private BigDecimal currentPrice; // Field for storing real-time price


    public Stock(Long id, String name, String ticker, int quantity, BigDecimal buyPrice) {
        this.id = id;
        this.name = name;
        this.ticker = ticker;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.currentPrice = buyPrice;
    }

    public Stock() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ticker='" + ticker + '\'' +
                ", quantity=" + quantity +
                ", buyPrice=" + buyPrice +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
