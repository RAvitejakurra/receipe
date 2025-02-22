package com.example.recipe.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "recipes")
public class Recipe {
    
    @Id  // MongoDB will automatically generate an ObjectId
    private String id;
    
    private String name;
    private boolean vegetarian;
    private int servings;
    private List<String> ingredients;
    private String instructions;

    // Default Constructor
    public Recipe() {}

    // Constructor with fields
    public Recipe(String name, boolean vegetarian, int servings, List<String> ingredients, String instructions) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.servings = servings;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public void setId(String id) { // Ensure setId() exists
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
