package uz.zinnur.cleaning_carpet.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.zinnur.cleaning_carpet.model.Customer;
import uz.zinnur.cleaning_carpet.model.dto.*;
import uz.zinnur.cleaning_carpet.service.CustomerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers); // Return customers with HTTP 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable UUID id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/get_by_phone")
    public ResponseEntity<Customer> findCustomerByPhoneNumber(@Valid @RequestBody CustomerPhoneNumberDto phoneNumberDto) {
        Customer customer = customerService.findCustomerByPhoneNumber(phoneNumberDto.getPhoneNumber());
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer); // Return HTTP 201 Created
    }

    @PutMapping("/update_full_name/{id}")
    public ResponseEntity<String> updateCustomerFullName(@PathVariable UUID id, @Valid @RequestBody CustomerFullNameDto fullNameDto) {
        customerService.updateCustomerFullName(id, fullNameDto);
        return ResponseEntity.ok("Full name updated");
    }

    @PutMapping("/update_phone_number/{id}")
    public ResponseEntity<String> updateCustomerPhoneNumber(@PathVariable UUID id, @Valid @RequestBody CustomerPhoneNumberDto phoneNumberDto) {
        customerService.updateCustomerPhoneNumber(id, phoneNumberDto);
        return ResponseEntity.ok("Phone number updated");
    }

    @PutMapping("/update_type/{id}")
    public ResponseEntity<String> updateCustomerType(@PathVariable UUID id, @Valid @RequestBody CustomerTypeDto typeDto) {
        customerService.updateCustomerType(id, typeDto);
        return ResponseEntity.ok("Type updated");
    }
    @PutMapping("/update_language/{id}")
    public ResponseEntity<String> updateCustomerLanguage(@PathVariable UUID id, @Valid @RequestBody CustomerLanguageDto languageDto) {
        customerService.updateCustomerLanguage(id, languageDto);
        return ResponseEntity.ok("Language updated");
    }
    @PutMapping("/update_notes/{id}")
    public ResponseEntity<String> updateCustomerNotes(@PathVariable UUID id, @Valid @RequestBody CustomerNotesDto notesDto) {
        customerService.updateCustomerNotes(id, notesDto);
        return ResponseEntity.ok("Notes updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully.");
    }
}
