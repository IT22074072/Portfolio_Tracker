package org.example.portfolio_tracker.controller;


import org.example.portfolio_tracker.model.Stock;
import org.example.portfolio_tracker.DTO.StockDistribution;
import org.example.portfolio_tracker.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin

public class StockController {

    @Autowired
    private StockService service;

    @GetMapping("/home")
    public String home(){
        return "Hello";
    }

    @GetMapping("/stocks")
    public ResponseEntity<List<Stock>> getStocks(){
        return new ResponseEntity<>(service.getAllStocks(), HttpStatus.OK);
    }


    @GetMapping("/stock/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable("id") Long id){
        Stock stock = service.getStockById(id);
        if (stock.getId() > 0){
            return new ResponseEntity<>(stock, HttpStatus.OK);
        }else {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/stock")
    public ResponseEntity<?> addProduct(@RequestBody Stock stock) {
        Stock savedStock = service.addStock(stock);
        return new ResponseEntity<>(savedStock,HttpStatus.CREATED);

    }

    @PutMapping("/stock/{id}")
    public ResponseEntity<String> addProduct(@PathVariable("id") Long id,@RequestBody Stock stock) {
        if(stock.getId() > 0){
            Stock updatedStock = service.updateStock(stock);
            return new ResponseEntity<>("Updated!", HttpStatus.OK);

        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @DeleteMapping("/stock/{id}")
    public ResponseEntity<String> deleteStockById(@PathVariable("id") Long id){
        Stock stock = service.getStockById(id);
        if (stock.getId() > 0){
            service.deleteStockById(id);
            return new ResponseEntity<>("Deleted!", HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/random-portfolio")
    public ResponseEntity<List<Stock>> getRandomPortfolio() {
        List<Stock> portfolio = service.getRandomPortfolio();
        return new ResponseEntity<>(portfolio, HttpStatus.OK);
    }

    @GetMapping("/portfolio-value")
    public ResponseEntity<Map<String, Object>> getPortfolioValue() {
        List<Stock> portfolio = service.getRandomPortfolio();
        BigDecimal totalValue = service.calculatePortfolioValue(portfolio);

        Map<String, Object> response = new HashMap<>();
        response.put("portfolio", portfolio);
        response.put("totalValue", totalValue);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Endpoint to get the top-performing stock
    @GetMapping("/top-performing-stock")
    public ResponseEntity<Stock> getTopPerformingStock() {
        Stock topStock = service.findTopPerformingStock();
        if (topStock != null) {
            return new ResponseEntity<>(topStock, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to get the portfolio distribution
    @GetMapping("/portfolio-distribution")
    public ResponseEntity<List<StockDistribution>> getPortfolioDistribution() {
        List<Stock> portfolio = service.getRandomPortfolio();
        List<StockDistribution> distribution = service.getPortfolioDistribution(portfolio);

        return new ResponseEntity<>(distribution, HttpStatus.OK);
    }

}
