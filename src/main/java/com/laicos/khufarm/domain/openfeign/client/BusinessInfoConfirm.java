package com.laicos.khufarm.domain.openfeign.client;

import com.laicos.khufarm.domain.openfeign.dto.reponse.BusinessInfoResponse;
import com.laicos.khufarm.domain.openfeign.dto.request.BusinessInfoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "BusinessInfoConfirm", url = "http://api.odcloud.kr/api/nts-businessman/v1")
public interface BusinessInfoConfirm {

    @PostMapping("/validate?serviceKey=${serviceKey.businessInfoConfirm}")
    BusinessInfoResponse confirmBusinessInfo(@RequestBody BusinessInfoRequest businessInfoRequest);

}
