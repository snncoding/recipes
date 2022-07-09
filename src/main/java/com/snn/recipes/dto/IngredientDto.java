package com.snn.recipes.dto;

import com.snn.recipes.model.Recipe;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class IngredientDto {

    private Long id;
    private String name;
}
