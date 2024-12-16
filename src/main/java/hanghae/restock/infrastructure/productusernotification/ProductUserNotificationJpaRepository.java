package hanghae.restock.infrastructure.productusernotification;

import hanghae.restock.domain.productusernotification.ActiveStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductUserNotificationJpaRepository extends
        JpaRepository<ProductUserNotificationEntity, Long> {

    @Query("""
            select pun from ProductUserNotificationEntity pun
            where pun.productId = :productId
            and pun.activeStatus = :activeStatus
            and pun.id < :cursor
            order by pun.id desc
            limit :size
            """)
    List<ProductUserNotificationEntity> findActiveNotifiedUsersForProduct(@Param("productId") Long productId,
                                                                          @Param("activeStatus") ActiveStatus activeStatus,
                                                                          @Param("cursor") long cursor, @Param("size") int size);


}
