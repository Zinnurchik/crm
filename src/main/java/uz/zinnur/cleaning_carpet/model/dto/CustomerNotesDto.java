package uz.zinnur.cleaning_carpet.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

public class CustomerNotesDto {
    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    @Column(length = 1000)
    private String notes;

    public CustomerNotesDto() {

    }
    public CustomerNotesDto(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
