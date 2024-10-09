package uz.zinnur.cleaning_carpet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.zinnur.cleaning_carpet.model.Permission;
import uz.zinnur.cleaning_carpet.service.PermissionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    // Get all permissions
    @GetMapping
    public List<Permission> getAllPermissions() {
        return permissionService.getAllPermissions();
    }

    // Get permission by ID
    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        Optional<Permission> permission = permissionService.getPermissionById(id);
        return permission.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new permission
    @PostMapping
    public ResponseEntity<Permission> createPermission(@RequestBody Permission permission) {
        Permission createdPermission = permissionService.savePermission(permission);
        return ResponseEntity.ok(createdPermission);
    }

    // Update an existing permission
    @PutMapping("/{id}")
    public ResponseEntity<Permission> updatePermission(@PathVariable Long id, @RequestBody Permission permissionDetails) {
        Optional<Permission> existingPermission = permissionService.getPermissionById(id);
        if (existingPermission.isPresent()) {
            Permission permission = existingPermission.get();
            permission.setPermission(permissionDetails.getPermission());

            Permission updatedPermission = permissionService.savePermission(permission);
            return ResponseEntity.ok(updatedPermission);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a permission by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        Optional<Permission> existingPermission = permissionService.getPermissionById(id);
        if (existingPermission.isPresent()) {
            permissionService.deletePermission(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
