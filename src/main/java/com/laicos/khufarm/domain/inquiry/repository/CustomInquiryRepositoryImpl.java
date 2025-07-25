package com.laicos.khufarm.domain.inquiry.repository;

import com.laicos.khufarm.domain.inquiry.converter.InquiryConverter;
import com.laicos.khufarm.domain.inquiry.converter.InquiryReplyConverter;
import com.laicos.khufarm.domain.inquiry.dto.InquiryReadCondition;
import com.laicos.khufarm.domain.inquiry.dto.response.InquiryReplyResponse;
import com.laicos.khufarm.domain.inquiry.dto.response.InquiryResponse;
import com.laicos.khufarm.domain.inquiry.entity.Inquiry;
import com.laicos.khufarm.domain.inquiry.entity.InquiryReply;
import com.laicos.khufarm.domain.seller.entity.Seller;
import com.laicos.khufarm.domain.seller.repository.SellerRepository;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.exception.RestApiException;
import com.laicos.khufarm.global.common.exception.code.status.SellerErrorStatus;
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
    private final SellerRepository sellerRepository;

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
                .orderBy(inquiry.id.desc())
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

    @Override
    public Slice<InquiryResponse> getSellerInquiry(Long cursorId, User user, Pageable pageable, InquiryReadCondition inquiryReadCondition){
        Seller seller = sellerRepository.findByUser(user)
                .orElseThrow(() -> new RestApiException(SellerErrorStatus.SELLER_NOT_FOUND));

        List<Inquiry> inquiryList = jpaQueryFactory.selectFrom(inquiry)
                .leftJoin(inquiry.fruit).fetchJoin()
                .leftJoin(inquiry.seller).fetchJoin()
                .leftJoin(inquiry.inquiryReply).fetchJoin()
                .leftJoin(inquiry.user).fetchJoin()
                .where(
                        gtCursorId(cursorId),// 커서 조건
                        eqSellerId(seller.getId()), // 판매자 ID 조건
                        eqIsAnswered(inquiryReadCondition.isAnswered())
                )
                .orderBy(inquiry.id.desc())
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

    private BooleanExpression eqSellerId(Long sellerId) {
        return (sellerId == null) ? null : inquiry.seller.id.eq(sellerId);
    }

    private BooleanExpression eqIsAnswered(Boolean isAnswered) {
        return (isAnswered == null) ? null : inquiry.isAnswered.eq(isAnswered);
    }
}
