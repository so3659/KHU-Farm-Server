package com.laicos.khufarm.domain.chatBot.dto.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatBotResponse {

    private String answer;
}
