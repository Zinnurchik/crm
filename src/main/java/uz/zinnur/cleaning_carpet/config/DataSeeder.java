package uz.zinnur.cleaning_carpet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.zinnur.cleaning_carpet.model.Employee;
import uz.zinnur.cleaning_carpet.model.Permission;
import uz.zinnur.cleaning_carpet.model.Role;
import uz.zinnur.cleaning_carpet.repository.EmployeeRepository;
import uz.zinnur.cleaning_carpet.repository.PermissionRepository;
import uz.zinnur.cleaning_carpet.service.PermissionService;
import uz.zinnur.cleaning_carpet.service.RoleService;

import java.util.List;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final PermissionService permissionService;

    @Autowired
    public DataSeeder(EmployeeRepository employeeRepository,
                      PermissionRepository permissionRepository,
                      PasswordEncoder passwordEncoder, RoleService roleService, PermissionService permissionService) {
        this.employeeRepository = employeeRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.permissionService = permissionService;
    }

    @Override
    public void run(String... args) throws Exception {
//       seedPermissions();
       seedEmployeesAndRoles();
    }

    private void seedPermissions() throws InterruptedException {
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

    private void seedEmployeesAndRoles() throws InterruptedException {
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
        Role roleOperator = new Role("OPERATOR", null);
        Role roleDriver = new Role("DRIVER", null);
        Role roleWasher = new Role("WASHER", null);
        Role rolePackager = new Role("PACKAGER", null);
//        roleService.saveRole(roleAdmin);
//        roleService.saveRole(roleManager);
        roleService.saveRole(roleOperator);
        roleService.saveRole(roleDriver);
        roleService.saveRole(roleWasher);
        roleService.saveRole(rolePackager);

//        Thread.sleep(1000);
//
//        Employee employee = new Employee("Zinnurbek", "Ahrorov", "zinnurbek", passwordEncoder.encode("78987898alo"),
//                "+998979265868", roleAdmin);
//        employeeRepository.save(employee);
    }
}

