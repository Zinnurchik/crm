package uz.zinnur.cleaning_carpet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.zinnur.cleaning_carpet.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}