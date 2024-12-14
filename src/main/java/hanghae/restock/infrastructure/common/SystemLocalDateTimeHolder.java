package hanghae.restock.infrastructure.common;

import hanghae.restock.service.port.LocalDateTimeHolder;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class SystemLocalDateTimeHolder implements LocalDateTimeHolder {
    @Override
    public LocalDateTime getCurrentDate() {
        return LocalDateTime.now();
    }
}
