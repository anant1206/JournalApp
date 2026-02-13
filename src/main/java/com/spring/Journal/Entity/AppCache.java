package com.spring.Journal.Entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "AppCacheConfig")
@Data
public class AppCache {

    private String key;
    private String value;


}
