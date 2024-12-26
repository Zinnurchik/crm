package uz.zinnur.cleaning_carpet.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import uz.zinnur.cleaning_carpet.model.Customer;
import uz.zinnur.cleaning_carpet.model.dto.*;
import uz.zinnur.cleaning_carpet.repository.CustomerRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll(); // Fetch all customers
    }

    public Customer getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer with ID " + customerId + " does not exist."));
    }

    public Customer findCustomerByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("Customer with phone number " + phoneNumber + " does not exist."));
    }

    public Customer createCustomer(@NonNull Customer customer) {
        boolean isPhoneNumberTaken = customerRepository.existsCustomerByPhoneNumber(customer.getPhoneNumber());
        if (isPhoneNumberTaken) {
            throw new RuntimeException("Phone number already taken.");
        }
        return customerRepository.save(customer); // Save the customer to the database
    }

    public void deleteCustomer(UUID customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist.");
        }
        customerRepository.deleteById(customerId);
    }

    public void updateCustomerFullName(UUID id, @NonNull @Valid CustomerFullNameDto fullNameDto) {
        Customer customerById = getCustomerById(id);
        customerById.setName(fullNameDto.getName());
        customerById.setSurname(fullNameDto.getSurname());
        customerRepository.save(customerById);
    }

    public void updateCustomerPhoneNumber(UUID id, @NonNull @Valid CustomerPhoneNumberDto phoneNumberDto) {
        Customer customerById = getCustomerById(id);
        customerById.setPhoneNumber(phoneNumberDto.getPhoneNumber());
        customerById.setExtraPhoneNumber(phoneNumberDto.getExtraPhoneNumber());
        customerRepository.save(customerById);
    }


    public void updateCustomerType(UUID id, @NonNull @Valid CustomerTypeDto typeDto) {
        Customer customerById = getCustomerById(id);
        customerById.setType(typeDto.getType());
        customerRepository.save(customerById);
    }


    public void updateCustomerLanguage(UUID id,@NonNull @Valid CustomerLanguageDto languageDto) {
        Customer customerById = getCustomerById(id);
        customerById.setLanguage(languageDto.getLanguage());
        customerRepository.save(customerById);
    }

    public void updateCustomerNotes(UUID id,@NonNull @Valid CustomerNotesDto notesDto) {
        Customer customerById = getCustomerById(id);
        customerById.setNotes(notesDto.getNotes());
        customerRepository.save(customerById);
    }

    public void updateCustomerAddress(UUID id,@NonNull @Valid CustomerAddressDto addressDto) {
        Customer customerById = getCustomerById(id);
        customerById.setAddress(addressDto.getAddress());
        customerRepository.save(customerById);
    }
}
