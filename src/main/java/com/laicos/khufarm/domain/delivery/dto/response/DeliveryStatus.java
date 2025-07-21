package com.laicos.khufarm.domain.delivery.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryStatus {
    private Carrier carrier;
    private Party from;
    private Party to;
    private List<Progress> progresses;
    private Status state;

    @JsonProperty("_deprecated_warn") // 언더스코어 포함된 필드
    private String deprecatedWarn;

    @Getter
    @Setter
    public static class Carrier {
        private String id;
        private String name;
        private String tel;
    }

    @Getter @Setter
    public static class Party {
        private String name;
        private String time;
    }

    @Getter @Setter
    public static class Progress {
        private String time;
        private Status status;
        private Location location;
        private String description;
    }

    @Getter @Setter
    public static class Status {
        private String id;
        private String text;
    }

    @Getter @Setter
    public static class Location {
        private String name;
    }
}
