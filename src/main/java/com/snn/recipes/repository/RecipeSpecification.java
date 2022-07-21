package com.snn.recipes.repository;

import com.snn.recipes.dto.CriteriaDto;
import com.snn.recipes.dto.SearchCriteriaDto;
import com.snn.recipes.model.Ingredient;
import com.snn.recipes.model.Recipe;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates a dynamic query
 */
@Component
public class RecipeSpecification {
    /**
     * Creates dynamic query by SearchCriteriaDto
     *
     * @param dto Includes requested criterias
     * @return Specification<Recipe>
     */
    public Specification<Recipe> getRecipes(SearchCriteriaDto dto) {
        return (root, query, cBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (dto.getIsVegetarian() != null) {
                predicates.add(cBuilder.equal(root.get("isVegetarian"), dto.getIsVegetarian()));
            }
            if (dto.getNumberOfServing() != null) {
                predicates.add(cBuilder.equal(root.get("numberOfServing"), dto.getNumberOfServing()));
            }
            if (dto.getIngredients() != null && !dto.getIngredients().isEmpty()) {
                for (CriteriaDto crtDto : dto.getIngredients()) {
                    Subquery<Ingredient> subquery = query.subquery(Ingredient.class);
                    Root<Ingredient> subqueryRoot = subquery.from(Ingredient.class);

                    Predicate subPredicateName = cBuilder.equal(subqueryRoot.get("name"), crtDto.getCriteria());
                    Predicate subPredicateRecipeId = cBuilder.equal(subqueryRoot.get("recipeId"), root.get("id"));

                    subquery.select(subqueryRoot.get("id")).where(subPredicateRecipeId, subPredicateName);
                    if (crtDto.isIncluded()) {
                        predicates.add(cBuilder.exists(subquery));
                    } else {
                        predicates.add(cBuilder.not(cBuilder.exists(subquery)));
                    }
                }
            }

            if (dto.getInstructions() != null && !dto.getInstructions().isEmpty()) {
                for (CriteriaDto crtDto : dto.getInstructions()) {
                    if (crtDto.isIncluded()) {
                        predicates.add(cBuilder.like(root.get("instruction"), "%" + crtDto.getCriteria() + "%"));
                    } else {
                        predicates.add(cBuilder.not(cBuilder.like(root.get("instruction"), "%" + crtDto.getCriteria() + "%")));
                    }
                }
            }
            return cBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
