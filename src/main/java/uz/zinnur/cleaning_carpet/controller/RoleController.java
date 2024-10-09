package uz.zinnur.cleaning_carpet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.zinnur.cleaning_carpet.model.Role;
import uz.zinnur.cleaning_carpet.service.RoleService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // Get all roles
    @GetMapping
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    // Get role by ID
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.getRoleById(id);
        return role.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Create a new role
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = roleService.saveRole(role);
        return ResponseEntity.ok(createdRole);
    }

    // Update an existing role
    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role roleDetails) {
        Optional<Role> existingRole = roleService.getRoleById(id);
        if (existingRole.isPresent()) {
            Role role = existingRole.get();
            role.setRole(roleDetails.getRole());
            role.setPermissions(roleDetails.getPermissions());

            Role updatedRole = roleService.saveRole(role);
            return ResponseEntity.ok(updatedRole);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a role by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        Optional<Role> existingRole = roleService.getRoleById(id);
        if (existingRole.isPresent()) {
            roleService.deleteRole(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


