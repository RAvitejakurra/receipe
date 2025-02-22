package com.example.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import com.mongodb.client.MongoClients;

@SpringBootApplication
@EnableMongoRepositories
public class RecipeManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RecipeManagerApplication.class, args);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(MongoClients.create("mongodb://localhost:27017"), "recipeDB");
    }
}
