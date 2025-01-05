package uz.zinnur.cleaning_carpet.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.zinnur.cleaning_carpet.model.Blanket;
import uz.zinnur.cleaning_carpet.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT MAX(o.orderNumber) FROM Order o")
    Optional<Long> findMaxOrderNumber();

    @Query("SELECT o FROM Order o JOIN o.drivers d WHERE d.id = :driverId AND o.status = :status")
    List<Order> findAllByDriverIdAndStatus(@Param("driverId") UUID driverId, @Param("status") String status);

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.blankets = :blankets WHERE o.id = :orderId")
    void updateBlankets(@Param("orderId") Long orderId, @Param("blankets") List<Blanket> blankets);

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.givenPrice = :givenPrice WHERE o.id = :id")
    void updateGivenPrice(@Param("id") Long id, @Param("givenPrice") Double givenPrice);

    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.deadline = :deadline WHERE o.id = :id")
    void updateDeadline(@Param("id") Long id, @Param("deadline") LocalDateTime deadline);

    List<Order> findAllByCustomerId(UUID customerId);
}

