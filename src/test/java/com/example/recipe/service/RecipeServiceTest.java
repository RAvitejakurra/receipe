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
    	Recipe recipe1 = new Recipe("Pasta", true, 2, Arrays.asList("Tomato", "Pasta"), "Boil pasta and mix ingredients");
    	Recipe recipe2 = new Recipe("Salad", true, 1, Arrays.asList("Lettuce", "Carrot"), "Chop and mix vegetables");

        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2));

        List<Recipe> recipes = recipeService.getAllRecipes();
        assertEquals(2, recipes.size());
    }
}
