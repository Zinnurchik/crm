package uz.zinnur.cleaning_carpet.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.zinnur.cleaning_carpet.model.Order;
import uz.zinnur.cleaning_carpet.model.dto.*;
import uz.zinnur.cleaning_carpet.service.OrderService;

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


}
