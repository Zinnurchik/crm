package uz.zinnur.cleaning_carpet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.zinnur.cleaning_carpet.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT MAX(o.orderNumber) FROM Order o")
    Optional<Long> findMaxOrderNumber();

    @Query("SELECT o FROM Order o JOIN o.drivers d WHERE d.id = :driverId AND o.status = :status")
    List<Order> findAllByDriverIdAndStatus(@Param("driverId") UUID driverId, @Param("status") String status);
}
