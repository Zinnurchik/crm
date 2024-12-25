package uz.zinnur.cleaning_carpet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.zinnur.cleaning_carpet.model.Customer;
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

    public Customer createCustomer(Customer customer) {
        // Additional validation or preprocessing logic can go here
        return customerRepository.save(customer); // Save the customer to the database
    }

    public void deleteCustomer(UUID customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist.");
        }
        customerRepository.deleteById(customerId);
    }
}
