package com.laicos.khufarm.domain.review;

import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.order.entity.Order;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.domain.user.entity.mapping.Seller;
import com.laicos.khufarm.global.base.BaseEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;
}
