package com.laicos.khufarm.domain.chatBot.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChatBotReqeust {

    @NotNull(message = "질문은 필수 입력값입니다.")
    private String question;
}
