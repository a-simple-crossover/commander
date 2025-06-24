package com.master.commander.ai.chat.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;
    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/ai")
    String generation(@RequestParam("userInput") String userInput) {
        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }
}
