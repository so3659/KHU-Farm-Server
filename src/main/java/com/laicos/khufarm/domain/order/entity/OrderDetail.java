package com.laicos.khufarm.domain.order.entity;

import com.laicos.khufarm.domain.delivery.enums.DeliveryCompany;
import com.laicos.khufarm.domain.delivery.enums.converter.DeliveryCompanyConverter;
import com.laicos.khufarm.domain.fruit.entity.Fruit;
import com.laicos.khufarm.domain.order.enums.OrderStatus;
import com.laicos.khufarm.domain.order.enums.converter.OrderStatusConverter;
import com.laicos.khufarm.domain.review.entitiy.Review;
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

    @Convert(converter = OrderStatusConverter.class)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Convert(converter = DeliveryCompanyConverter.class)
    private DeliveryCompany deliveryCompany;

    private String deliveryNumber;

    private String refundReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="fruit_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Fruit fruit;

    @OneToOne(mappedBy = "orderDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;

    protected void setOrder(Order order) {
        this.order = order;
    }

    public void updateReviewStatus(boolean isReviewed) {
        this.isReviewed = isReviewed;
    }

    public void addReview(Review review) {
        this.review = review;
        review.setOrderDetail(this);
    }

    public void updateDeliveryInfo(DeliveryCompany deliveryCompany, String deliveryNumber) {
        this.deliveryCompany = deliveryCompany;
        this.deliveryNumber = deliveryNumber;
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }
}
