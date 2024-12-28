package uz.zinnur.cleaning_carpet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.zinnur.cleaning_carpet.model.Order;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT MAX(o.orderNumber) FROM Order o")
    Optional<Long> findMaxOrderNumber();
}
