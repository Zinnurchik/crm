package uz.zinnur.cleaning_carpet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.zinnur.cleaning_carpet.model.Carpet;
import uz.zinnur.cleaning_carpet.service.CarpetService;

import java.util.List;

@RestController
@RequestMapping("/carpets")
public class CarpetController {

    private final CarpetService carpetService;

    public CarpetController(CarpetService carpetService) {
        this.carpetService = carpetService;
    }

    // Get carpets by Order ID
    @GetMapping("/get_by_order_id/{orderId}")
    public ResponseEntity<List<Carpet>> getCarpetsByOrderId(@PathVariable Long orderId) {
        List<Carpet> carpets = carpetService.getCarpetsByOrderId(orderId);
        return ResponseEntity.ok(carpets);
    }

    @PatchMapping("/{id}/dimensions")
    public ResponseEntity<String> updateHeightAndWidth(
            @PathVariable Long id,
            @RequestParam Double height,
            @RequestParam Double width) {
        carpetService.updateHeightAndWidth(id, height, width);
        return ResponseEntity.ok("Height and width updated successfully for Carpet ID " + id);
    }

    // Update status
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestParam String status) {
        carpetService.updateStatus(id, status);
        return ResponseEntity.ok("Status updated successfully for Carpet ID " + id);
    }

    // Delete carpet by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCarpetById(@PathVariable Long id) {
        carpetService.deleteCarpetById(id);
        return ResponseEntity.ok("Carpet with ID " + id + " deleted successfully.");
    }
}
