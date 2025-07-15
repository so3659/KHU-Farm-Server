package com.laicos.khufarm.domain.image.extractor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UrlExtractor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<String> extractUrlsFromDescription(String descriptionJson) {
        List<String> urls = new ArrayList<>();
        if (descriptionJson == null || descriptionJson.isBlank()) {
            return urls;
        }

        try {
            // JSON 문자열을 JsonNode 트리로 파싱
            JsonNode rootNode = objectMapper.readTree(descriptionJson);

            if (rootNode.isArray()) {
                // 배열의 각 요소를 순회
                for (JsonNode node : rootNode) {
                    // {"insert": {"image": "..."}} 구조를 찾습니다.
                    if (node.has("insert")) {
                        JsonNode insertNode = node.get("insert");
                        if (insertNode.isObject() && insertNode.has("image")) {
                            urls.add(insertNode.get("image").asText());
                        }
                    }
                }
            }
        } catch (IOException e) {
            // JSON 파싱 중 오류 발생 시 로그를 남기거나 예외 처리를 할 수 있습니다.
            // 여기서는 간단히 빈 리스트를 반환합니다.
            System.err.println("Description 필드 JSON 파싱 실패: " + e.getMessage());
        }

        return urls;
    }
}