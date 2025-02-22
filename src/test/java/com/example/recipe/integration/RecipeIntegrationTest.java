package com.example.recipe.integration;

import com.example.recipe.model.Recipe;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RecipeIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Recipe testRecipe;

    @BeforeEach
    void setUp() {
        testRecipe = new Recipe(null, "Pizza", true, 3, Arrays.asList("Dough", "Cheese", "Tomato"), "Bake at 350°F");
    }

    @Test
    void testCreateRecipe() throws Exception {
        mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testRecipe)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetAllRecipes() throws Exception {
        mockMvc.perform(get("/recipes"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetRecipeById_NotFound() throws Exception {
        mockMvc.perform(get("/recipes/{id}", "invalid-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateRecipe_NotFound() throws Exception {
        Recipe updatedRecipe = new Recipe(null, "Updated Pizza", true, 4, Arrays.asList("Dough", "Cheese", "Basil"), "Bake at 375°F");
        
        mockMvc.perform(put("/recipes/{id}", "invalid-id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedRecipe)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteRecipe_NotFound() throws Exception {
        mockMvc.perform(delete("/recipes/{id}", "invalid-id"))
                .andExpect(status().isNotFound());
    }
}
