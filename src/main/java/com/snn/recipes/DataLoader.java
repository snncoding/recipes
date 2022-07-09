package com.snn.recipes;

import com.google.common.collect.Sets;
import com.snn.recipes.model.Ingredient;
import com.snn.recipes.model.Recipe;
import com.snn.recipes.repository.IngredientRepository;
import com.snn.recipes.repository.RecipeRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public void run(String... args) throws Exception {

        Ingredient potatoes = ingredientRepository.save(new Ingredient("potatoes"));
        Ingredient tomatoes = ingredientRepository.save(new Ingredient("tomatoes"));
        Ingredient salt = ingredientRepository.save(new Ingredient("salt"));
        Ingredient onion = ingredientRepository.save(new Ingredient("onion"));
        Ingredient sugar = ingredientRepository.save(new Ingredient("sugar"));
        Ingredient salmon = ingredientRepository.save(new Ingredient("salmon"));
        Ingredient paprika = ingredientRepository.save(new Ingredient("paprika"));
        Ingredient chicken = ingredientRepository.save(new Ingredient("chicken"));
        Ingredient redChilliPowder = ingredientRepository.save(new Ingredient("red chilli powder"));
        Ingredient oil = ingredientRepository.save(new Ingredient("oil"));
        Ingredient garlic = ingredientRepository.save(new Ingredient("garlic"));
        Ingredient ginger = ingredientRepository.save(new Ingredient("ginger"));
        Ingredient egg = ingredientRepository.save(new Ingredient("egg"));
        Ingredient pasta = ingredientRepository.save(new Ingredient("pasta"));
        Ingredient pepper = ingredientRepository.save(new Ingredient("pepper"));
        Ingredient mushroom = ingredientRepository.save(new Ingredient("mushroom"));
        Ingredient cheese = ingredientRepository.save(new Ingredient("cheese"));
        Ingredient butter = ingredientRepository.save(new Ingredient("butter"));
        Ingredient lemon = ingredientRepository.save(new Ingredient("lemon"));


        recipeRepository.save(new Recipe(null, "Baked Chicken Seekh", true, 4,
                Sets.newHashSet(chicken, onion, garlic, garlic, ginger, egg, salt, redChilliPowder, oil, potatoes), "oven", "oven"));

        recipeRepository.save(new Recipe(null, "Home-Style Baked Pasta", true, 6,
                Sets.newHashSet(pasta, oil, onion, pepper, garlic, mushroom, cheese), "oven", "oven"));

        recipeRepository.save(new Recipe(null, "Lemon Tart", true, 4,
                Sets.newHashSet(lemon, butter, egg, sugar), "oven", "oven"));

        recipeRepository.save(new Recipe(null, "Wild salmon with radish & orange slaw", true, 12,
                Sets.newHashSet(salmon, salt, oil, lemon, pepper, tomatoes, paprika), "oven", "oven"));


    }
}
