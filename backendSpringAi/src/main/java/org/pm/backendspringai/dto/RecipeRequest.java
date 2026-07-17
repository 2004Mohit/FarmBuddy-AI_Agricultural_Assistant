package org.pm.backendspringai.dto;

public record RecipeRequest(
        String ingredients,
        String cuisine,
        String dietaryRestrictions
) {
}