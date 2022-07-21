package com.snn.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RecipeDto {

    private Long id;
    private boolean isVegetarian;
    @Min(value = 1, message = "numberOfServing must be greater than 1")
    @Max(value = 10, message = "numberOfServing must be less than 10")
    private int numberOfServing;
    @NotEmpty(message = "Recipe ingredients can not be empty")
    private List<IngredientDto> ingredients;
    @NotNull(message = "Recipe instruction can not be null")
    @Size(min = 10, max = 500, message = "Recipe instruction size must be between 10 and 500!")
    private String instruction;
}
