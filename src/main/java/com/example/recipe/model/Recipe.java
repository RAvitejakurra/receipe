package com.example.recipe.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "recipes")
public class Recipe {
    @Id
    private String id;
    private String name;
    private boolean vegetarian;
    private int servings;
    private List<String> ingredients;
    private String instructions;

    // Getters and Setters
}
