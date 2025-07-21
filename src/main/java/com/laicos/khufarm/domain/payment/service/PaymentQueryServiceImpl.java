package com.laicos.khufarm.domain.payment.service;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithOrder;
import com.laicos.khufarm.domain.payment.repository.PaymentRepository;
import com.laicos.khufarm.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentQueryServiceImpl implements PaymentQueryService{

    private final PaymentRepository paymentRepository;

    @Override
    public Slice<FruitResponseWithOrder> getPaidFruit(User user, Long cursorId, Pageable pageable){

        return paymentRepository.getPaidFruit(user, cursorId, pageable);
    }
}
