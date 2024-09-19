package com.user.service;

import com.user.model.Customer;
import com.user.model.User;
import com.user.repository.CustomerRepository;
import com.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerRepository customerRepository;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setFullName("imane");
        customer.setUsername("imane@gmail.com");
        customer.setPassword("1234");
        customer.setEmail("imane@gmail.com");
        customerRepository.save(customer);
    }
    @Test
    void GetAllCustomers() {
        List<Customer> customers = userService.getAllCustomers();
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        assertEquals(1, customers.size());
        assertEquals("imane", customers.getFirst().getFullName());
    }
    @Test
    void GetCustomerById() {
        Customer foundCustomer = userService.getCustomerById(customer.getId());
        assertNotNull(foundCustomer);
        assertEquals("Jane Doe", foundCustomer.getFullName());
    }

    @Test
    void testCreateCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setFullName("imane");
        newCustomer.setUsername("imane@gmail.com");
        newCustomer.setPassword("1234");
        newCustomer.setEmail("imane@gmail.com");

        Customer savedCustomer = userService.createCustomer(newCustomer);

        assertNotNull(savedCustomer);
        assertNotNull(savedCustomer.getId());
        assertEquals("Alice Wonderland", savedCustomer.getFullName());
    }

    @Test
    void testUpdateCustomer() {
        customer.setFullName("Jane Updated");
        Customer updatedCustomer = userService.updateCustomer(customer);

        assertNotNull(updatedCustomer);
        assertEquals("Jane Updated", updatedCustomer.getFullName());
    }

    @Test
    void testDeleteCustomer() {
        userService.deleteCustomer(customer.getId());
        Customer deletedCustomer = userService.getCustomerById(customer.getId());
        assertNull(deletedCustomer);
    }

}
