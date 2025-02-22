package com.example.recipe.controller;

import com.example.recipe.model.Recipe;
import com.example.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    // ✅ GET: Fetch all recipes
    @GetMapping
    public ResponseEntity<?> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        if (recipes.isEmpty()) {
            return ResponseEntity.status(404).body("No recipes found.");
        }
        return ResponseEntity.ok(recipes);
    }

    // ✅ GET: Fetch a recipe by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable String id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        
        if (recipe.isPresent()) {
            return ResponseEntity.ok(recipe.get());
        } else {
            return ResponseEntity.status(404).body("Error: Recipe with ID " + id + " not found.");
        }
    }


    // ✅ POST: Add a new recipe
    @PostMapping
    public ResponseEntity<?> addRecipe(@RequestBody Recipe recipe) {
        if (recipe.getName() == null || recipe.getName().isEmpty()) {
            return ResponseEntity.status(400).body("Error: Recipe name is required.");
        }
        Recipe savedRecipe = recipeRepository.save(recipe);
        return ResponseEntity.status(201).body(savedRecipe);
    }

    // ✅ PUT: Update an existing recipe
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable String id, @RequestBody Recipe updatedRecipe) {
        Optional<Recipe> existingRecipe = recipeRepository.findById(id);

        if (existingRecipe.isPresent()) {
            updatedRecipe.setId(id);  // Preserve the original ID
            Recipe savedRecipe = recipeRepository.save(updatedRecipe);
            return ResponseEntity.ok(savedRecipe);
        } else {
            return ResponseEntity.status(404).body("Error: Recipe with ID " + id + " not found.");
        }
    }

    // ✅ DELETE: Remove a recipe by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable String id) {
        if (!recipeRepository.existsById(id)) {
            return ResponseEntity.status(404).body("Error: Recipe with ID " + id + " not found.");
        }
        recipeRepository.deleteById(id);
        return ResponseEntity.ok("Recipe with ID " + id + " deleted successfully.");
    }
}
