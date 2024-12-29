package uz.zinnur.cleaning_carpet.model.dto;

import jakarta.validation.constraints.Size;

public class OrderNotesDto {
    @Size(max = 500, message = "Notes must be at most 500 characters.")
    private String notes;
    public OrderNotesDto() {

    }
    public OrderNotesDto(String notes) {
        this.notes = notes;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
