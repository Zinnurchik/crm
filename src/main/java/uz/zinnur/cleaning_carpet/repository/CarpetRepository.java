package uz.zinnur.cleaning_carpet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.zinnur.cleaning_carpet.model.Carpet;

import java.util.List;

@Repository
public interface CarpetRepository extends JpaRepository<Carpet, Long> {

    // Find all carpets by order ID
    @Query("SELECT c FROM Carpet c WHERE c.order.id = :orderId")
    List<Carpet> findAllByOrderId(@Param("orderId") Long orderId);

    // Update both height and width
    @Modifying
    @Transactional
    @Query("UPDATE Carpet c SET c.height = :height, c.width = :width WHERE c.id = :id")
    void updateHeightAndWidth(@Param("id") Long id, @Param("height") Double height, @Param("width") Double width);

    // Update status
    @Modifying
    @Transactional
    @Query("UPDATE Carpet c SET c.status = :status WHERE c.id = :id")
    void updateStatus(@Param("id") Long id, @Param("status") String status);
}
