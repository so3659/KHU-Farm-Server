package com.laicos.khufarm.domain.openfeign.client;

import com.laicos.khufarm.domain.chatBot.dto.request.ChatBotReqeust;
import com.laicos.khufarm.domain.chatBot.dto.response.ChatBotResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ChatBot", url = "${chatBot.url}")
public interface ChatBot {

    @PostMapping("/chatBot/ask")
    ChatBotResponse askToChatBot(
            @RequestBody ChatBotReqeust chatBotReqeust
    );
}
