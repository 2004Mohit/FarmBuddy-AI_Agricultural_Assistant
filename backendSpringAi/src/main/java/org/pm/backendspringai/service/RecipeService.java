package org.pm.backendspringai.service;

import org.pm.backendspringai.dto.RecipeRequest;
import org.pm.backendspringai.dto.RecipeResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {

    private final ChatClient chatClient;

    public RecipeService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public RecipeResponse generateRecipe(RecipeRequest recipeRequest
                                 ) {

        // Reusable Prompt with Placeholders
        PromptTemplate promptTemplate = new PromptTemplate(new ClassPathResource("prompts/recipe-template.st"));

        Prompt prompt = promptTemplate.create(
                Map.of(
                        "ingredients", recipeRequest.ingredients(),
                        "cuisine", recipeRequest.cuisine(),
                        "dietaryRestrictions", recipeRequest.dietaryRestrictions()
                )
        );

        // Using Spring AI's entity mapping :
        // Spring AI asks the model to generate JSON matching RecipeResponse and maps it into your Java record.
        return chatClient.prompt(prompt)
                .call()
                .entity(RecipeResponse.class);
    }
}
