package com.example.recipe.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Document(collection = "recipes")
public class Recipe {
    
    @Id  // MongoDB will automatically generate an ObjectId
    private String id;
    
    @NotBlank(message = "Recipe name is required")
    private String name;
    
    @NotNull(message = "Vegetarian status is required")
    private Boolean vegetarian;
    
    @NotNull(message = "Servings count is required")
    private Integer servings;
    
    @NotNull(message = "Ingredients are required")
    private List<String> ingredients;
    
    @NotBlank(message = "Instructions are required")
    private String instructions;

    // Default Constructor
    public Recipe() {}

    // Constructor with ID (Fixes issue in test cases)
    public Recipe(String id, String name, boolean vegetarian, int servings, List<String> ingredients, String instructions) {
        this.id = id;
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

    public void setId(String id) {
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
