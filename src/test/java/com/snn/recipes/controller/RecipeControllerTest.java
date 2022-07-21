package com.snn.recipes.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.snn.recipes.dto.SearchCriteriaDto;
import com.snn.recipes.mapper.RecipeMapper;
import com.snn.recipes.model.Ingredient;
import com.snn.recipes.model.Recipe;
import com.snn.recipes.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.EntityNotFoundException;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RecipeMapper recipeMapper;

    @MockBean
    private RecipeService service;

    private List<Recipe> recipes;

    @BeforeEach
    void setUp() {
        recipes = List.of(
                new Recipe(1L, false, 4, getIngredients("egg,water,salt"), "Boil with oil at 100 degrees"),
                new Recipe(2L, true, 2, getIngredients("pasta,water,salt,pepper,onion"), "grill at high temperature")
        );
    }

    private Set<Ingredient> getIngredients(String strIngredients) {
        Set<Ingredient> ingredients = Arrays.stream(strIngredients.split(",")).map(p -> new Ingredient(p)).collect(toSet());
        return ingredients;
    }

    @Test
    void itShouldReturnAllData_getAll() throws Exception {
        given(service.getAll()).willReturn(recipes.stream().map(recipeMapper::toRecipeDto).collect(toList()));

        ResultActions response = mockMvc.perform(get("/api/recipe"));

        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(recipes.size())));
    }

    @Test
    void itShouldReturnById_fetch() throws Exception {
        given(service.fetch(1L)).willReturn(recipeMapper.toRecipeDto(recipes.get(0)));

        ResultActions resultActions = mockMvc.perform(get("/api/recipe/" + recipes.get(0).getId()));

        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id",
                        is(1)));
    }

    @Test
    void itShouldThrowNotFound_fetch() throws Exception {
        given(service.fetch(1L)).willThrow(EntityNotFoundException.class);

        ResultActions resultActions = mockMvc.perform(get("/api/recipe/" + recipes.get(0).getId()));

        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void itShouldResponseCreatedHttpStatus_add() throws Exception {
        given(service.add(recipeMapper.toRecipeDto(recipes.get(0)))).willReturn(recipes.get(0));

        ResultActions resultActions = mockMvc.perform(post("/api/recipe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(recipes.get(0))));

        resultActions.andExpect(status().isCreated());
    }

    byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    @Test
    void itShouldReturnOkStatus_update() throws Exception {
        given(service.update(1L, recipeMapper.toRecipeDto(recipes.get(0)))).willReturn(recipes.get(0));

        ResultActions resultActions = mockMvc.perform(put("/api/recipe/" + recipes.get(0).getId())
                .content(objectMapper.writeValueAsString(recipes.get(0)))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
    }

    @Test
    void itShouldReturnNotFoundStatus_update() throws Exception {
        given(service.update(1L, recipeMapper.toRecipeDto(recipes.get(0)))).willThrow(EntityNotFoundException.class);

        ResultActions resultActions = mockMvc.perform(put("/api/recipe/" + recipes.get(0).getId())
                .content(objectMapper.writeValueAsString(recipes.get(0)))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void itShouldReturnOkStatus_delete() throws Exception {
        doNothing().when(service).delete(1L);

        ResultActions resultActions = mockMvc.perform(delete("/api/recipe/1")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isOk());
    }

    @Test
    void itShouldThrowNotFound_delete() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(service).delete(1L);

        ResultActions resultActions = mockMvc.perform(delete("/api/recipe/1")
                .contentType(MediaType.APPLICATION_JSON));
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    void search() throws Exception {
        given(service.search(SearchCriteriaDto.builder().isVegetarian(false).build())).
                willReturn(List.of(recipeMapper.toRecipeDto(recipes.get(0))));

        ResultActions resultActions = mockMvc.perform(post("/api/recipe/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(SearchCriteriaDto.builder().isVegetarian(false).build())));

        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].id", is(1)));
    }
}