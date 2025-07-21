package com.laicos.khufarm.domain.openfeign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "DeliveryInfoConfirm", url = "https://apis.tracker.delivery")
public interface DeliveryInfoConfirm {

    @GetMapping("/carriers/{carrierId}/tracks/{trackId}")
    ResponseEntity<String> confirmDeliveryInfo(
            @PathVariable("carrierId") String carrierId,
            @PathVariable("trackId") String trackId
    );
}

