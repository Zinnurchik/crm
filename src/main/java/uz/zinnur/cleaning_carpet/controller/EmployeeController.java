package uz.zinnur.cleaning_carpet.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uz.zinnur.cleaning_carpet.config.JwtService;
import uz.zinnur.cleaning_carpet.model.Employee;
import uz.zinnur.cleaning_carpet.model.Role;
import uz.zinnur.cleaning_carpet.model.dao.AuthenticationResponse;
import uz.zinnur.cleaning_carpet.model.dto.*;
import uz.zinnur.cleaning_carpet.model.projection.EmployeeProjection;
import uz.zinnur.cleaning_carpet.repository.EmployeeRepository;
import uz.zinnur.cleaning_carpet.service.EmployeeService;
import uz.zinnur.cleaning_carpet.service.RoleService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final JwtService jwtService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, RoleService roleService, JwtService jwtService) {
        this.employeeService = employeeService;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeProjection>> getAllEmployeesExceptCurrent() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        List<EmployeeProjection> employees = employeeService.getAllEmployeesExceptCurrent(currentUsername);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/current_employee")
    public ResponseEntity<Employee> getCurrentEmployee() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Employee> currentEmployee = employeeService.getEmployeeByUsername(username);
        return ResponseEntity.ok(currentEmployee.orElseThrow(() -> new RuntimeException("Current employee not found")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable UUID id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee.orElseThrow(()->new RuntimeException("Employee not found")));
    }

    @GetMapping("/drivers")
    public ResponseEntity<List<Employee>> getAllDrivers() {
        return ResponseEntity.ok(employeeService.getAllDrivers());
    }

    @PostMapping("/create_employee")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @PutMapping("/update_full_name/{id}")
    public ResponseEntity<Employee> updateName(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeeFullNameDto updateEmployeeDTO) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            employee.get().setName(updateEmployeeDTO.getName());
            employee.get().setSurname(updateEmployeeDTO.getSurname());
            Employee save = employeeRepository.save(employee.get());
            return ResponseEntity.ok(save);
        }else {
            throw new UsernameNotFoundException("Employee not found.");
        }
    }

    @PutMapping("/update_username/{id}")
    public ResponseEntity<AuthenticationResponse> updateUsername(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeeUsernameDto usernameDto) {

        boolean isUsernameTaken = employeeRepository.existsByUsername(usernameDto.getUsername());
        if (isUsernameTaken) {
            throw new RuntimeException("Username already taken.");
        }
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            employee.get().setUsername(usernameDto.getUsername());
            Employee save = employeeRepository.save(employee.get());
            return ResponseEntity.ok(new AuthenticationResponse(jwtService.generateToken(save)));
        }else {
            throw new RuntimeException("Employee not found.");
        }
    }

    @PutMapping("/update_phone_number/{id}")
    public ResponseEntity<Employee> updatePhoneNumber(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeePhoneNumberDto phoneNumber) {

        boolean isPhoneNumberTaken = employeeRepository.existsByPhoneNumber(phoneNumber.getPhoneNumber());
        if (isPhoneNumberTaken) {
            throw new RuntimeException("Phone number already taken.");
        }
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            employee.get().setPhoneNumber(phoneNumber.getPhoneNumber());
            Employee save = employeeRepository.save(employee.get());
            return ResponseEntity.ok(save);
        }else {
            throw new RuntimeException("Phone number not found.");
        }
    }

    @PutMapping("/update_password/{id}")
    public ResponseEntity<Employee> updatePassword(
            @PathVariable UUID id,
            @Valid @RequestBody EmployeePasswordDto passwordDto) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            if (!passwordEncoder.matches(passwordDto.getOldPassword(), employee.get().getPassword())) {
                throw new RuntimeException("Old password doesn't match.");
            }
            String hashedPassword = passwordEncoder.encode(passwordDto.getNewPassword());
            employee.get().setPassword(hashedPassword);
            Employee save = employeeRepository.save(employee.get());
            return ResponseEntity.ok(save);
        }else {
            throw new RuntimeException("Employee not found.");
        }
    }

    @PutMapping("/update_role/{id}")
    public ResponseEntity<Employee> updateRole(
            @PathVariable UUID id,
            @RequestBody EmployeeRoleDto role) {

        Role byRole = roleService.findRoleByRole(role.getRole());
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            employee.get().setRole(byRole);
            Employee save = employeeRepository.save(employee.get());
            return ResponseEntity.ok(save);
        }else {
            throw new RuntimeException("Employee not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable UUID id) {
        Optional<Employee> existingEmployee = employeeService.getEmployeeById(id);
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if (existingEmployee.isPresent() && existingEmployee.get().getUsername().equals(currentUsername)) {
            throw new RuntimeException("You are not allowed to delete yourself");
        }
        if (existingEmployee.isPresent()) {
            employeeService.deleteEmployee(id);
            return ResponseEntity.ok("Employee deleted successfully.");
        } else {
            throw new RuntimeException("Employee not found.");
        }
    }
}
