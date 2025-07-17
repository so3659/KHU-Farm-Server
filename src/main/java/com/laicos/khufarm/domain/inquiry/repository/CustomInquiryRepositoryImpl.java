package com.laicos.khufarm.domain.inquiry.repository;

import com.laicos.khufarm.domain.inquiry.converter.InquiryConverter;
import com.laicos.khufarm.domain.inquiry.converter.InquiryReplyConverter;
import com.laicos.khufarm.domain.inquiry.dto.InquiryReadCondition;
import com.laicos.khufarm.domain.inquiry.dto.response.InquiryReplyResponse;
import com.laicos.khufarm.domain.inquiry.dto.response.InquiryResponse;
import com.laicos.khufarm.domain.inquiry.entity.Inquiry;
import com.laicos.khufarm.domain.inquiry.entity.InquiryReply;
import com.laicos.khufarm.domain.user.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.laicos.khufarm.domain.inquiry.entity.QInquiry.inquiry;

@Repository
@RequiredArgsConstructor
public class CustomInquiryRepositoryImpl implements CustomInquiryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<InquiryResponse> getAllInquiry(Long cursorId, InquiryReadCondition inquiryReadCondition, Pageable pageable){

        List<Inquiry> inquiryList = jpaQueryFactory.selectFrom(inquiry)
                .leftJoin(inquiry.fruit).fetchJoin()
                .leftJoin(inquiry.seller).fetchJoin()
                .leftJoin(inquiry.inquiryReply).fetchJoin()
                .leftJoin(inquiry.user).fetchJoin()
                .where(
                        gtCursorId(cursorId),// 커서 조건
                        eqFruitId(inquiryReadCondition.getFruitId()), // 과일 ID 조건
                        eqUserId(inquiryReadCondition.getUser()) // 사용자 ID 조건
                )
                .orderBy(inquiry.id.asc())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<InquiryReply> inquiries = inquiryList.stream()
                .map(Inquiry::getInquiryReply)
                .toList();

        List<InquiryReplyResponse> replyList = inquiries.stream()
                .map(reply -> reply != null ? InquiryReplyConverter.toInquiryReplyDTO(reply) : null)
                .toList();

        List<InquiryResponse> content = InquiryConverter.toInquiryDTOList(inquiryList, replyList);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression gtCursorId(Long cursorId) {
        return (cursorId == null) ? null : inquiry.id.gt(cursorId);
    }

    private BooleanExpression eqFruitId(Long fruitId) {
        return (fruitId == null) ? null : inquiry.fruit.id.eq(fruitId);
    }

    private BooleanExpression eqUserId(User user) {
        return (user == null) ? null : inquiry.user.id.eq(user.getId());
    }
}
