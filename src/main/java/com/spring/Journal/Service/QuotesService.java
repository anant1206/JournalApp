package com.spring.Journal.Service;

import com.spring.Journal.Entity.Quotes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuotesService {

    private static final String API = "https://zenquotes.io/api/random";

    private final RestTemplate restTemplate;

    @Autowired
    public QuotesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Quotes callQuotesAPI() {
        ResponseEntity<Quotes[]> response =
                restTemplate.getForEntity(API, Quotes[].class);

        Quotes[] quotes = response.getBody();

        if (quotes != null && quotes.length > 0) {
            return quotes[0];
        }
        return null;
    }
}


