package com.yeahmobi.loadbalance_manager.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yeahmobi.loadbalance_manager.model.ConfigGen;

/**
 */
@Service
public class ConfigGenDao extends MongoBaseDao<ConfigGen, Long> {

    @Autowired
    public ConfigGenDao(MongoClient mongoClient) {
        super(mongoClient.getDatastore());
        // at application start
        // map classes before calling with morphia map* methods
        mongoClient.getMorphia().map(ConfigGen.class);
        mongoClient.getDatastore().ensureCaps(); // creates capped collections from @Entity
        mongoClient.getDatastore().ensureIndexes(); // creates indexes from @Index annotations in your entities
    }

}
