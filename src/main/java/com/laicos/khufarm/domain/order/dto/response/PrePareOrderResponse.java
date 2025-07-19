package com.laicos.khufarm.domain.order.dto.response;

import com.laicos.khufarm.domain.address.dto.response.AddressResponse;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithCount;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PrePareOrderResponse {

        private AddressResponse addressResponse;
        private List<FruitResponseWithCount> fruitResponseWithCount;
}
