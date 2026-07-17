package org.pm.backendspringai.controller;

import org.pm.backendspringai.dto.ChatRequest;
import org.pm.backendspringai.dto.ChatResponse;
import org.pm.backendspringai.dto.RecipeRequest;
import org.pm.backendspringai.dto.RecipeResponse;
import org.pm.backendspringai.service.ChatService;
import org.pm.backendspringai.service.RecipeService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
public class GenAiController {

    private final ChatService chatService;
    private final RecipeService recipeService;

    public GenAiController(ChatService chatService, RecipeService recipeService) {
        this.chatService = chatService;
        this.recipeService = recipeService;
    }

    @PostMapping("/chat")
    public ChatResponse askAi(@RequestBody ChatRequest chatRequest) {
        return new ChatResponse(chatService.askAi(chatRequest));
    }

    @GetMapping(value = "/chat/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamChat(@RequestBody ChatRequest chatRequest) {
        return chatService.streamChat(chatRequest);
    }

    @GetMapping("/recipe")
    public RecipeResponse generateRecipe(@RequestBody RecipeRequest recipeRequest) {
        return recipeService.generateRecipe(recipeRequest);
    }
}
