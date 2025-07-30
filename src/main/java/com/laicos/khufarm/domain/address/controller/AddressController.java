package com.laicos.khufarm.domain.address.controller;

import com.laicos.khufarm.domain.address.dto.request.AddressCreateRequest;
import com.laicos.khufarm.domain.address.dto.response.AddressResponse;
import com.laicos.khufarm.domain.address.service.AddressCommandService;
import com.laicos.khufarm.domain.address.service.AddressQueryService;
import com.laicos.khufarm.domain.auth.security.CustomUserDetails;
import com.laicos.khufarm.global.common.base.BaseResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
@Validated
@Tag(name = "Address", description = "배송지 관련 API")
public class AddressController {

    private final AddressCommandService addressCommandService;
    private final AddressQueryService addressQueryService;

    @PostMapping("/create")
    public BaseResponse<Void> createAddress(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @Validated @RequestBody AddressCreateRequest addressCreateRequest
    ) {
        addressCommandService.createAddress(customUserDetails.getUser(), addressCreateRequest);

        return BaseResponse.onSuccess(null);
    }

    @GetMapping
    public BaseResponse<Slice<AddressResponse>> getAddressList(
            @RequestParam(required = false) Long cursorId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam(defaultValue="5") int size
    ) {
        Pageable pageable = PageRequest.of(0, size);
        Slice<AddressResponse> addressResponses = addressQueryService.getAddress(customUserDetails.getUser(), cursorId, pageable);

        return BaseResponse.onSuccess(addressResponses);
    }

    @PatchMapping("/update/{addressId}")
    public BaseResponse<Void> updateAddress(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long addressId,
            @Validated @RequestBody AddressCreateRequest addressCreateRequest
    ) {
        addressCommandService.updateAddress(customUserDetails.getUser(), addressId, addressCreateRequest);

        return BaseResponse.onSuccess(null);
    }

    @DeleteMapping("/delete/{addressId}")
    public BaseResponse<Void> deleteAddress(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long addressId
    ) {
        addressCommandService.deleteAddress(customUserDetails.getUser(), addressId);
        return BaseResponse.onSuccess(null);
    }
}
