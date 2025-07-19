package com.laicos.khufarm.domain.order.service;

import com.laicos.khufarm.domain.address.converter.AddressConverter;
import com.laicos.khufarm.domain.address.dto.response.AddressResponse;
import com.laicos.khufarm.domain.address.entity.Address;
import com.laicos.khufarm.domain.address.repository.AddressRepository;
import com.laicos.khufarm.domain.cart.entity.Cart;
import com.laicos.khufarm.domain.cart.repository.CartRepository;
import com.laicos.khufarm.domain.fruit.converter.FruitConverter;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithCount;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.fruit.repository.FruitRepository;
import com.laicos.khufarm.domain.order.converter.OrderConverter;
import com.laicos.khufarm.domain.order.dto.response.PrePareOrderResponse;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.CartListErrorStatus;
import com.laicos.khufarm.global.common.exception.code.status.FruitErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderQueryServiceImpl implements OrderQueryService {

    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final FruitRepository fruitRepository;

    @Override
    public PrePareOrderResponse getPrepareCartOrder(User user, List<Long> cartIds){

        List<Cart> cartList = cartIds.stream()
                .map(cartId -> cartRepository.findByUserAndId(user, cartId)
                        .orElseThrow(() -> new RestApiException(CartListErrorStatus.CART_NOT_FOUND)))
                .toList();

        List<Fruit> fruitList = cartList.stream()
                .map(Cart::getFruit)
                .toList();

        List<FruitResponseWithCount> content = FruitConverter.toFruitDTOListWithCount(fruitList, cartList);

        Address address = addressRepository.findByUserAndIsDefaultTrue(user)
                .orElse(null);

        if (address == null) {
            return OrderConverter.toPrePareOrderResponse(null, content);
        } else {
            AddressResponse addressResponse = AddressConverter.toAddressResponse(address);
            return OrderConverter.toPrePareOrderResponse(addressResponse, content);
        }
    }

    @Override
    public PrePareOrderResponse getPrepareDirectOrder(User user, Long fruitId, Integer orderCount){

        Fruit fruit = fruitRepository.findById(fruitId)
                .orElseThrow(() -> new RestApiException(FruitErrorStatus.FRUIT_NOT_FOUND));

        List<FruitResponseWithCount> fruitResponses =
                List.of(FruitConverter.toFruitDTOOnlyCount(fruit, orderCount));


        Address address = addressRepository.findByUserAndIsDefaultTrue(user)
                .orElse(null);

        if (address == null) {
            return OrderConverter.toPrePareOrderResponse(null, fruitResponses);
        } else {
            AddressResponse addressResponse = AddressConverter.toAddressResponse(address);
            return OrderConverter.toPrePareOrderResponse(addressResponse, fruitResponses);
        }
    }
}
