package com.spring.Journal.Config;

import com.spring.Journal.Controller.Repository.AppCacheRepository;
import com.spring.Journal.Entity.AppCache;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class AppCacheConfig {

    @Getter
    private static HashMap<String, String> map= new HashMap<>();

    @Autowired
    AppCacheRepository appCacheRepository;

    @PostConstruct
    public void init() {
        final List<AppCache> all = appCacheRepository.findAll();
        for(AppCache ac : all){
            map.put(ac.getKey(),ac.getValue());
        }

    }

}
