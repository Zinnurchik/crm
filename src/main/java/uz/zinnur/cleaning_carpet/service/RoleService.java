package uz.zinnur.cleaning_carpet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.zinnur.cleaning_carpet.model.Role;
import uz.zinnur.cleaning_carpet.repository.RoleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Fetch all roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role findRoleByRole(String role) {
        return roleRepository.findByRole(role);
    }

    // Fetch role by ID
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    // Create or update a role
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    // Delete a role by ID
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}

