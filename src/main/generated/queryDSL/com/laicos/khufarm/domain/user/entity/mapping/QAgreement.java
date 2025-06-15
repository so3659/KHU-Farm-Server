package com.laicos.khufarm.domain.user.entity.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAgreement is a Querydsl query type for Agreement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAgreement extends EntityPathBase<Agreement> {

    private static final long serialVersionUID = -640321978L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAgreement agreement1 = new QAgreement("agreement1");

    public final com.laicos.khufarm.global.base.QBaseEntity _super = new com.laicos.khufarm.global.base.QBaseEntity(this);

    public final BooleanPath agreement = createBoolean("agreement");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QTermsConditions termsConditions;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.laicos.khufarm.domain.user.entity.QUser user;

    public QAgreement(String variable) {
        this(Agreement.class, forVariable(variable), INITS);
    }

    public QAgreement(Path<? extends Agreement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAgreement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAgreement(PathMetadata metadata, PathInits inits) {
        this(Agreement.class, metadata, inits);
    }

    public QAgreement(Class<? extends Agreement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.termsConditions = inits.isInitialized("termsConditions") ? new QTermsConditions(forProperty("termsConditions")) : null;
        this.user = inits.isInitialized("user") ? new com.laicos.khufarm.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

