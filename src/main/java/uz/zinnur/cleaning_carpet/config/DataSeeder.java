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
import uz.zinnur.cleaning_carpet.service.PermissionService;
import uz.zinnur.cleaning_carpet.service.RoleService;

import java.util.List;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final PermissionService permissionService;

    public DataSeeder(EmployeeRepository employeeRepository,
                      PermissionRepository permissionRepository,
                      PasswordEncoder passwordEncoder, RoleRepository roleRepository, RoleService roleService, PermissionService permissionService) {
        this.employeeRepository = employeeRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @Override
    public void run(String... args) throws Exception {
       seedPermissionsAndRoles();
       seedEmployees();
    }

    private void seedPermissionsAndRoles() throws InterruptedException {
        // Create sample permissions
        Permission readEmployeePermission = new Permission();
        readEmployeePermission.setPermission("READ_EMPLOYEE");
        Permission writeEmployeePermission = new Permission();
        writeEmployeePermission.setPermission("CREATE_EMPLOYEE");
        Permission updateEmployeePermission = new Permission();
        updateEmployeePermission.setPermission("UPDATE_EMPLOYEE");
        Permission deleteEmployeePermission = new Permission();
        deleteEmployeePermission.setPermission("DELETE_EMPLOYEE");
        // Save permissions to the database
        permissionRepository.saveAll(List.of(readEmployeePermission, writeEmployeePermission, updateEmployeePermission, deleteEmployeePermission));
        Thread.sleep(1000);
    }

    private void seedEmployees() {
        Role roleAdmin = new Role("ADMIN", Set.of(
                permissionService.getPermissionByName("READ_EMPLOYEE"),
                permissionService.getPermissionByName("UPDATE_EMPLOYEE"),
                permissionService.getPermissionByName("DELETE_EMPLOYEE"),
                permissionService.getPermissionByName("CREATE_EMPLOYEE")
        ));
        Role roleManager = new Role("MANAGER", Set.of(
                permissionService.getPermissionByName("READ_EMPLOYEE"),
                permissionService.getPermissionByName("UPDATE_EMPLOYEE"),
                permissionService.getPermissionByName("CREATE_EMPLOYEE")
        ));
        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleManager);

        Employee employee = new Employee("Zinnurbek", "Ahrorov", "zinnurbek", passwordEncoder.encode("78987898alo"),
                "+998979265868", Set.of(
                roleAdmin, roleManager
        ));
        employeeRepository.save(employee);
    }
}

