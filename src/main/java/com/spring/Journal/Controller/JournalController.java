package com.spring.Journal.Controller;

import com.spring.Journal.Entity.JournalEntry;
import com.spring.Journal.Entity.User;
import com.spring.Journal.Service.JournalEntryService;
import com.spring.Journal.Service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalEntryService journalEntryService;


    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        final List<JournalEntry> all = user.getJournalEntries();

        if(!all.isEmpty()  && all != null){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{myID}")
    public ResponseEntity<JournalEntry> getByID(@PathVariable ObjectId myID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        User user  = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myID)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            final Optional<JournalEntry> byEntry = journalEntryService.findByEntryId(myID);
            if(byEntry.isPresent()){
                return new ResponseEntity<>(byEntry.get(), HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<JournalEntry>  createEntry(@RequestBody JournalEntry myEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try {
            journalEntryService.saveEntry(myEntry, userName);
            System.out.println(userName +" ,pwd is : "+authentication.getCredentials());
            return new ResponseEntity<>(myEntry, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/id/{myID}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId myID, @RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user  = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myID)).collect(Collectors.toList());

        if(!collect.isEmpty()){
            Optional<JournalEntry> byEntry = journalEntryService.findByEntryId(myID);
            if(byEntry.isPresent()){
                JournalEntry oldEntry = byEntry.get();
                oldEntry.setName(newEntry.getName() != null && !newEntry.getName().isEmpty() ? newEntry.getName() : oldEntry.getName());
                oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent() );
                journalEntryService.saveEntry(oldEntry, userName);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myID}")
    public ResponseEntity<?> deleteEntry(@PathVariable ObjectId myID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();

        boolean removed = journalEntryService.deleteByEntry(myID, userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
