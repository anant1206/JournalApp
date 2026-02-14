package com.spring.Journal.Controller;

import com.spring.Journal.Config.AppCacheConfig;
import com.spring.Journal.Entity.AppCache;
import com.spring.Journal.Entity.User;
import com.spring.Journal.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    AppCacheConfig appCacheConfig;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAll(){

        List<User> all = userService.getAllUsers();
        if(!all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAdmin(@RequestBody User user){
        try {
            userService.saveAdmin(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/refresh-app-cache")
    public void refreshAppCache(){
        appCacheConfig.init();
    }

    @PostMapping("/add-app-cache")
    public ResponseEntity<?> addAppCacheConfigProperty(@RequestBody AppCache appCache){
        try{
            appCacheConfig.addCacheConfig(appCache);
            return new ResponseEntity<>(appCache, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
