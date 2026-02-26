package com.spring.Journal.Service;

import com.spring.Journal.Config.AppCacheConfig;
import com.spring.Journal.Entity.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class StockService {

    private final RestTemplate restTemplate;
    private final AppCacheConfig appCacheConfig;
    private final RedisService redisService;

    public StockService(RestTemplate restTemplate, AppCacheConfig appCacheConfig, RedisService redisService) {
        this.restTemplate = restTemplate;
        this.appCacheConfig = appCacheConfig;
        this.redisService = redisService;
    }


    public Stock getStockData(String stockName) {
        String redisKey = "stock::" + stockName.toUpperCase();

        Stock redisStock = redisService.get(redisKey, Stock.class);
        if(redisStock !=null){
            log.info("Fetched stock from Redis: "+stockName);
            return redisStock;
        }

        String apiUrl = appCacheConfig.getMap().get("stockAPI") + stockName;

        try {
            ResponseEntity<Stock> response = restTemplate.getForEntity(apiUrl, Stock.class);
            if(response!=null && response.getBody() != null){
                log.info("Adding stock in Redis: "+stockName);
                redisService.set(redisKey,response.getBody(),300L);
            }
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            // API returned 404, return null or empty optional
            return null;
        } catch (HttpClientErrorException e) {
            // Log other 4xx/5xx errors
            throw new RuntimeException("Stock API error: " + e.getStatusCode());
        }
    }

}
