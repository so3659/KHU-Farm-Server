package com.laicos.khufarm.domain.fruit.entity.category;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWholesaleRetailCategory is a Querydsl query type for WholesaleRetailCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWholesaleRetailCategory extends EntityPathBase<WholesaleRetailCategory> {

    private static final long serialVersionUID = -144514300L;

    public static final QWholesaleRetailCategory wholesaleRetailCategory = new QWholesaleRetailCategory("wholesaleRetailCategory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QWholesaleRetailCategory(String variable) {
        super(WholesaleRetailCategory.class, forVariable(variable));
    }

    public QWholesaleRetailCategory(Path<? extends WholesaleRetailCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWholesaleRetailCategory(PathMetadata metadata) {
        super(WholesaleRetailCategory.class, metadata);
    }

}

