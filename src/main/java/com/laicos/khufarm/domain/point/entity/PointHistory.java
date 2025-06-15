package com.laicos.khufarm.domain.point.entity;

import com.laicos.khufarm.domain.point.enums.PointEventType;
import com.laicos.khufarm.domain.point.enums.converter.PointEventTypeConverter;
import com.laicos.khufarm.domain.user.entity.User;
import com.laicos.khufarm.global.common.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Builder
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Table(name = "pointHistory")
public class PointHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer point;

    @Convert(converter = PointEventTypeConverter.class)
    @Column(nullable = false)
    private PointEventType pointEventType;

    @Column(nullable = false)
    private Long eventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
