package hanghae.restock.domain.productusernotification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ActiveStatus {
    ACTIVATE("활성화"), DEACTIVATE("비활성화");

    private final String description;
}
