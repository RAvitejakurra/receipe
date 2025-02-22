package com.example.recipe.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.example.recipe.model.Recipe;
import com.example.recipe.repository.RecipeRepository;
import com.example.recipe.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(RecipeController.class)
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock repository to prevent interaction with real database
        Recipe recipe1 = new Recipe("Pasta", true, 2, Arrays.asList("Tomato", "Pasta"), "Boil pasta and mix ingredients");
        Recipe recipe2 = new Recipe("Salad", true, 1, Arrays.asList("Lettuce", "Carrot"), "Chop and mix vegetables");

        List<Recipe> recipes = Arrays.asList(recipe1, recipe2);

        // Mock repository behavior
        when(recipeRepository.findAll()).thenReturn(recipes);
        when(recipeService.getAllRecipes()).thenReturn(recipes);
    }

    @Test
    void testGetAllRecipes() throws Exception {
        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(recipeService.getAllRecipes()), true));

        verify(recipeService, times(1)).getAllRecipes();
    }
}
