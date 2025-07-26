package com.laicos.khufarm.domain.chatBot.controller;

import com.laicos.khufarm.domain.chatBot.dto.request.ChatBotReqeust;
import com.laicos.khufarm.domain.chatBot.service.ChatBotQueryService;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chatBot")
@RequiredArgsConstructor
@Validated
@Tag(name = "chatBot", description = "챗봇 관련 API")
public class ChatBotController {

    private final ChatBotQueryService chatBotQueryService;

    @GetMapping
    public BaseResponse<String> askToChatBot(
            @Valid ChatBotReqeust chatBotReqeust
            ) {
        String chatBotResponse = chatBotQueryService.askToChatBot(chatBotReqeust);
        return BaseResponse.onSuccess(chatBotResponse);
    }
}
