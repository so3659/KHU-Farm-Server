package com.laicos.khufarm.domain.chatBot.service;

import com.laicos.khufarm.domain.chatBot.dto.request.ChatBotReqeust;

public interface ChatBotQueryService {

    String askToChatBot(ChatBotReqeust chatBotRequest);
}
