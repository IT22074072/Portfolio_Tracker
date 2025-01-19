package org.example.portfolio_tracker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class StockPriceService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${stock.api.key}")
    private String apiKey;

    // Method to get stock price by ticker
    public BigDecimal getStockPrice(String ticker) {
        String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + ticker + "&interval=1min&apikey=" + apiKey;
        try {
            String response = restTemplate.getForObject(url, String.class);
            return extractPriceFromResponse(response);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching stock price for ticker: " + ticker, e);
        }
    }

    // Helper method to extract the price from the API response
    private BigDecimal extractPriceFromResponse(String response) {
        try {
            // Create an ObjectMapper to parse the JSON response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            // Navigate to the time series data
            JsonNode timeSeries = root.path("Time Series (1min)");

            // Get the most recent entry (latest available price)
            if (timeSeries.isObject()) {
                // The most recent timestamp will be the first key in the time series
                String latestTimestamp = timeSeries.fieldNames().next();
                JsonNode latestData = timeSeries.path(latestTimestamp);

                // Extract the "close" price (current price) from the latest data
                String price = latestData.path("4. close").asText();

                return new BigDecimal(price);
            } else {
                throw new RuntimeException("Invalid response structure from Alpha Vantage API");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error parsing stock price from response", e);
        }
    }
}
