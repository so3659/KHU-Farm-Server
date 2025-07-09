package com.laicos.khufarm.domain.order.entity;

import com.laicos.khufarm.domain.fruit.entity.Fruit;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "orderDetail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false)
    private Integer weight;

    @Column(nullable = false)
    private boolean isReviewed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fruit_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Fruit fruit;

    protected void setOrder(Order order) {
        this.order = order;
    }

    public void updateReviewStatus(boolean isReviewed) {
        this.isReviewed = isReviewed;
    }
}
