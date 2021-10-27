package top.widealpha.mongodemo.config;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {
    @Bean
    MongoDatabase mongodb() {
        MongoClient mongodb = new MongoClient("localhost", 27017);
        return mongodb.getDatabase("user201900301053");
    }
}
