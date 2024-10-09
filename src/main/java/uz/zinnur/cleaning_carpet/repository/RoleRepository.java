package uz.zinnur.cleaning_carpet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.zinnur.cleaning_carpet.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
