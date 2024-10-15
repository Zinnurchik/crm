package uz.zinnur.cleaning_carpet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.zinnur.cleaning_carpet.model.Permission;
import uz.zinnur.cleaning_carpet.repository.PermissionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Autowired
    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    // Fetch all permissions
    public List<Permission> getAllPermissions() {
        return permissionRepository.findAll();
    }

    // Fetch permission by ID
    public Optional<Permission> getPermissionById(Long id) {
        return permissionRepository.findById(id);
    }

    // Create or update a permission
    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    // Delete a permission by ID
    public void deletePermission(Long id) {
        permissionRepository.deleteById(id);
    }
    public Permission getPermissionByName(String name) {
        return permissionRepository.findByPermission(name).orElseThrow(() -> new RuntimeException("Permission not found"));
    }
}
