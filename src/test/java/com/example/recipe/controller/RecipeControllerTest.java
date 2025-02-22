package com.example.recipe.controller;

import com.example.recipe.model.Recipe;
import com.example.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeControllerTest {

    @InjectMocks
    private RecipeController recipeController;

    @Mock
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRecipes() {
        Recipe recipe1 = new Recipe("1", "Pasta", true, 2, Arrays.asList("Tomato", "Pasta"), "Boil water and add pasta");
        Recipe recipe2 = new Recipe("2", "Chicken Curry", false, 4, Arrays.asList("Chicken", "Curry Powder"), "Cook chicken with curry");
        
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2));

        ResponseEntity<List<Recipe>> response = recipeController.getAllRecipes(null, null, null, null, null);
        
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetVegetarianRecipes() {
        Recipe recipe = new Recipe("1", "Salad", true, 1, Arrays.asList("Lettuce", "Tomato"), "Mix all ingredients");

        when(recipeRepository.findAll()).thenReturn(List.of(recipe));

        ResponseEntity<List<Recipe>> response = recipeController.getAllRecipes(true, null, null, null, null);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().get(0).isVegetarian());
    }

    @Test
    void testGetRecipesByServings() {
        Recipe recipe = new Recipe("1", "Soup", true, 3, Arrays.asList("Carrot", "Salt"), "Boil vegetables");

        when(recipeRepository.findAll()).thenReturn(List.of(recipe));

        ResponseEntity<List<Recipe>> response = recipeController.getAllRecipes(null, 3, null, null, null);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(3, response.getBody().get(0).getServings());
    }

    @Test
    void testIncludeIngredientFilter() {
        Recipe recipe = new Recipe("1", "Pizza", true, 2, Arrays.asList("Cheese", "Tomato"), "Bake pizza");

        when(recipeRepository.findAll()).thenReturn(List.of(recipe));

        ResponseEntity<List<Recipe>> response = recipeController.getAllRecipes(null, null, "Cheese", null, null);

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().get(0).getIngredients().contains("Cheese"));
    }

    @Test
    void testExcludeIngredientFilter() {
        Recipe recipe = new Recipe("1", "Fish Curry", false, 2, Arrays.asList("Fish", "Spices"), "Cook fish with spices");

        when(recipeRepository.findAll()).thenReturn(List.of(recipe));

        ResponseEntity<List<Recipe>> response = recipeController.getAllRecipes(null, null, null, "Fish", null);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testTextSearchInInstructions() {
        Recipe recipe = new Recipe("1", "Cake", true, 4, Arrays.asList("Flour", "Sugar"), "Bake in oven at 180C");

        when(recipeRepository.findAll()).thenReturn(List.of(recipe));

        ResponseEntity<List<Recipe>> response = recipeController.getAllRecipes(null, null, null, null, "oven");

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetRecipeById() {
        Recipe recipe = new Recipe("1", "Pasta", true, 2, Arrays.asList("Tomato", "Pasta"), "Boil water and add pasta");

        when(recipeRepository.findById("1")).thenReturn(Optional.of(recipe));

        ResponseEntity<Recipe> response = recipeController.getRecipeById("1");

        assertNotNull(response.getBody());
        assertEquals("Pasta", response.getBody().getName());
    }

    @Test
    void testGetRecipeById_NotFound() {
        when(recipeRepository.findById("99")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> recipeController.getRecipeById("99"));

        assertEquals("Recipe with ID 99 not found.", exception.getMessage());
    }
}
