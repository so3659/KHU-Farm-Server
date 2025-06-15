package com.laicos.khufarm.domain.fruit.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFruit is a Querydsl query type for Fruit
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFruit extends EntityPathBase<Fruit> {

    private static final long serialVersionUID = 111187519L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFruit fruit = new QFruit("fruit");

    public final com.laicos.khufarm.global.base.QBaseEntity _super = new com.laicos.khufarm.global.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath deliveryCompany = createString("deliveryCompany");

    public final NumberPath<Integer> deliveryDay = createNumber("deliveryDay", Integer.class);

    public final StringPath description = createString("description");

    public final com.laicos.khufarm.domain.fruit.entity.category.QFruitCategory fruitCategory;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Integer> ratingCount = createNumber("ratingCount", Integer.class);

    public final NumberPath<Integer> ratingSum = createNumber("ratingSum", Integer.class);

    public final com.laicos.khufarm.domain.user.entity.mapping.QSeller seller;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public final com.laicos.khufarm.domain.fruit.entity.category.QWholesaleRetailCategory wholesaleRetailCategory;

    public QFruit(String variable) {
        this(Fruit.class, forVariable(variable), INITS);
    }

    public QFruit(Path<? extends Fruit> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFruit(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFruit(PathMetadata metadata, PathInits inits) {
        this(Fruit.class, metadata, inits);
    }

    public QFruit(Class<? extends Fruit> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fruitCategory = inits.isInitialized("fruitCategory") ? new com.laicos.khufarm.domain.fruit.entity.category.QFruitCategory(forProperty("fruitCategory")) : null;
        this.seller = inits.isInitialized("seller") ? new com.laicos.khufarm.domain.user.entity.mapping.QSeller(forProperty("seller"), inits.get("seller")) : null;
        this.wholesaleRetailCategory = inits.isInitialized("wholesaleRetailCategory") ? new com.laicos.khufarm.domain.fruit.entity.category.QWholesaleRetailCategory(forProperty("wholesaleRetailCategory")) : null;
    }

}

