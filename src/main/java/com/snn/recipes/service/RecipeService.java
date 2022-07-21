package com.snn.recipes.service;

import com.snn.recipes.dto.RecipeDto;
import com.snn.recipes.dto.SearchCriteriaDto;
import com.snn.recipes.mapper.IngredientMapper;
import com.snn.recipes.mapper.RecipeMapper;
import com.snn.recipes.model.Recipe;
import com.snn.recipes.repository.RecipeRepository;
import com.snn.recipes.repository.RecipeSpecification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class RecipeService {

    private final RecipeRepository repository;
    private final RecipeMapper recipeMapper;
    private final IngredientMapper ingredientMapper;

    private final RecipeSpecification recipeSpecification;

    /**
     * Returns all Recipe rows with Ingredients as list of RecipeDto
     * @return
     */
    public List<RecipeDto> getAll() {
        List<Recipe> list = repository.findAll();
        return list.stream().map(recipeMapper::toRecipeDto).collect(toList());
    }

    /**
     *
     * @param id
     * @return
     */
    public Recipe getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Could not found given recipe id :%d", id)));
    }

    public RecipeDto fetch(Long id) {
        return recipeMapper.toRecipeDto(getById(id));
    }

    public Recipe add(RecipeDto dto) {
        return repository.save(recipeMapper.toRecipe(dto));
    }

    /**
     * update method searches by id and if it return with value, saves values with dto,
     * else @throws NOT_FOUND exception
     *
     * @param id  It is a Recipe id
     * @param dto It is a dto object of Recipe
     * @return
     */
    public Recipe update(Long id, RecipeDto dto) {
        Recipe recipe = getById(id);

        recipe.setInstruction(dto.getInstruction());
        recipe.setNumberOfServing(dto.getNumberOfServing());
        recipe.setVegetarian(dto.isVegetarian());
        recipe.setIngredients(dto.getIngredients().
                stream().
                map(ingredientMapper::toIngredient).
                collect(Collectors.toSet()));

        return repository.save(recipe);
    }

    /**
     * delete method searches by id if it does not exist return NOT_FOUND exception else
     * it deletes the row on db table.
     *
     * @param id
     */
    public void delete(Long id) {
        //Removed unused vaiable
        getById(id);

        repository.deleteById(id);
    }

    public List<RecipeDto> search(SearchCriteriaDto criteria) {
        List<Recipe> recipes = repository.findAll(recipeSpecification.getRecipes(criteria));
        return recipes.stream().map(recipeMapper::toRecipeDto).collect(toList());
    }
}
