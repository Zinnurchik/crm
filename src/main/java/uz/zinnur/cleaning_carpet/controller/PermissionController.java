package uz.zinnur.cleaning_carpet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.zinnur.cleaning_carpet.model.Permission;
import uz.zinnur.cleaning_carpet.service.PermissionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    // Fetch all permissions
    @GetMapping
    public ResponseEntity<List<Permission>> getAllPermissions() {
        List<Permission> permissions = permissionService.getAllPermissions();
        return ResponseEntity.ok(permissions);
    }

    // Fetch a permission by ID
    @GetMapping("/{id}")
    public ResponseEntity<Permission> getPermissionById(@PathVariable Long id) {
        Optional<Permission> permission = permissionService.getPermissionById(id);
        return permission.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Fetch a permission by name
    @GetMapping("/name/{name}")
    public ResponseEntity<Permission> getPermissionByName(@PathVariable String name) {
        try {
            Permission permission = permissionService.getPermissionByName(name);
            return ResponseEntity.ok(permission);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Create or update a permission
    @PostMapping
    public ResponseEntity<Permission> createOrUpdatePermission(@RequestBody Permission permission) {
        Permission savedPermission = permissionService.savePermission(permission);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPermission);
    }

    // Delete a permission by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        Optional<Permission> permission = permissionService.getPermissionById(id);
        if (permission.isPresent()) {
            permissionService.deletePermission(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
