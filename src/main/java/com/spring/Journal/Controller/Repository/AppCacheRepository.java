package com.spring.Journal.Controller.Repository;

import com.spring.Journal.Entity.AppCache;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppCacheRepository extends MongoRepository<AppCache, ObjectId> {

   Boolean findByKey(String key);

}
