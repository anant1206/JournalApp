package com.spring.Journal.Controller.Repository;

import com.spring.Journal.Entity.AppCache;
import com.spring.Journal.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppCacheRepository extends MongoRepository<AppCache, ObjectId> {

   User findByKey(String key);

}
