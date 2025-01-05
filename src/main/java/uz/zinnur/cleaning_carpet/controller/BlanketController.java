package uz.zinnur.cleaning_carpet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.zinnur.cleaning_carpet.model.Blanket;
import uz.zinnur.cleaning_carpet.service.BlanketService;

import java.util.List;

@RestController
@RequestMapping("/blankets")
public class BlanketController {

    private final BlanketService blanketService;

    public BlanketController(BlanketService blanketService) {
        this.blanketService = blanketService;
    }

    @GetMapping("/get_by order_id/{orderId}")
    public ResponseEntity<List<Blanket>> getBlanketsByOrderId(@PathVariable Long orderId) {
        List<Blanket> blankets = blanketService.getBlanketsByOrderId(orderId);
        return ResponseEntity.ok(blankets);
    }

    @PatchMapping("/{id}/size")
    public ResponseEntity<String> updateSize(@PathVariable Long id, @RequestParam String size) {
        blanketService.updateSize(id, size);
        return ResponseEntity.ok("Size updated successfully");
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam String status) {
        blanketService.updateStatus(id, status);
        return ResponseEntity.ok("Status updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlanketById(@PathVariable Long id) {
        blanketService.deleteById(id);
        return ResponseEntity.ok("Blanket with ID " + id + " deleted successfully.");
    }
}
