package uz.zinnur.cleaning_carpet.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorDetails {

    private LocalDateTime timestamp;
    private String message;
    private Map<String, String> details;

    // Constructor
    public ErrorDetails(LocalDateTime timestamp, String message, Map<String, String> details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    // Getters and setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ErrorDetails{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}