package com.laicos.khufarm.domain.wishList.dto.response;

import com.laicos.khufarm.domain.fruit.dto.response.FruitResponseWithWishListId;
import lombok.*;
import org.springframework.data.domain.Slice;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class WishListResponse {

    private Long userId;
    private Slice<FruitResponseWithWishListId> fruitWithWishList;
}
