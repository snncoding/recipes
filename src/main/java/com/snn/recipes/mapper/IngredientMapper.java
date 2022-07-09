package com.snn.recipes.mapper;

import com.snn.recipes.dto.IngredientDto;
import com.snn.recipes.model.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper {
    /**
     * toIngredientDto maps an Ingredient object to IngredientDto object
     * @param i  Ingredient
     * @return IngredientDto
     */
    public IngredientDto toIngredientDto(Ingredient i){
        return IngredientDto
                .builder()
                .id(i.getId())
                .name(i.getName())
                .build();
    }

    /**
     * toIngredient maps an IngredientDto object to Ingredient object
     * @param i  IngredientDto
     * @return Ingredient
     */
    public Ingredient toIngredient(IngredientDto i){
        return Ingredient
                .builder()
                .id(i.getId())
                .name(i.getName())
                .build();
    }
}
