package com.snn.recipes.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@NotNull
public class SearchCriteriaDto {
    private Boolean isVegetarian;
    private Integer numberOfServing;
    private List<CriteriaDto> ingredients = new ArrayList<>();
    private List<CriteriaDto> instructions = new ArrayList<>();
}
