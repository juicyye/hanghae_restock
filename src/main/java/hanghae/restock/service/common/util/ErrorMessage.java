package hanghae.restock.service.common.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorMessage {

    NOT_FOUND_PRODUCT("해당하는 상품을 찾을 수 없습니다.");

    private final String message;
}
