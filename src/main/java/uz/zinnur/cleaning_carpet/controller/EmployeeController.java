package uz.zinnur.cleaning_carpet.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.zinnur.cleaning_carpet.model.Employee;
import uz.zinnur.cleaning_carpet.model.Role;
import uz.zinnur.cleaning_carpet.model.dto.*;
import uz.zinnur.cleaning_carpet.model.projection.EmployeeProjection;
import uz.zinnur.cleaning_carpet.repository.EmployeeRepository;
import uz.zinnur.cleaning_carpet.repository.RoleRepository;
import uz.zinnur.cleaning_carpet.service.EmployeeService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }





    @GetMapping("/current_employee")
    public ResponseEntity<Employee> getCurrentEmployee() {
        // Assuming the SecurityContext contains user details with a username or ID
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Employee> currentEmployee = employeeService.getEmployeeByUsername(username);

        return currentEmployee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }






    // Get all employees
    @GetMapping
    public ResponseEntity<List<EmployeeProjection>> getAllEmployeesExceptCurrent() {
        // Get the current authenticated user's username
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        // Fetch all employees except the current user
        List<EmployeeProjection> employees = employeeService.getAllEmployeesExceptCurrent(currentUsername);
        return ResponseEntity.ok(employees);
    }







    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable UUID id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }







    // Create a new employee
    @PostMapping("/create_employee")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        employee.getRole().setPermissions(new HashSet<>());
        Employee createdEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(createdEmployee);
    }











    @PutMapping("/update_full_name/{id}")
    public ResponseEntity<Employee> updateName(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeeFullNameDto updateEmployeeDTO) {

        return employeeService.getEmployeeById(id)
                .map(employee -> {
                    employee.setName(updateEmployeeDTO.getName());
                    employee.setSurname(updateEmployeeDTO.getSurname());
                    employeeRepository.save(employee);
                    return ResponseEntity.ok(employee);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update_username/{id}")
    public ResponseEntity<String> updateUsername(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeeUsernameDto usernameDto) {

        // Check if the username already exists
        boolean isUsernameTaken = employeeRepository.existsByUsername(usernameDto.getUsername());
        if (isUsernameTaken) {
            return ResponseEntity.badRequest().body("Username is already taken.");
        }

        // Find the employee and update the username
        return employeeService.getEmployeeById(id)
                .map(employee -> {
                    employee.setUsername(usernameDto.getUsername());
                    employeeRepository.save(employee);
                    return ResponseEntity.ok("Username updated successfully.");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update_phone_number/{id}")
    public ResponseEntity<String> updatePhoneNumber(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeePhoneNumberDto phoneNumber) {

        // Check if the phone number is already in use
        boolean isPhoneNumberTaken = employeeRepository.existsByPhoneNumber(phoneNumber.getPhoneNumber());
        if (isPhoneNumberTaken) {
            return ResponseEntity.badRequest().body("Phone number is already taken.");
        }

        // Find the employee and update the phone number
        return employeeService.getEmployeeById(id)
                .map(employee -> {
                    employee.setPhoneNumber(phoneNumber.getPhoneNumber()); // Update the phone number
                    employeeRepository.save(employee);    // Save changes to the database
                    return ResponseEntity.ok("Phone number updated successfully.");
                })
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/update_password/{id}")
    public ResponseEntity<String> updatePassword(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeePasswordDto passwordDto) {

        // Find the employee by ID
        return employeeService.getEmployeeById(id)
                .map(employee -> {
                    // Verify the old password
                    if (!passwordEncoder.matches(passwordDto.getOldPassword(), employee.getPassword())) {
                        return ResponseEntity.badRequest().body("Old password is incorrect.");
                    }

                    // Hash the new password before saving
                    String hashedPassword = passwordEncoder.encode(passwordDto.getNewPassword());
                    employee.setPassword(hashedPassword);
                    employeeRepository.save(employee);

                    return ResponseEntity.ok("Password updated successfully.");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update_role/{id}")
    public ResponseEntity<String> updateRole(
            @PathVariable UUID id,
            @RequestBody EmployeeRoleDto role) {

        // Validate the role against allowed roles
        Role byRole = roleRepository.findByRole(role.getRole());
        if (byRole == null) {
            return ResponseEntity.badRequest().body("Role not found.");
        }

        // Find the employee and update the role
        return employeeService.getEmployeeById(id)
                .map(employee -> {
                    employee.setRole(byRole); // Update the role
                    employeeRepository.save(employee); // Save changes to the database
                    return ResponseEntity.ok("Role updated successfully.");
                })
                .orElse(ResponseEntity.notFound().build());
    }









    // Delete an employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        Optional<Employee> existingEmployee = employeeService.getEmployeeById(id);
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (existingEmployee.isPresent() && existingEmployee.get().getUsername().equals(currentUsername)) {
            throw(new RuntimeException("You are not allowed to delete yourself"));
        }
        if (existingEmployee.isPresent()) {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
