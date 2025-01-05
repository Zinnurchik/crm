package uz.zinnur.cleaning_carpet.service;

import org.springframework.stereotype.Service;
import uz.zinnur.cleaning_carpet.model.Blanket;
import uz.zinnur.cleaning_carpet.repository.BlanketRepository;

import java.util.List;

@Service
public class BlanketService {

    private final BlanketRepository blanketRepository;

    public BlanketService(BlanketRepository blanketRepository) {
        this.blanketRepository = blanketRepository;
    }

    public List<Blanket> getBlanketsByOrderId(Long orderId) {
        return blanketRepository.findAllByOrderId(orderId);
    }

    public void updateSize(Long id, String size) {
        blanketRepository.updateSize(id, size);
    }

    public void updateStatus(Long id, String status) {
        blanketRepository.updateStatus(id, status);
    }

    public void deleteById(Long id) {
        if (!blanketRepository.existsById(id)) {
            throw new IllegalArgumentException("Blanket with ID " + id + " does not exist.");
        }
        blanketRepository.deleteById(id);
    }
}

