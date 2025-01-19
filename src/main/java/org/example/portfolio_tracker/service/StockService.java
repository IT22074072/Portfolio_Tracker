package org.example.portfolio_tracker.service;

import org.example.portfolio_tracker.model.Stock;
import org.example.portfolio_tracker.DTO.StockDistribution;
import org.example.portfolio_tracker.repo.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class StockService {

    @Autowired
    private StockRepository repo;

    @Autowired
    private StockPriceService stockPriceService;
    public List<Stock> getAllStocks() {
        return repo.findAll();
    }

    public Stock getStockById(Long id) {
        return repo.findById(id).orElse(new Stock());
    }


    public Stock addStock(Stock stock) {
        BigDecimal livePrice = stockPriceService.getStockPrice(stock.getTicker());
        stock.setCurrentPrice(livePrice);
        return repo.save(stock);
    }

    public void deleteStockById(Long id) {
        repo.deleteById(id);
    }

    public Stock updateStock(Stock stock) {
        return repo.save(stock);
    }

    // Get a portfolio of 5 random stocks
    public List<Stock> getRandomPortfolio() {
        List<Stock> allStocks = repo.findAll();
        List<Stock> portfolio = new ArrayList<>();
        Random random = new Random();

        // Create a copy of the list to avoid modifying the original list
        List<Stock> availableStocks = new ArrayList<>(allStocks);

        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(availableStocks.size());
            Stock selectedStock = availableStocks.get(index);

            portfolio.add(selectedStock);

            // Remove the selected stock to avoid duplicates
            availableStocks.remove(index);
        }

        return portfolio;
    }


    // Calculate the total portfolio value
    public BigDecimal calculatePortfolioValue(List<Stock> portfolio) {
        BigDecimal totalValue = BigDecimal.ZERO;

        for (Stock stock : portfolio) {
            BigDecimal stockValue = stock.getCurrentPrice().multiply(BigDecimal.valueOf(stock.getQuantity()))
                    .setScale(2, RoundingMode.HALF_UP);

            totalValue = totalValue.add(stockValue);
        }

        return totalValue;
    }

    // Method to find the top-performing stock
    public Stock findTopPerformingStock() {
        List<Stock> allStocks = repo.findAll();
        Stock topStock = null;
        BigDecimal highestPerformance = BigDecimal.ZERO;

        for (Stock stock : allStocks) {
            BigDecimal performance = calculatePerformance(stock);
            if (performance.compareTo(highestPerformance) > 0) {
                highestPerformance = performance;
                topStock = stock;
            }
        }

        return topStock;
    }

    // Calculate the percentage performance of a stock
    private BigDecimal calculatePerformance(Stock stock) {
        if (stock.getBuyPrice().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal change = stock.getCurrentPrice().subtract(stock.getBuyPrice());
        return change.divide(stock.getBuyPrice(), 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
    }

    // Method to get portfolio distribution
    public List<StockDistribution> getPortfolioDistribution(List<Stock> portfolio) {
        BigDecimal totalPortfolioValue = calculatePortfolioValue(portfolio);
        List<StockDistribution> distribution = new ArrayList<>();

        for (Stock stock : portfolio) {
            BigDecimal stockValue = stock.getCurrentPrice().multiply(BigDecimal.valueOf(stock.getQuantity()))
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal distributionPercentage = stockValue.divide(totalPortfolioValue, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            distribution.add(new StockDistribution(stock, distributionPercentage));
        }

        return distribution;
    }
}


