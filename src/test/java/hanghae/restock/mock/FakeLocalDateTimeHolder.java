package hanghae.restock.mock;

import hanghae.restock.service.port.LocalDateTimeHolder;
import java.time.LocalDateTime;

public class FakeLocalDateTimeHolder implements LocalDateTimeHolder {
    private LocalDateTime localDateTime;

    public FakeLocalDateTimeHolder(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public LocalDateTime getCurrentDate() {
        return localDateTime;
    }
}
