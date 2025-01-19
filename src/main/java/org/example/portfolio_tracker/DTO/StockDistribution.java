package org.example.portfolio_tracker.DTO;

import jakarta.persistence.Entity;
import org.example.portfolio_tracker.model.Stock;

import java.math.BigDecimal;
public class StockDistribution {
    private Stock stock;
    private BigDecimal distributionPercentage;

    public StockDistribution(Stock stock, BigDecimal distributionPercentage) {
        this.stock = stock;
        this.distributionPercentage = distributionPercentage;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public BigDecimal getDistributionPercentage() {
        return distributionPercentage;
    }

    public void setDistributionPercentage(BigDecimal distributionPercentage) {
        this.distributionPercentage = distributionPercentage;
    }
}
