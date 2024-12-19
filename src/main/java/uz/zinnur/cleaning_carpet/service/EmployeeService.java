package uz.zinnur.cleaning_carpet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.zinnur.cleaning_carpet.model.Employee;
import uz.zinnur.cleaning_carpet.model.Permission;
import uz.zinnur.cleaning_carpet.model.Role;
import uz.zinnur.cleaning_carpet.repository.EmployeeRepository;
import java.util.*;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final PermissionService permissionService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, RoleService roleService, PermissionService permissionService) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    // Fetch all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Fetch employee by ID
    public Optional<Employee> getEmployeeById(UUID id) {
        return employeeRepository.findById(id);
    }

    // Create or update an employee
    public Employee saveEmployee(Employee employee) {

        // Check if the username already exists
        if (employeeRepository.existsByUsername(employee.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Check if the phone number already exists
        if (employeeRepository.existsByPhoneNumber(employee.getPhoneNumber())) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        // Encrypt the password before saving
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        try {
            Set<Permission> permissions = new HashSet<>();
            employee.getRole().getPermissions().forEach(permission -> {
                Permission permissionByName = permissionService.getPermissionByName(permission.getPermission());
                permissions.add(permissionByName);
            });
            employee.getRole().setPermissions(permissions);
            Role savedRole = roleService.saveRole(employee.getRole());
            employee.setRoles(savedRole);
            return employeeRepository.save(employee);
        } catch (DataIntegrityViolationException e) {
            // Handle cases where constraints are violated (e.g., unique constraints)
            throw new IllegalStateException("Failed to save employee due to data integrity violation", e);
        }
    }


    // Delete an employee by ID
    public void deleteEmployee(UUID id) {
        employeeRepository.deleteById(id);
    }

    public Optional<Employee> getEmployeeByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }
}

