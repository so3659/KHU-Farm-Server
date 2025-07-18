package com.laicos.khufarm.domain.review.entitiy;

import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.order.entity.OrderDetail;
import com.laicos.khufarm.domain.seller.entity.Seller;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "review")
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private boolean isAnswered;

    @Column(nullable = false)
    private boolean isPointed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="seller_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fruit_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Fruit fruit;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderDetail_id", nullable = false, unique = true)
    private OrderDetail orderDetail;

    @OneToOne(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private ReviewReply reviewReply;

    public void addReviewReply(ReviewReply reviewReply) {
        this.reviewReply = reviewReply;
        reviewReply.setReview(this);
    }

    public void setOrderDetail(OrderDetail orderDetail){
        this.orderDetail= orderDetail;
    }

    public void setIsPointed(boolean isPointed) {
        this.isPointed = isPointed;
    }

    public void setIsAnswered(boolean isAnswered) {
        this.isAnswered = isAnswered;
    }
}
