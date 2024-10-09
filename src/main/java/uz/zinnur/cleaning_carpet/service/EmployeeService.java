package uz.zinnur.cleaning_carpet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.zinnur.cleaning_carpet.model.Employee;
import uz.zinnur.cleaning_carpet.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // Fetch all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Fetch employee by ID
    public Optional<Employee> getEmployeeById(UUID id) {
        return employeeRepository.findById(id);
    }

    // Fetch employee by username
    public Optional<Employee> getEmployeeByUsername(String username) {
        return employeeRepository.findByUsername(username);
    }

    // Create or update an employee
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    // Delete an employee by ID
    public void deleteEmployee(UUID id) {
        employeeRepository.deleteById(id);
    }
}

