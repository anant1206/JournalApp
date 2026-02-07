package com.spring.Journal.Controller;

import com.spring.Journal.Entity.JournalEntry;
import com.spring.Journal.Entity.Quotes;
import com.spring.Journal.Entity.User;
import com.spring.Journal.Service.JournalEntryService;
import com.spring.Journal.Service.QuotesService;
import com.spring.Journal.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService userService;

    @Autowired
    QuotesService quotesService;

    @Autowired
    JournalEntryService journalEntryService;

    @GetMapping("/health-check")
    public String healthCheck(){
        return "OK";
    }


    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            userService.saveNewUser(user);

            Quotes quote = quotesService.callQuotesAPI();
            String randomFact = (quote != null) ? quote.getQuote(): "Stay positive and keep going!";
            String randomFactAuthor = (quote != null) ? quote.getAuthor(): user.getUserName();

            JournalEntry je = new JournalEntry();
            je.setName("Default: "+randomFactAuthor);
            je.setContent(randomFact.strip());

            journalEntryService.saveEntry(je, user.getUserName());

            return ResponseEntity.ok("Hi " + user.getUserName() + ", Random fact: " + randomFact);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Something went wrong");
        }
    }

}
