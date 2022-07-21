package com.snn.recipes.controller;

import com.snn.recipes.dto.RecipeDto;
import com.snn.recipes.dto.SearchCriteriaDto;
import com.snn.recipes.service.RecipeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @ApiOperation("Returns all recipe")
    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @ApiOperation("Gets a recipe by its id")
    @GetMapping("/{recipeId}")
    public RecipeDto fetch(@PathVariable Long recipeId){
        return service.fetch(recipeId);
    }

    @ApiOperation("Saves a recipe")
    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestBody RecipeDto dto){
        service.add(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("Updates a recipe")
    @PutMapping("/{recipeId}")
    public void update(@PathVariable Long recipeId, @Valid @RequestBody RecipeDto dto){
        service.update(recipeId, dto);
    }

    @ApiOperation("Deletes a recipe")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }

    @ApiOperation("Search request for Recipe")
    @PostMapping("/search")
    public ResponseEntity<List<RecipeDto>> search(@RequestBody SearchCriteriaDto criteria){
        return ResponseEntity.ok(service.search(criteria));
    }
}
