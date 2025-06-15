package com.laicos.khufarm.domain.user.entity.mapping;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTermsConditions is a Querydsl query type for TermsConditions
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTermsConditions extends EntityPathBase<TermsConditions> {

    private static final long serialVersionUID = 1743939291L;

    public static final QTermsConditions termsConditions = new QTermsConditions("termsConditions");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final EnumPath<com.laicos.khufarm.domain.user.enums.TermsEssential> termsEssential = createEnum("termsEssential", com.laicos.khufarm.domain.user.enums.TermsEssential.class);

    public QTermsConditions(String variable) {
        super(TermsConditions.class, forVariable(variable));
    }

    public QTermsConditions(Path<? extends TermsConditions> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTermsConditions(PathMetadata metadata) {
        super(TermsConditions.class, metadata);
    }

}

