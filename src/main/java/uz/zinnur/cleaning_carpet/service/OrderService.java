package uz.zinnur.cleaning_carpet.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import uz.zinnur.cleaning_carpet.model.Blanket;
import uz.zinnur.cleaning_carpet.model.Customer;
import uz.zinnur.cleaning_carpet.model.Employee;
import uz.zinnur.cleaning_carpet.model.Order;
import uz.zinnur.cleaning_carpet.model.dto.*;
import uz.zinnur.cleaning_carpet.repository.BlanketRepository;
import uz.zinnur.cleaning_carpet.repository.CustomerRepository;
import uz.zinnur.cleaning_carpet.repository.EmployeeRepository;
import uz.zinnur.cleaning_carpet.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final BlanketRepository blanketRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, BlanketRepository blanketRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.blanketRepository = blanketRepository;
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

    @Transactional
    public Order updateDrivers(UUID id, Set<UUID> orderDriverIds) {
        List<Employee> allById = employeeRepository.findAllById(orderDriverIds);

        if (allById.size() != orderDriverIds.size()) {
            throw new IllegalArgumentException("Some driver IDs are invalid or do not exist");
        }
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));

        Set<Employee> drivers = new HashSet<>(allById);
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

    public void updateBlankets(UUID orderId, List<Blanket> blankets) {
        // Validate that the order exists
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order with ID " + orderId + " does not exist."));

        // Save or update blankets in the database
        blanketRepository.saveAll(blankets);

        // Set the new blankets list and save the order
        order.setBlankets(blankets);
        orderRepository.save(order);
    }

    // Update givenPrice
    public void updateGivenPrice(Long id, Double givenPrice) {
        orderRepository.updateGivenPrice(id, givenPrice);
    }

    // Update deadline
    public void updateDeadline(Long id, LocalDateTime deadline) {
        orderRepository.updateDeadline(id, deadline);
    }

    public List<Order> getByCustomerId(UUID customerId) {
        return orderRepository.findAllByCustomerId(customerId);
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
