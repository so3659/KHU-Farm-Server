package com.laicos.khufarm.domain.delivery.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laicos.khufarm.domain.delivery.converter.DeliveryConverter;
import com.laicos.khufarm.domain.delivery.dto.response.DeliveryErrorResponse;
import com.laicos.khufarm.domain.delivery.dto.response.DeliveryInfoConfirmResponse;
import com.laicos.khufarm.domain.delivery.dto.response.DeliveryStatus;
import com.laicos.khufarm.domain.openfeign.client.DeliveryInfoConfirm;
import com.laicos.khufarm.domain.order.converter.OrderConverter;
import com.laicos.khufarm.domain.order.dto.response.OrderResponse;
import com.laicos.khufarm.domain.order.entity.Order;
import com.laicos.khufarm.domain.order.repository.OrderRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.OrderErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryQueryServiceImpl implements DeliveryQueryService{

    private final OrderRepository orderRepository;
    private final DeliveryInfoConfirm deliveryInfoConfirm;

    @Override
    public DeliveryInfoConfirmResponse getDeliveryInfo(User user, Long orderId){
        Order order = orderRepository.findByUserAndId(user, orderId)
                .orElseThrow(() -> new RestApiException(OrderErrorStatus.ORDER_NOT_FOUND));

        OrderResponse orderResponse = OrderConverter.toOrderResponse(order);

        ResponseEntity<String> response = deliveryInfoConfirm.confirmDeliveryInfo(order.getDeliveryCompany().getId(), order.getDeliveryNumber());

        String responseBody = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 성공한 경우 mapping
            return DeliveryConverter.toDeliveryInfoConfirmResponse(orderResponse, objectMapper.readValue(responseBody, DeliveryStatus.class));
        } catch (JsonProcessingException e) {
            // 실패 응답 처리 (ex: { "message": "tracking number not found." })
            try {
                DeliveryErrorResponse error = objectMapper.readValue(responseBody, DeliveryErrorResponse.class);
                throw new RuntimeException(error.getMessage());
            } catch (Exception ex) {
                throw new RuntimeException("알 수 없는 응답 형태: " + responseBody);
            }
        }


    }
}
