package com.snn.recipes.mapper;

import com.snn.recipes.dto.RecipeDto;
import com.snn.recipes.model.Recipe;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
@AllArgsConstructor
public class RecipeMapper {

    public final IngredientMapper ingredientMapper;

    public RecipeDto toRecipeDto(Recipe r) {
        return RecipeDto
                .builder()
                .id(r.getId())
                .name(r.getName())
                .isVegetarian(r.isVegetarian())
                .instruction(r.getInstruction())
                .cookingMethod(r.getCookingMethod())
                .numberOfServing(r.getNumberOfServing())
                .ingredients(r.getIngredients()
                        .stream()
                        .map(ingredientMapper::toIngredientDto)
                        .collect(toList()))
                .build();
    }

    public Recipe toRecipe(RecipeDto r) {
        return Recipe
                .builder()
                .id(r.getId())
                .name(r.getName())
                .isVegetarian(r.isVegetarian())
                .instruction(r.getInstruction())
                .cookingMethod(r.getCookingMethod())
                .numberOfServing(r.getNumberOfServing())
                .ingredients(r.getIngredients()
                        .stream()
                        .map(ingredientMapper::toIngredient)
                        .collect(toSet()))
                .build();
    }
}
