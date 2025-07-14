package com.laicos.khufarm.domain.image.extractor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlExtractor {

    public static List<String> extractUrls(String description) {
        List<String> containedUrls = new ArrayList<>();

        // http, https, ftp 프로토콜을 포함하는 URL을 찾기 위한 정규 표현식
        String urlRegex = "\\b(?:https?|ftp):\\/\\/\\S+\\b";

        // 정규 표현식을 컴파일합니다.
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(description);

        while (matcher.find()) {
            containedUrls.add(matcher.group());
        }

        return containedUrls;
    }
}
