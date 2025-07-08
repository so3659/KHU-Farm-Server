package com.laicos.khufarm.domain.fruit.entity;

import com.laicos.khufarm.domain.fruit.entity.category.FruitCategory;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicInsert
@Table(name = "fruitRegularPrice")
public class FruitRegularPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer price;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="fruitCategory_id", nullable = false, unique = true)
    private FruitCategory fruitCategory;

}
