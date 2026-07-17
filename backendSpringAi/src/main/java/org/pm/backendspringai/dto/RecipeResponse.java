package org.pm.backendspringai.dto;

import java.util.List;

public record RecipeResponse(
        String recipeName,
        String preparationTime,
        String cookingTime,
        List<String> ingredients,
        List<String> instructions,
        String nutrition,
        String tips
) {}