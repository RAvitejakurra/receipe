package com.example.recipe.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.recipe.model.Recipe;
import com.example.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class RecipeServiceTest {

    @InjectMocks
    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllRecipes() {
        Recipe recipe1 = new Recipe("1", "Pasta", true, 2, Arrays.asList("Tomato", "Pasta"), "Boil pasta and mix ingredients");
        Recipe recipe2 = new Recipe("2", "Salad", true, 1, Arrays.asList("Lettuce", "Carrot"), "Chop and mix vegetables");

        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2));

        List<Recipe> recipes = recipeService.getAllRecipes();
        assertEquals(2, recipes.size());
    }

    @Test
    void testGetRecipeById() {
        Recipe recipe = new Recipe("1", "Soup", true, 3, Arrays.asList("Carrot", "Salt"), "Boil vegetables");

        when(recipeRepository.findById("1")).thenReturn(Optional.of(recipe));

        Optional<Recipe> foundRecipe = recipeService.getRecipeById("1");
        assertTrue(foundRecipe.isPresent());
        assertEquals("Soup", foundRecipe.get().getName());
    }

    @Test
    void testAddRecipe() {
        Recipe recipe = new Recipe("1", "Pizza", true, 4, Arrays.asList("Dough", "Cheese", "Tomato"), "Bake at 350°F");
        when(recipeRepository.save(recipe)).thenReturn(recipe);

        Recipe savedRecipe = recipeService.addRecipe(recipe);
        assertNotNull(savedRecipe);
        assertEquals("Pizza", savedRecipe.getName());
    }

    @Test
    void testDeleteRecipe() {
        doNothing().when(recipeRepository).deleteById("1");
        assertDoesNotThrow(() -> recipeService.deleteRecipe("1"));
    }
}
