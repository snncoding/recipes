package com.snn.recipes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RecipeDto {

    private Long id;
    private boolean isVegetarian;
    private int numberOfServing;
    @NotEmpty(message = "Recipe ingredients can not be empty")
    private List<IngredientDto> ingredients;
    @NotNull(message = "Recipe instruction can not be null")
    @Size(min = 10, max = 500, message = "Recipe instruction size must be between 3 and 100!")
    private String instruction;
}
