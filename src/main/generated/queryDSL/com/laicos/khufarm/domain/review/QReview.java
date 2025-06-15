package com.laicos.khufarm.domain.review;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = -457916206L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final com.laicos.khufarm.global.base.QBaseEntity _super = new com.laicos.khufarm.global.base.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.laicos.khufarm.domain.fruit.entity.QFruit fruit;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final BooleanPath isAnswered = createBoolean("isAnswered");

    public final BooleanPath isPointed = createBoolean("isPointed");

    public final com.laicos.khufarm.domain.order.entity.QOrder order;

    public final NumberPath<Integer> rating = createNumber("rating", Integer.class);

    public final com.laicos.khufarm.domain.user.entity.mapping.QSeller seller;

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.laicos.khufarm.domain.user.entity.QUser user;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fruit = inits.isInitialized("fruit") ? new com.laicos.khufarm.domain.fruit.entity.QFruit(forProperty("fruit"), inits.get("fruit")) : null;
        this.order = inits.isInitialized("order") ? new com.laicos.khufarm.domain.order.entity.QOrder(forProperty("order"), inits.get("order")) : null;
        this.seller = inits.isInitialized("seller") ? new com.laicos.khufarm.domain.user.entity.mapping.QSeller(forProperty("seller"), inits.get("seller")) : null;
        this.user = inits.isInitialized("user") ? new com.laicos.khufarm.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

