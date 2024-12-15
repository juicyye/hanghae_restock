package hanghae.restock.service.common.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorMessage {

    NOT_FOUND_PRODUCT("해당하는 상품을 찾을 수 없습니다."),
    NOT_FOUND_USER_NOTI_HISTORY("해당 재입고 회차의 유저 정보가 없습니다."),
    OUT_OF_STOCK("주문하신 상품의 재고가 부족합니다")
    ;

    private final String message;
}
