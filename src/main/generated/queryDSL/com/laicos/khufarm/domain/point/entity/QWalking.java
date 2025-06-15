package com.laicos.khufarm.domain.point.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWalking is a Querydsl query type for Walking
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWalking extends EntityPathBase<Walking> {

    private static final long serialVersionUID = 1181450216L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWalking walking = new QWalking("walking");

    public final NumberPath<Integer> getSteps = createNumber("getSteps", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> steps = createNumber("steps", Integer.class);

    public final com.laicos.khufarm.domain.user.entity.QUser user;

    public QWalking(String variable) {
        this(Walking.class, forVariable(variable), INITS);
    }

    public QWalking(Path<? extends Walking> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWalking(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWalking(PathMetadata metadata, PathInits inits) {
        this(Walking.class, metadata, inits);
    }

    public QWalking(Class<? extends Walking> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.laicos.khufarm.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

