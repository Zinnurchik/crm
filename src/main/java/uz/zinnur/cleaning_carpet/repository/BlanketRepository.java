package uz.zinnur.cleaning_carpet.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.zinnur.cleaning_carpet.model.Blanket;

import java.util.List;

@Repository
public interface BlanketRepository extends JpaRepository<Blanket, Long> {

    @Query("SELECT b FROM Blanket b WHERE b.order.id = :orderId")
    List<Blanket> findAllByOrderId(@Param("orderId") Long orderId);

    @Modifying
    @Transactional
    @Query("UPDATE Blanket b SET b.size = :size WHERE b.id = :id")
    void updateSize(@Param("id") Long id, @Param("size") String size);

    @Modifying
    @Transactional
    @Query("UPDATE Blanket b SET b.status = :status WHERE b.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") String status);
}
