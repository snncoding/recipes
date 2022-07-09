package com.snn.recipes.controller;

import com.snn.recipes.dto.RecipeDto;
import com.snn.recipes.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api("Includes Recipe endpoints")
@AllArgsConstructor
@RestController
@RequestMapping("api/recipe")
public class RecipeController {

    private final RecipeService service;

    @ApiOperation("Return all recipe data")
    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @ApiOperation("Save a recipe")
    @PostMapping
    public void save(@Valid @RequestBody RecipeDto dto){
        service.save(dto);
    }

    @ApiOperation("Update a recipe")
    @PutMapping("/{recipeId}")
    public void update(@RequestParam Long recipeId, @Valid @RequestBody RecipeDto dto){
        service.update(recipeId, dto);
    }

    @ApiOperation("Delete a recipe")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
