package uz.zinnur.cleaning_carpet.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.zinnur.cleaning_carpet.model.Blanket;
import uz.zinnur.cleaning_carpet.model.Order;
import uz.zinnur.cleaning_carpet.model.dto.*;
import uz.zinnur.cleaning_carpet.service.OrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderCreateDto order) {
        Order createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(createdOrder);
    }

    @GetMapping("/get_by_customer_id/{id}")
    public ResponseEntity<List<Order>> getOrderById(@PathVariable UUID id) {
        List<Order> orders = orderService.getByCustomerId(id);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/now/{id}")
    public ResponseEntity<List<Order>> getAllNowOrders(@PathVariable UUID id) {
        List<Order> orders = orderService.getAllNowOrders(id);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/in_progress/{id}")
    public ResponseEntity<List<Order>> getAllInProgressOrders(@PathVariable UUID id) {
        List<Order> orders = orderService.getAllInProgressOrders(id);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/completed/{id}")
    public ResponseEntity<List<Order>> getAllCompletedOrders(@PathVariable UUID id) {
        List<Order> orders = orderService.getAllCompletedOrders(id);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/canceled/{id}")
    public ResponseEntity<List<Order>> getAllCanceledOrders(@PathVariable UUID id) {
        List<Order> orders = orderService.getAllCanceledOrders(id);
        return ResponseEntity.ok(orders);
    }



    @PutMapping("/update_drivers/{id}")
    public ResponseEntity<Order> updateDrivers(@Valid @RequestBody OrderDriverIdsDto orderDriverIdsDto, @PathVariable UUID id) {
        Order order = orderService.updateDrivers(id, orderDriverIdsDto.getDriverIds());
        return ResponseEntity.ok(order);
    }

    @PutMapping("/update_carpet_price/{id}")
    public ResponseEntity<Order> updateCarpetPrice(@PathVariable UUID id,@Valid @RequestBody OrderCarpetPriceDto carpetPriceDto) {
        Order order = orderService.updateCarpetPrice(id, carpetPriceDto.getCarpetPrice());
        return ResponseEntity.ok(order);
    }
    @PutMapping("/update_single_blanket_price/{id}")
    public ResponseEntity<Order> updateSingleBlanketPrice(@PathVariable UUID id,@Valid @RequestBody OrderSingleBlanketPriceDto singleBlanketPriceDto) {
        Order order = orderService.updateSingleBlanketPrice(id, singleBlanketPriceDto.getSingleBlanketPrice());
        return ResponseEntity.ok(order);
    }
    @PutMapping("/update_double_blanket_price/{id}")
    public ResponseEntity<Order> updateDoubleBlanketPrice(@PathVariable UUID id,@Valid @RequestBody OrderDoubleBlanketPriceDto doubleBlanketPriceDto) {
        Order order = orderService.updateDoubleBlanketPrice(id, doubleBlanketPriceDto.getDoubleBlanketPrice());
        return ResponseEntity.ok(order);
    }

    @PutMapping("/update_notes/{id}")
    public ResponseEntity<Order> updateNotes(@PathVariable UUID id, @Valid @RequestBody OrderNotesDto notesDto) {
        Order order = orderService.updateNotes(id, notesDto.getNotes());
        return ResponseEntity.ok(order);
    }

    @PatchMapping("/{orderId}/blankets")
    public ResponseEntity<String> updateBlankets(
            @PathVariable UUID orderId,
            @RequestBody List<Blanket> blankets) {
        orderService.updateBlankets(orderId, blankets);
        return ResponseEntity.ok("Blankets updated successfully for Order ID " + orderId);
    }

    // Update givenPrice
    @PatchMapping("/{id}/given-price")
    public ResponseEntity<String> updateGivenPrice(
            @PathVariable Long id,
            @RequestParam Double givenPrice) {
        orderService.updateGivenPrice(id, givenPrice);
        return ResponseEntity.ok("Given price updated successfully for Order ID " + id);
    }

    // Update deadline PATCH /orders/1/deadline?deadline=2024-12-31T12:00:00
    @PatchMapping("/{id}/deadline")
    public ResponseEntity<String> updateDeadline(
            @PathVariable Long id,
            @RequestParam LocalDateTime deadline) {
        orderService.updateDeadline(id, deadline);
        return ResponseEntity.ok("Deadline updated successfully for Order ID " + id);
    }
}
