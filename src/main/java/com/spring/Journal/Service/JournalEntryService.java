package com.spring.Journal.Service;

import com.spring.Journal.Controller.Repository.JournalEntryRepository;
import com.spring.Journal.Entity.JournalEntry;
import com.spring.Journal.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry (JournalEntry journalEntry, String userName){
        journalEntry.setDate(LocalDateTime.now());

        User user = userService.findByUserName(userName);
        final JournalEntry saved = journalEntryRepository.save(journalEntry);

        user.getJournalEntries().add(saved);
        userService.saveUser(user);

    }

    public List<JournalEntry> getAllEntry (){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findByEntryId(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public JournalEntry findByEntryName (String journalEntryName) {
        return journalEntryRepository.findByName(journalEntryName);
    }

    @Transactional
    public boolean deleteByEntry (ObjectId id, String userName){
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));

            if(removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("A new Error ocurred while deleting entry",e);
        }
        return removed;

    }


}
