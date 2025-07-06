package com.laicos.khufarm.domain.order.service;

import com.laicos.khufarm.domain.cart.entity.Cart;
import com.laicos.khufarm.domain.cart.repository.CartRepository;
import com.laicos.khufarm.domain.fruit.converter.FruitConverter;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithCount;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.fruit.repository.FruitRepository;
import com.laicos.khufarm.domain.order.converter.OrderConverter;
import com.laicos.khufarm.domain.order.converter.OrderDetailConverter;
import com.laicos.khufarm.domain.order.dto.request.OrderRequest;
import com.laicos.khufarm.domain.order.dto.response.OrderResponse;
import com.laicos.khufarm.domain.order.entity.Order;
import com.laicos.khufarm.domain.order.entity.OrderDetail;
import com.laicos.khufarm.domain.order.repository.OrderRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.CartListErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.FruitErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.OrderErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCommandServiceImpl implements OrderCommandService{

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final FruitRepository fruitRepository;

    @Override
    public OrderResponse orderByCart(User user, OrderRequest.CartOrderRequest request){

        String merchantUid = generateMerchantUid();
        int totalCount = 0;

        List<Cart> cartList = request.getCartId().stream()
                .map(cartId -> cartRepository.findByUserAndId(user, cartId)
                    .orElseThrow(() -> new RestApiException(CartListErrorStatus.CART_NOT_FOUND)))
                .toList();

        List<Fruit> fruitList = cartList.stream()
                .map(Cart::getFruit)
                .toList();

        List<Integer> countList = cartList.stream()
                .map(Cart::getCount)
                .toList();

        for(Fruit fruit : fruitList){
            if(fruit.getStock() == 0){
                throw new RestApiException(OrderErrorStatus.OUT_OF_STOCK);
            }
        }

        validateTotalPrice(fruitList, countList, request.getTotalPrice());

        totalCount = countList.stream()
                .mapToInt(Integer::intValue)
                .sum();

        Order order = OrderConverter.toOrder(request.getShippingInfo(), request.getTotalPrice() ,merchantUid, totalCount, user);

        List<OrderDetail> orderDetailList = cartList.stream()
                .map(cart -> OrderDetailConverter.toOrderDetailWithCart(cart, order))
                .toList();

        for(OrderDetail orderDetail : orderDetailList) {
            order.addOrderDetail(orderDetail);
        }

        orderRepository.save(order);

        List<FruitResponseWithCount> fruitListWithCount = FruitConverter.toFruitDTOListWithCount(fruitList, countList);

        return OrderConverter.toOrderResponse(
                order.getId(),
                request.getShippingInfo(),
                fruitListWithCount
        );
    }

    @Override
    public OrderResponse orderByDirect(User user, OrderRequest.DirectOrderRequest request){

        String merchantUid = generateMerchantUid();
        int totalPrice = 0;

        Fruit fruit = fruitRepository.findById(request.getFruitId())
                .orElseThrow(() -> new RestApiException(FruitErrorStatus.FRUIT_NOT_FOUND));

        if(fruit.getStock() == 0){
            throw new RestApiException(OrderErrorStatus.OUT_OF_STOCK);
        }

        totalPrice=fruit.getPrice()*request.getOrderCount();

        if(totalPrice!= request.getTotalPrice()) {
            throw new RestApiException(OrderErrorStatus.PRICE_NOT_MATCH);
        }

        Order order = OrderConverter.toOrder(request.getShippingInfo(), request.getTotalPrice(), merchantUid, request.getOrderCount(), user);

        OrderDetail orderDetail = OrderDetailConverter.toOrderDetail(fruit, order, request.getOrderCount());
        order.addOrderDetail(orderDetail);

        orderRepository.save(order);

        FruitResponseWithCount fruitResponseWithCount = FruitConverter.toFruitDTOWithCount(fruit, request.getOrderCount());

        return OrderConverter.toOrderResponse(
                order.getId(),
                request.getShippingInfo(),
                List.of(fruitResponseWithCount)
        );
    }

    // 주문번호 생성 메서드
    private String generateMerchantUid() {
        // 현재 날짜와 시간을 포함한 고유한 문자열 생성
        long nano = System.currentTimeMillis();
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDay = today.format(formatter).replace("-", "");

        // 무작위 문자열과 현재 날짜/시간을 조합하여 주문번호 생성
        return formattedDay +'-'+ nano;
    }

    // 총 가격 검증 메서드
    private void validateTotalPrice(List<Fruit> fruitList, List<Integer> countList, Integer totalPrice) {
        int calculateTotalPrice = 0;

        for (int i = 0; i < fruitList.size(); i++) {
            calculateTotalPrice += fruitList.get(i).getPrice() * countList.get(i);
        }

        if(calculateTotalPrice != totalPrice) {
            throw new RestApiException(OrderErrorStatus.PRICE_NOT_MATCH);
        }
    }
}
