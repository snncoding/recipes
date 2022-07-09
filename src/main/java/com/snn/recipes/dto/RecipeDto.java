package com.snn.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RecipeDto {

    private Long id;
    @NotEmpty(message = "Recipe name must not be null!")
    @Size(min = 3, max = 100, message = "Recipe name size must be between 3 and 100!")
    private String name;
    private boolean isVegetarian;
    private int numberOfServing;
    @NotEmpty(message = "Recipe ingredients can not be empty")
    private List<IngredientDto> ingredients;
    @NotNull(message = "Recipe instruction must not be null")
    private String instruction;
    private String cookingMethod;
}
