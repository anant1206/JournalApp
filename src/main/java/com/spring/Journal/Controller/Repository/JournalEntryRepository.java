package com.spring.Journal.Controller.Repository;

import com.spring.Journal.Entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

    JournalEntry findByName (String name);
}
