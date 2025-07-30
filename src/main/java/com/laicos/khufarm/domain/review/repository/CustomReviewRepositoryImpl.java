package com.laicos.khufarm.domain.review.repository;

import com.laicos.khufarm.domain.fruit.converter.FruitConverter;
import com.laicos.khufarm.domain.fruit.dto.response.FruitResponse;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.review.converter.ReviewConverter;
import com.laicos.khufarm.domain.review.converter.ReviewReplyConverter;
import com.laicos.khufarm.domain.review.dto.ReviewReadCondition;
import com.laicos.khufarm.domain.review.dto.response.MyReviewResponse;
import com.laicos.khufarm.domain.review.dto.response.ReviewReplyResponse;
import com.laicos.khufarm.domain.review.dto.response.ReviewResponse;
import com.laicos.khufarm.domain.review.dto.response.ReviewResponseWithFruit;
import com.laicos.khufarm.domain.review.entitiy.Review;
import com.laicos.khufarm.domain.review.entitiy.ReviewReply;
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

import static com.laicos.khufarm.domain.review.entitiy.QReview.review;

@Repository
@RequiredArgsConstructor
public class CustomReviewRepositoryImpl implements CustomReviewRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final SellerRepository sellerRepository;

    @Override
    public Slice<ReviewResponse> getAllReviews(Long cursorId, ReviewReadCondition reviewReadCondition, Pageable pageable){

        List<Review> reviewList = jpaQueryFactory.selectFrom(review)
                .leftJoin(review.fruit).fetchJoin()
                .leftJoin(review.seller).fetchJoin()
                .leftJoin(review.reviewReply).fetchJoin()
                .leftJoin(review.user).fetchJoin()
                .leftJoin(review.orderDetail).fetchJoin()
                .where(
                        ltCursorId(cursorId),// 커서 조건
                        eqFruitId(reviewReadCondition.getFruitId()), // 과일 ID 조건
                        eqUserId(reviewReadCondition.getUser()) // 사용자 ID 조건
                )
                .orderBy(review.id.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<ReviewReply> replies = reviewList.stream()
                .map(Review::getReviewReply)
                .toList();

        List<ReviewReplyResponse> replyList = replies.stream()
                .map(reply -> reply != null ? ReviewReplyConverter.toReviewReplyDTO(reply.getContent()) : null)
                .toList();

        List<ReviewResponse> content = ReviewConverter.toReviewDTOList(reviewList, replyList);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<MyReviewResponse> getMyReviews(User user, Long cursorId, Pageable pageable){

        List<Review> reviewList = jpaQueryFactory.selectFrom(review)
                .leftJoin(review.fruit).fetchJoin()
                .leftJoin(review.seller).fetchJoin()
                .leftJoin(review.reviewReply).fetchJoin()
                .leftJoin(review.user).fetchJoin()
                .leftJoin(review.orderDetail).fetchJoin()
                .where(
                        ltCursorId(cursorId),// 커서 조건
                        eqUserId(user.getId()) // 사용자 ID 조건
                )
                .orderBy(review.id.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<ReviewReply> replies = reviewList.stream()
                .map(Review::getReviewReply)
                .toList();

        List<ReviewReplyResponse> replyList = replies.stream()
                .map(reply -> reply != null ? ReviewReplyConverter.toReviewReplyDTO(reply.getContent()) : null)
                .toList();

        List<Fruit> fruitList = reviewList.stream()
                .map(Review::getFruit)
                .toList();

        List<Integer> orderCountList = reviewList.stream()
                .map(r -> r.getOrderDetail().getCount())
                .toList();

        List<ReviewResponse> reviewDTOList = ReviewConverter.toReviewDTOList(reviewList, replyList);

        List<FruitResponse> fruitDTOList = FruitConverter.toFruitDTOList(fruitList);

        List<MyReviewResponse> content = ReviewConverter.toMyReviewDTOList(fruitDTOList, orderCountList, reviewDTOList);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<ReviewResponseWithFruit> getSellerReviews(User user, Long cursorId, Pageable pageable, ReviewReadCondition reviewReadCondition){
        Seller seller = sellerRepository.findByUser(user)
                .orElseThrow(() -> new RestApiException(SellerErrorStatus.SELLER_NOT_FOUND));

        List<Review> reviewList = jpaQueryFactory.selectFrom(review)
                .leftJoin(review.fruit).fetchJoin()
                .leftJoin(review.seller).fetchJoin()
                .leftJoin(review.reviewReply).fetchJoin()
                .leftJoin(review.user).fetchJoin()
                .leftJoin(review.orderDetail).fetchJoin()
                .where(
                        ltCursorId(cursorId), // 커서 조건
                        eqSellerId(seller.getId()), // 판매자 ID 조건
                        eqFruitId(reviewReadCondition.getFruitId()), // 과일 ID 조건
                        eqIsAnswered(reviewReadCondition.getIsAnswered()) // 답변 여부 조건
                )
                .orderBy(review.id.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        List<ReviewReply> replies = reviewList.stream()
                .map(Review::getReviewReply)
                .toList();

        List<Fruit> fruitList = reviewList.stream()
                .map(Review::getFruit)
                .toList();

        List<ReviewReplyResponse> replyList = replies.stream()
                .map(reply -> reply != null ? ReviewReplyConverter.toReviewReplyDTO(reply.getContent()) : null)
                .toList();

        List<FruitResponse> fruitDTOList = FruitConverter.toFruitDTOList(fruitList);

        List<ReviewResponseWithFruit> content = ReviewConverter.toReviewWithFruitDTOList(reviewList, replyList,fruitDTOList);

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
    }

    private BooleanExpression ltCursorId(Long cursorId) {
        return (cursorId == null) ? null : review.id.lt(cursorId);
    }

    private BooleanExpression eqFruitId(Long fruitId) {
        return (fruitId == null) ? null : review.fruit.id.eq(fruitId);
    }

    private BooleanExpression eqUserId(Long userId) {
        return (userId == null) ? null : review.user.id.eq(userId);
    }

    private BooleanExpression eqUserId(User user) {
        return (user == null) ? null : review.user.id.eq(user.getId());
    }

    private BooleanExpression eqSellerId(Long sellerId) {
        return (sellerId == null) ? null : review.seller.id.eq(sellerId);
    }

    private BooleanExpression eqIsAnswered(Boolean isAnswered) {
        return (isAnswered == null) ? null : review.isAnswered.eq(isAnswered);
    }
}
