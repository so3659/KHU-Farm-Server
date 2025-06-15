package com.laicos.khufarm.domain.inquiry.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInquiryReply is a Querydsl query type for InquiryReply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInquiryReply extends EntityPathBase<InquiryReply> {

    private static final long serialVersionUID = -150662645L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInquiryReply inquiryReply = new QInquiryReply("inquiryReply");

    public final com.laicos.khufarm.global.base.QBaseEntity _super = new com.laicos.khufarm.global.base.QBaseEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QInquiry inquiry;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QInquiryReply(String variable) {
        this(InquiryReply.class, forVariable(variable), INITS);
    }

    public QInquiryReply(Path<? extends InquiryReply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInquiryReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInquiryReply(PathMetadata metadata, PathInits inits) {
        this(InquiryReply.class, metadata, inits);
    }

    public QInquiryReply(Class<? extends InquiryReply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inquiry = inits.isInitialized("inquiry") ? new QInquiry(forProperty("inquiry"), inits.get("inquiry")) : null;
    }

}

