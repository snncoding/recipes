package com.snn.recipes.service;

import com.snn.recipes.dto.RecipeDto;
import com.snn.recipes.mapper.IngredientMapper;
import com.snn.recipes.mapper.RecipeMapper;
import com.snn.recipes.model.Recipe;
import com.snn.recipes.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository repository;

    private final RecipeMapper recipeMapper;
    private final IngredientMapper ingredientMapper;

    public List<RecipeDto> getAll() {
        List<Recipe> list = repository.findAll();
        List<RecipeDto> dtoList = list.stream().map(recipeMapper::toRecipeDto).collect(Collectors.toList());
        return dtoList;
    }

    public Recipe getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Could not found given recipe id :%d", id)));
    }

    public void save(RecipeDto dto) {
        repository.save(recipeMapper.toRecipe(dto));
    }

    /**
     * update method searches by id and if it return with value, saves values with dto,
     * else it returns NOT_FOUND exception
     *
     * @param id  It is a Recipe id
     * @param dto It is a dto object of Recipe
     */
    public void update(Long id, RecipeDto dto) {
        Recipe recipe = getById(id);

        recipe.setName(dto.getName());
        recipe.setCookingMethod(dto.getCookingMethod());
        recipe.setInstruction(dto.getInstruction());
        recipe.setNumberOfServing(dto.getNumberOfServing());
        recipe.setVegetarian(dto.isVegetarian());
        recipe.setIngredients(dto.getIngredients().
                stream().
                map(ingredientMapper::toIngredient).
                collect(Collectors.toSet()));

        repository.save(recipe);
    }

    /**
     * delete method searches by id if it does not exist return NOT_FOUND exception else
     * it deletes the row on db table.
     *
     * @param id
     */
    public void delete(Long id) {
        Recipe recipe = getById(id);

        repository.deleteById(id);
    }
}
