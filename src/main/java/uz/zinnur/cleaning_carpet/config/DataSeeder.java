package uz.zinnur.cleaning_carpet.config;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.zinnur.cleaning_carpet.model.Employee;
import uz.zinnur.cleaning_carpet.model.Permission;
import uz.zinnur.cleaning_carpet.model.Role;
import uz.zinnur.cleaning_carpet.repository.EmployeeRepository;
import uz.zinnur.cleaning_carpet.repository.PermissionRepository;
import uz.zinnur.cleaning_carpet.repository.RoleRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(EmployeeRepository employeeRepository, RoleRepository roleRepository,
                      PermissionRepository permissionRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
       //seedPermissionsAndRoles();
       //seedEmployees();
    }

    private void seedPermissionsAndRoles() throws InterruptedException {
        // Create sample permissions
        Permission readEmployeePermission = new Permission();
        readEmployeePermission.setPermission("READ_EMPLOYEE");
        Permission writeEmployeePermission = new Permission();
        writeEmployeePermission.setPermission("WRITE_EMPLOYEE");
        Permission updateEmployeePermission = new Permission();
        updateEmployeePermission.setPermission("UPDATE_EMPLOYEE");
        Permission deleteEmployeePermission = new Permission();
        deleteEmployeePermission.setPermission("DELETE_EMPLOYEE");
        // Save permissions to the database
        permissionRepository.saveAll(List.of(readEmployeePermission, writeEmployeePermission, updateEmployeePermission, deleteEmployeePermission));

        Thread.sleep(1000);

        // Create roles and assign permissions
        Role adminRole = new Role();
        adminRole.setRole("ADMIN");
        adminRole.setPermissions(new HashSet<>(List.of(readEmployeePermission, writeEmployeePermission, updateEmployeePermission, deleteEmployeePermission)));

        Role operatorRole = new Role();
        operatorRole.setRole("OPERATOR");
        operatorRole.setPermissions(new HashSet<>(List.of(readEmployeePermission)));

        Role driverRole = new Role();
        driverRole.setRole("DRIVER");
        driverRole.setPermissions(new HashSet<>(List.of(readEmployeePermission)));

        Role washerRole = new Role();
        washerRole.setRole("WASHER");
        washerRole.setPermissions(new HashSet<>(List.of(readEmployeePermission)));

        Role managerRole = new Role();
        managerRole.setRole("MANAGER");
        managerRole.setPermissions(new HashSet<>(List.of(readEmployeePermission, writeEmployeePermission)));

        Role packagerRole = new Role();
        packagerRole.setRole("PACKAGER");
        packagerRole.setPermissions(new HashSet<>(List.of(readEmployeePermission)));
        // Save roles to the database
        roleRepository.saveAll(List.of(adminRole, operatorRole, driverRole, washerRole, managerRole, packagerRole));
        Thread.sleep(1000);
    }

    private void seedEmployees() {
        Random random = new Random();
        Faker faker = new Faker();
        List<Role> roles = roleRepository.findAll();
        for (int i = 1; i <= 50; i++) {
            String randomPassword = faker.internet().password(6, 16);
            String username = faker.name().username();
            System.out.println(username + ": " + randomPassword);
            int i1 = random.nextInt(100000000, 999999999);
            String phone = String.valueOf(i1);
            Employee employee = new Employee(
                    faker.name().firstName(),
                    faker.name().lastName(),
                    username,
                    passwordEncoder.encode(randomPassword),
                    "+998" + phone,
                    Set.of(roles.get(random.nextInt(roles.size())))
            );
            System.out.println(employee);
            employeeRepository.save(employee);
        }
    }
}

