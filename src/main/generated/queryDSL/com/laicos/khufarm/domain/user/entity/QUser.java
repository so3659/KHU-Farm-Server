package com.laicos.khufarm.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1439832849L;

    public static final QUser user = new QUser("user");

    public final com.laicos.khufarm.global.base.QBaseEntity _super = new com.laicos.khufarm.global.base.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final NumberPath<Integer> totalDonation = createNumber("totalDonation", Integer.class);

    public final NumberPath<Integer> totalPoint = createNumber("totalPoint", Integer.class);

    public final NumberPath<Integer> totalPrice = createNumber("totalPrice", Integer.class);

    public final NumberPath<Integer> totalWeight = createNumber("totalWeight", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath userId = createString("userId");

    public final EnumPath<com.laicos.khufarm.domain.user.enums.UserStatus> userStatus = createEnum("userStatus", com.laicos.khufarm.domain.user.enums.UserStatus.class);

    public final EnumPath<com.laicos.khufarm.domain.user.enums.UserType> userType = createEnum("userType", com.laicos.khufarm.domain.user.enums.UserType.class);

    public final NumberPath<Double> version = createNumber("version", Double.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

