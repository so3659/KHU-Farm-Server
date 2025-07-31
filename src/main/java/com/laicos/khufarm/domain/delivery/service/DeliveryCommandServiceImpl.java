package com.laicos.khufarm.domain.delivery.service;

import com.laicos.khufarm.domain.delivery.dto.request.DeliveryInfoRequest;
import com.laicos.khufarm.domain.delivery.enums.DeliveryCompany;
import com.laicos.khufarm.domain.order.entity.OrderDetail;
import com.laicos.khufarm.domain.order.enums.OrderStatus;
import com.laicos.khufarm.domain.order.repository.OrderDetailRepository;
import com.laicos.khufarm.domain.order.repository.OrderRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.OrderErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DeliveryCommandServiceImpl implements DeliveryCommandService{

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final DeliveryTrackerApiClient deliveryTrackerApiClient; // API 호출 클라이언트 or 서비스
    private final DeliveryQueryService deliveryQueryService;

    @Value("${tracker.callBackUrl}")
    private String callbackUrl;

    @Override
    public void updateDeliveryStatus(User user, Long orderDetailId, DeliveryInfoRequest deliveryInfoRequest) {

        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new RestApiException(OrderErrorStatus.ORDER_DETAIL_NOT_FOUND));

        DeliveryCompany deliveryCompany = DeliveryCompany.fromName(deliveryInfoRequest.getDeliveryCompany());
        String deliveryNumber = deliveryInfoRequest.getDeliveryNumber();

        orderDetail.updateDeliveryInfo(deliveryCompany, deliveryNumber);
        orderDetail.updateOrderStatus(OrderStatus.SHIPPING);
        orderDetail.getOrder().updateOrderStatus(OrderStatus.SHIPPING);

        // Webhook 등록을 위해 딜리버리트래커 API 호출
        try {
            // 48시간 후 expirationTime (ISO8601)
            ZonedDateTime expiration = ZonedDateTime.now(ZoneOffset.UTC).plusHours(48);
            String expirationTimeIso = expiration.format(DateTimeFormatter.ISO_INSTANT);

            deliveryTrackerApiClient.registerTrackWebhook(
                    deliveryCompany.getId(),
                    deliveryNumber,
                    callbackUrl,
                    expirationTimeIso
            );
        } catch (Exception e) {
            // 등록 실패 로그 기록 등 필요 시 추가
            // 단, 메서드 흐름을 막지 않도록 예외는 잡아둠
            log.error("Webhook 등록 실패: {}", e.getMessage());
        }
    }

    @Override
    public void handleDeliveryStatusCallback(String carrierId, String trackingNumber) {

        // 운송장 번호로 OrderDetail 조회
        OrderDetail orderDetail = orderDetailRepository.findByDeliveryCompanyAndDeliveryNumber(
                DeliveryCompany.ofId(carrierId), trackingNumber
        ).orElseThrow(() -> new RestApiException(OrderErrorStatus.ORDER_DETAIL_NOT_FOUND));

        String deliveryState = deliveryQueryService.getDeliveryStateInfo(orderDetail.getOrder().getUser(), orderDetail.getId());

        if(deliveryState.contains("완료")) {
            orderDetail.updateOrderStatus(OrderStatus.SHIPMENT_COMPLETED);
        }
    }
}