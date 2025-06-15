package com.laicos.khufarm.domain.businessInfo.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBusinessInfo is a Querydsl query type for BusinessInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBusinessInfo extends EntityPathBase<BusinessInfo> {

    private static final long serialVersionUID = 1543626485L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBusinessInfo businessInfo = new QBusinessInfo("businessInfo");

    public final StringPath businessId = createString("businessId");

    public final StringPath businessName = createString("businessName");

    public final StringPath businessRegistrationDate = createString("businessRegistrationDate");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.laicos.khufarm.domain.user.entity.QUser user;

    public QBusinessInfo(String variable) {
        this(BusinessInfo.class, forVariable(variable), INITS);
    }

    public QBusinessInfo(Path<? extends BusinessInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBusinessInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBusinessInfo(PathMetadata metadata, PathInits inits) {
        this(BusinessInfo.class, metadata, inits);
    }

    public QBusinessInfo(Class<? extends BusinessInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.laicos.khufarm.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

