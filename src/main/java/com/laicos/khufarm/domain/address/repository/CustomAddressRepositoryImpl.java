package com.laicos.khufarm.domain.address.repository;

import com.laicos.khufarm.domain.address.converter.AddressConverter;
import com.laicos.khufarm.domain.address.dto.response.AddressResponse;
import com.laicos.khufarm.domain.address.entity.Address;
import com.laicos.khufarm.domain.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.laicos.khufarm.domain.address.entity.QAddress.*;

@Repository
@RequiredArgsConstructor
public class CustomAddressRepositoryImpl implements CustomAddressRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<AddressResponse> getAddress(User user, Long cursorId, Pageable pageable){

        List<Address> addressList = jpaQueryFactory.selectFrom(address1)
                .leftJoin(address1.user).fetchJoin()
                .where(
                        gtCursorId(cursorId), // 커서 조건
                        eqUserId(user.getId()) // 사용자 ID 조건
                )
                .orderBy(address1.id.asc())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<AddressResponse> content = AddressConverter.toAddressResponseList(addressList);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression eqUserId(Long userId) {
        return (userId == null) ? null : address1.user.id.eq(userId);
    }

    private BooleanExpression gtCursorId(Long cursorId) {
        return (cursorId == null) ? null : address1.id.gt(cursorId);
    }

}
