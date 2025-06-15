package com.laicos.khufarm.domain.fruit.entity.category;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QFruitCategory is a Querydsl query type for FruitCategory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFruitCategory extends EntityPathBase<FruitCategory> {

    private static final long serialVersionUID = -911326921L;

    public static final QFruitCategory fruitCategory = new QFruitCategory("fruitCategory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QFruitCategory(String variable) {
        super(FruitCategory.class, forVariable(variable));
    }

    public QFruitCategory(Path<? extends FruitCategory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFruitCategory(PathMetadata metadata) {
        super(FruitCategory.class, metadata);
    }

}

