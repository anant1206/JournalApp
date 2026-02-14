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
    private static HashMap<String, String> map;

    @Autowired
    AppCacheRepository appCacheRepository;

    @PostConstruct
    public void init() {
        map= new HashMap<>();
        final List<AppCache> all = appCacheRepository.findAll();
        for(AppCache ac : all){
            map.put(ac.getKey(),ac.getValue());
        }
//        Temp Check
//        for(String i : map.keySet()){
//            System.out.println("Key: " + i +" Value:"+map.get(i));
//        }

    }

    public void addCacheConfig(AppCache appCache){
        if(appCache!=null){
            appCacheRepository.save(appCache);
        }
    }

    public String addCacheUpdateSpecificConfig(AppCache appCache){
        if(appCache!=null){
            String key = appCache.getKey();
            if(appCacheRepository.findByKey(key)){
                appCacheRepository.save(appCache);
                return "SUCCESS";
            }
            return "NOEXIST";
        }
        return "BAD";
    }

}
