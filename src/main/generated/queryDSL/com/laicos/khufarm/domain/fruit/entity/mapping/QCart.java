package com.laicos.khufarm.domain.fruit.entity.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCart is a Querydsl query type for Cart
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCart extends EntityPathBase<Cart> {

    private static final long serialVersionUID = 1309717429L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCart cart = new QCart("cart");

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final com.laicos.khufarm.domain.fruit.entity.QFruit fruit;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.laicos.khufarm.domain.user.entity.QUser user;

    public QCart(String variable) {
        this(Cart.class, forVariable(variable), INITS);
    }

    public QCart(Path<? extends Cart> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCart(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCart(PathMetadata metadata, PathInits inits) {
        this(Cart.class, metadata, inits);
    }

    public QCart(Class<? extends Cart> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.fruit = inits.isInitialized("fruit") ? new com.laicos.khufarm.domain.fruit.entity.QFruit(forProperty("fruit"), inits.get("fruit")) : null;
        this.user = inits.isInitialized("user") ? new com.laicos.khufarm.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

