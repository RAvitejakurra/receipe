package com.example.recipe.controller;

import com.example.recipe.model.Recipe;
import com.example.recipe.repository.RecipeRepository;
import com.example.recipe.exception.RecipeNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);
    
    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes(
            @RequestParam(required = false) Boolean vegetarian,
            @RequestParam(required = false) Integer servings,
            @RequestParam(required = false) String includeIngredient,
            @RequestParam(required = false) String excludeIngredient,
            @RequestParam(required = false) String instructionKeyword) {
        
        List<Recipe> recipes = recipeRepository.findAll();
        logger.info("Fetching all recipes, found: {}", recipes.size());

        if (vegetarian != null) {
            recipes = recipes.stream().filter(r -> r.isVegetarian() == vegetarian).collect(Collectors.toList());
        }
        if (servings != null) {
            recipes = recipes.stream().filter(r -> r.getServings() == servings).collect(Collectors.toList());
        }

        if (includeIngredient != null) {
            recipes = recipes.stream().filter(r -> r.getIngredients().contains(includeIngredient)).collect(Collectors.toList());
        }
        if (excludeIngredient != null) {
            recipes = recipes.stream().filter(r -> !r.getIngredients().contains(excludeIngredient)).collect(Collectors.toList());
        }
        if (instructionKeyword != null) {
            recipes = recipes.stream().filter(r -> r.getInstructions().toLowerCase().contains(instructionKeyword.toLowerCase())).collect(Collectors.toList());
        }

        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable String id) {
        logger.info("Fetching recipe with ID: {}", id);
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe with ID " + id + " not found."));
        return ResponseEntity.ok(recipe);
    }

    @PostMapping
    public ResponseEntity<Recipe> addRecipe(@Valid @RequestBody Recipe recipe) {
        logger.info("Adding new recipe: {}", recipe.getName());
        Recipe savedRecipe = recipeRepository.save(recipe);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable String id, @Valid @RequestBody Recipe updatedRecipe) {
        logger.info("Updating recipe with ID: {}", id);
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe with ID " + id + " not found."));
        updatedRecipe.setId(existingRecipe.getId());
        Recipe savedRecipe = recipeRepository.save(updatedRecipe);
        return ResponseEntity.ok(savedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable String id) {
        logger.info("Deleting recipe with ID: {}", id);
        if (!recipeRepository.existsById(id)) {
            throw new RecipeNotFoundException("Recipe with ID " + id + " not found.");
        }
        recipeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
