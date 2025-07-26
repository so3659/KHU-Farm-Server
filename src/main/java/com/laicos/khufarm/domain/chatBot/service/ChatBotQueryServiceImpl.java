package com.laicos.khufarm.domain.chatBot.service;

import com.laicos.khufarm.domain.chatBot.dto.request.ChatBotReqeust;
import com.laicos.khufarm.domain.chatBot.dto.response.ChatBotResponse;
import com.laicos.khufarm.domain.openfeign.client.ChatBot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatBotQueryServiceImpl implements  ChatBotQueryService {

    private final ChatBot chatBotClient;

    @Override
    public String askToChatBot(ChatBotReqeust chatBotRequest){
        try {
            // FeignClient를 통해 API 호출
            ChatBotResponse response = chatBotClient.askToChatBot(chatBotRequest);
            return response.getAnswer();
        } catch (Exception e) {
            e.printStackTrace();
            return "챗봇 답변을 가져오는 중 오류가 발생했습니다: " + e.getMessage();
        }
    }
}
