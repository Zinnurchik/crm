package uz.zinnur.cleaning_carpet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.zinnur.cleaning_carpet.model.Employee;
import uz.zinnur.cleaning_carpet.model.projection.EmployeeProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query("SELECT new uz.zinnur.cleaning_carpet.model.projection.EmployeeProjection(u.id, u.name, u.surname, u.username, u.phoneNumber, u.role) FROM Employee u WHERE u.username <> :currentUsername")
    List<EmployeeProjection> findAllUsersExcept(@Param("currentUsername") String currentUsername);

    // Custom query method to find an employee by username
    Optional<Employee> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByPhoneNumber(String phoneNumber);
}
