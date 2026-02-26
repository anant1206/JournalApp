package com.spring.Journal.Controller;

import com.spring.Journal.Entity.Stock;
import com.spring.Journal.Service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stocks")
public class StockController {

    private final StockService stockService;

    // Constructor injection
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * GET /api/stocks/{stockName}
     * Example: /api/stocks/AAPL
     */
    @GetMapping("/{stockName}")
    public ResponseEntity<Stock> getStock(@PathVariable String stockName) {
        Stock stock = stockService.getStockData(stockName.toUpperCase());

        if (stock != null) {
            return ResponseEntity.ok(stock);
        } else {
            // Return 404 if stock not found
            return ResponseEntity.notFound().build();
        }
    }
}
