package uz.zinnur.cleaning_carpet.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uz.zinnur.cleaning_carpet.model.Employee;
import uz.zinnur.cleaning_carpet.model.projection.EmployeeProjection;
import uz.zinnur.cleaning_carpet.service.EmployeeService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
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

    // Update an existing employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable UUID id, @RequestBody Employee employeeDetails) {
        Optional<Employee> existingEmployee = employeeService.getEmployeeById(id);
        if (existingEmployee.isPresent()) {
            Employee employee = existingEmployee.get();
            employee.setName(employeeDetails.getName());
            employee.setSurname(employeeDetails.getSurname());
            employee.setUsername(employeeDetails.getUsername());
            employee.setPassword(employeeDetails.getPassword());
            employee.setRoles(employeeDetails.getRole());

            Employee updatedEmployee = employeeService.saveEmployee(employee);
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.notFound().build();
        }
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
