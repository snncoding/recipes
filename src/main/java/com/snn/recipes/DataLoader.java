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

        /*Ingredient potatoes = new Ingredient("potatoes");
        Ingredient tomatoes = new Ingredient("tomatoes");
        Ingredient salt = new Ingredient("salt");
        Ingredient onion = new Ingredient("onion");
        Ingredient sugar = new Ingredient("sugar");
        Ingredient salmon = new Ingredient("salmon");
        Ingredient paprika = new Ingredient("paprika");
        Ingredient chicken = new Ingredient("chicken");
        Ingredient redChilliPowder = new Ingredient("red chilli powder");
        Ingredient oil = new Ingredient("oil");
        Ingredient garlic = new Ingredient("garlic");
        Ingredient ginger = new Ingredient("ginger");
        Ingredient egg = new Ingredient("egg");
        Ingredient pasta = new Ingredient("pasta");
        Ingredient pepper = new Ingredient("pepper");
        Ingredient mushroom = new Ingredient("mushroom");
        Ingredient cheese = new Ingredient("cheese");
        Ingredient butter = new Ingredient("butter");
        Ingredient lemon = new Ingredient("lemon");*/

        recipeRepository.save(new Recipe(null, true, 4,
                Sets.newHashSet(i("chicken"), i("onion"), i("garlic"), i("garlic"), i("ginger"), i("egg"), i("salt"), i("redChilliPowder"), i("oil"), i("potatoes")), "oven"));

        recipeRepository.save(new Recipe(null,  false, 6,
                Sets.newHashSet(i("pasta"), i("oil"), i("onion"), i("pepper"), i("garlic"), i("mushroom"), i("cheese")), "grill"));

        recipeRepository.save(new Recipe(null, true, 4,
                Sets.newHashSet(i("lemon"), i("butter"), i("egg"), i("sugar")), "oven"));

        recipeRepository.save(new Recipe(null, false, 8,
                Sets.newHashSet(i("salmon"), i("salt"), i("oil"), i("pepper"), i("tomatoes"), i("paprika")), "grill"));

        recipeRepository.save(new Recipe(null, true, 8,
                Sets.newHashSet(i("potatoes"), i("salt"), i("oil"), i("lemon"), i("pepper"), i("tomatoes"), i("paprika")), "grill"));

        recipeRepository.save(new Recipe(null, true, 8,
                Sets.newHashSet(i("potatoes"), i("salt"), i("oil"), i("lemon"), i("pepper"), i("mushroom"), i("paprika")), "oven"));
    }

    private Ingredient i(String name){
        return new Ingredient(name);
    }
}
