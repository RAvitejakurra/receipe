package com.example.recipe.repository;

import com.example.recipe.model.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {
    List<Recipe> findByVegetarian(boolean vegetarian);
    List<Recipe> findByServings(int servings);
    List<Recipe> findByIngredientsContaining(String ingredient);
    List<Recipe> findByInstructionsContaining(String text);
}
