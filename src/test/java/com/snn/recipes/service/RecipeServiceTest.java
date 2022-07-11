package com.snn.recipes.service;

import com.snn.recipes.dto.RecipeDto;
import com.snn.recipes.dto.SearchCriteriaDto;
import com.snn.recipes.mapper.RecipeMapper;
import com.snn.recipes.model.Ingredient;
import com.snn.recipes.model.Recipe;
import com.snn.recipes.repository.RecipeRepository;
import com.snn.recipes.repository.RecipeSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RecipeServiceTest {

    @MockBean
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeService recipeService;
    private List<Recipe> recipes;

    @Autowired
    private RecipeMapper recipeMapper;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipes = List.of(
                new Recipe(1L, false, 4, getIngredients("egg,water,salt"), "boiled"),
                new Recipe(2L, true, 2, getIngredients("pasta,water,salt,pepper,onion"), "boiled")
        );

    }

    /**
     * Creates a Set of Ingredient for test
     * @param strIngredients String type
     * @return
     */
    private Set<Ingredient> getIngredients(String strIngredients) {
        Set<Ingredient> ingredients = Arrays.stream(strIngredients.split(",")).map(p -> new Ingredient(p)).collect(toSet());
        return ingredients;
    }

    @Test
    void getAll() {
        when(recipeRepository.findAll()).thenReturn(recipes);

        List<RecipeDto> recipeServiceAll = recipeService.getAll();

        assertEquals(recipeServiceAll.get(0).getId(), recipes.get(0).getId(), "recipeService.getAll must return to expected data");
        assertEquals(recipeServiceAll.size(), recipes.size(), "Returned size doesn't match");
    }

    @Test
    void getById() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipes.get(0)));

        Recipe recipe = recipeService.getById(1L);

        assertEquals(recipe.getId(), recipes.get(0).getId(), "recipeService.getById must return to expected data");
    }

    @Test
    void itShouldThrowEntityNotFoundException_getById(){
        when(recipeRepository.findById(any())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class,()-> recipeService.getById(3L), "recipeService.getById must throw EntityNotFoundException");
    }

    @Test
    void fetch() {
        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipes.get(0)));

        RecipeDto recipe = recipeService.fetch(1L);

        assertEquals(recipe.getId(), recipes.get(0).getId(), "recipeService.fetch must return to expected data");
    }

    @Test
    void add() {
        when(recipeRepository.save(any())).thenReturn(recipes.get(0));
        RecipeDto dto = recipeMapper.toRecipeDto(recipes.get(0));

        Recipe recipe = recipeService.add(dto);

        //equals method already had been written
        assertEquals(recipe, recipes.get(0));
    }

    @Test
    void update() {
        when(recipeRepository.findById(any())).thenReturn(Optional.of(recipes.get(0)));
        when(recipeRepository.save(any())).thenReturn(recipes.get(0));

        Recipe recipe = recipeService.update(1L, recipeMapper.toRecipeDto(recipes.get(0)));

        assertEquals(recipe.getId(), recipes.get(0).getId(), "Updated data must be correct value");
    }

    @Test
    void itShouldThrowEntityNotFoundException_update() {
        when(recipeRepository.findById(any())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class,()->recipeService.update(2L, recipeMapper.toRecipeDto(recipes.get(1))));

    }

    @Test
    void canDeleteById_delete() {
        when(recipeRepository.findById(any())).thenReturn(Optional.ofNullable(recipes.get(0)));
        doNothing().when(recipeRepository).deleteById(any());

        recipeService.delete(1L);

        verify(recipeRepository, times(1)).deleteById(1L);
    }


    @Test
    void itCanReturnAllVegetarianRecipe_search() {
        RecipeSpecification spec = new RecipeSpecification();
        SearchCriteriaDto dto = SearchCriteriaDto.builder().isVegetarian(true).build();
        when(recipeRepository.findAll(any(Specification.class))).thenReturn(List.of(recipes.get(1)));

        List<RecipeDto> list = recipeService.search(dto);

        assertTrue(list.get(0).isVegetarian(), "After searching, result must return all vegetarian recipe");
        assertEquals(list.get(0).getId(), 2, "After searching, result must return valid id");
    }

    @Test
    void itCanReturnAllNonVegetarianRecipe_search() {
        RecipeSpecification spec = new RecipeSpecification();
        SearchCriteriaDto dto = SearchCriteriaDto.builder().isVegetarian(false).build();
        when(recipeRepository.findAll(any(Specification.class))).thenReturn(List.of(recipes.get(0)));

        List<RecipeDto> list = recipeService.search(dto);

        assertFalse(list.get(0).isVegetarian(), "After searching, result must return non-vegetarian recipe");
        assertEquals(list.get(0).getId(), 1, "After searching, result must return valid id for non-vegetarian recipe");
    }
}