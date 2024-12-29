package uz.zinnur.cleaning_carpet.service;

import jakarta.validation.Valid;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import uz.zinnur.cleaning_carpet.model.Customer;
import uz.zinnur.cleaning_carpet.model.Employee;
import uz.zinnur.cleaning_carpet.model.Order;
import uz.zinnur.cleaning_carpet.model.dto.*;
import uz.zinnur.cleaning_carpet.repository.CustomerRepository;
import uz.zinnur.cleaning_carpet.repository.EmployeeRepository;
import uz.zinnur.cleaning_carpet.repository.OrderRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

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

    public Order findById(UUID id) {
        return orderRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Order not found"));
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
        return orderRepository.save(order);
    }

    public Order updateDrivers(UUID id,Set<UUID> orderDriverIds) {
        List<Employee> allById = employeeRepository.findAllById(orderDriverIds);
        Order order = findById(id);
        Set<Employee> drivers = Set.copyOf(allById);
        order.setDrivers(drivers);
        return orderRepository.save(order);
    }

    public Order updateCarpetPrice(UUID id,Double carpetPrice) {
        Order order = findById(id);
        order.setCarpetPrice(carpetPrice);
        return orderRepository.save(order);
    }

    public Order updateSingleBlanketPrice(UUID id, Double singleBlanketPrice){
        Order order = findById(id);
        order.setSingleBlanketPrice(singleBlanketPrice);
        return orderRepository.save(order);
    }

    public Order updateDoubleBlanketPrice(UUID id, Double doubleBlanketPriceDto){
        Order order = findById(id);
        order.setDoubleBlanketPrice(doubleBlanketPriceDto);
        return orderRepository.save(order);
    }

    public Order updateNotes(UUID id, String notes) {
        Order order = findById(id);
        order.setNotes(notes);
        return orderRepository.save(order);
    }

    public List<Order> getAllNowOrders(UUID id) {
        return orderRepository.findAllByDriverIdAndStatus(id, "NOW");
    }
    public List<Order> getAllInProgressOrders(UUID id) {
        return orderRepository.findAllByDriverIdAndStatus(id, "IN_PROGRESS");
    }
    public List<Order> getAllCompletedOrders(UUID id) {
        return orderRepository.findAllByDriverIdAndStatus(id, "COMPLETED");
    }
    public List<Order> getAllCanceledOrders(UUID id) {
        return orderRepository.findAllByDriverIdAndStatus(id, "CANCELED");
    }


}
