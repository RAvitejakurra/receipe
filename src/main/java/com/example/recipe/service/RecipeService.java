package com.example.recipe.service;

import com.example.recipe.model.Recipe;
import com.example.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(String id) {
        return recipeRepository.findById(id);
    }

    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(String id, Recipe updatedRecipe) {
        if (recipeRepository.existsById(id)) {
            updatedRecipe.setId(id);
            return recipeRepository.save(updatedRecipe);
        }
        return null;
    }

    public void deleteRecipe(String id) {
        recipeRepository.deleteById(id);
    }
}
