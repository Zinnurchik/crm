package uz.zinnur.cleaning_carpet.service;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import uz.zinnur.cleaning_carpet.model.Customer;
import uz.zinnur.cleaning_carpet.model.Employee;
import uz.zinnur.cleaning_carpet.model.Order;
import uz.zinnur.cleaning_carpet.model.dto.OrderCreateDto;
import uz.zinnur.cleaning_carpet.repository.CustomerRepository;
import uz.zinnur.cleaning_carpet.repository.EmployeeRepository;
import uz.zinnur.cleaning_carpet.repository.OrderRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    public Order createOrder(@NonNull OrderCreateDto orderDTO) {
        Order order = new Order();
        Long maxOrderNumber = orderRepository.findMaxOrderNumber().orElse(0L);
        order.setOrderNumber(maxOrderNumber + 1);

        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));
        order.setCustomer(customer);

        // Map Drivers
        List<Employee> allById = employeeRepository.findAllById(orderDTO.getDriverIds());
        Set<Employee> drivers = Set.copyOf(allById);
        order.setDrivers(drivers);

        // Map Prices and Notes
        order.setCarpetPrice(orderDTO.getCarpetPrice());
        order.setSingleBlanketPrice(orderDTO.getSingleBlanketPrice());
        order.setDoubleBlanketPrice(orderDTO.getDoubleBlanketPrice());
        order.setNotes(orderDTO.getNotes());
        order.setStatus("NEW");
        Order save = orderRepository.save(order);
        return save;
    }
}
