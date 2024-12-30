package uz.zinnur.cleaning_carpet.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class OrderDriverIdsDto {
    @NotNull(message = "Driver IDs are required")
    @Size(min = 1, message = "There must be at least one driver.")
    private Set<UUID> driverIds = new HashSet<>();
    
    public OrderDriverIdsDto() {
        
    }
    public OrderDriverIdsDto(Set<UUID> driverIds) {
        this.driverIds = driverIds;
    }
    public Set<UUID> getDriverIds() {
        return driverIds;
    }
    public void setDriverIds(Set<UUID> driverIds) {
        this.driverIds = driverIds;
    }
}
