package uz.zinnur.cleaning_carpet.service;

import org.springframework.stereotype.Service;
import uz.zinnur.cleaning_carpet.model.Carpet;
import uz.zinnur.cleaning_carpet.repository.CarpetRepository;

import java.util.List;

@Service
public class CarpetService {

    private final CarpetRepository carpetRepository;

    public CarpetService(CarpetRepository carpetRepository) {
        this.carpetRepository = carpetRepository;
    }

    // Get all carpets by Order ID
    public List<Carpet> getCarpetsByOrderId(Long orderId) {
        return carpetRepository.findAllByOrderId(orderId);
    }

    // Update both height and width
    public void updateHeightAndWidth(Long id, Double height, Double width) {
        carpetRepository.updateHeightAndWidth(id, height, width);
    }

    // Update status
    public void updateStatus(Long id, String status) {
        carpetRepository.updateStatus(id, status);
    }

    // Delete carpet by ID
    public void deleteCarpetById(Long id) {
        if (!carpetRepository.existsById(id)) {
            throw new IllegalArgumentException("Carpet with ID " + id + " does not exist.");
        }
        carpetRepository.deleteById(id);
    }
}
