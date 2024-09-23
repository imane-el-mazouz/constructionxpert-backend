package com.user.service;

import com.user.model.Customer;
import com.user.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setFullName("imane");
        customer.setUsername("imane@gmail.com");
        customer.setPassword("1234");
        customer.setEmail("imane@gmail.com");

        when(customerRepository.findAll()).thenReturn(List.of(customer));
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        doNothing().when(customerRepository).deleteById(anyLong());
    }

    @Test
    void GetAllCustomers() {
        List<Customer> customers = userService.getAllCustomers();
        assertNotNull(customers);
        assertFalse(customers.isEmpty());
        assertEquals(1, customers.size());
        assertEquals("imane", customers.get(0).getFullName());    }

    @Test
    void GetCustomerById() {
        Customer foundCustomer = userService.getCustomerById(1L);
        assertNotNull(foundCustomer);
        assertEquals("imane", foundCustomer.getFullName());
    }

    @Test
    void CreateCustomer() {
        Customer newCustomer = new Customer();
        newCustomer.setFullName("imane");
        newCustomer.setUsername("imane@gmail.com");
        newCustomer.setPassword("1234");
        newCustomer.setEmail("imane@gmail.com");

        when(customerRepository.save(any(Customer.class))).thenReturn(newCustomer);

        Customer savedCustomer = userService.createCustomer(newCustomer);

        assertEquals("imane", savedCustomer.getFullName(), "Customer full name should match");
    }

    @Test
    void UpdateCustomer() {
        customer.setFullName("imana");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        Customer updatedCustomer = userService.updateCustomer(customer);
        assertNotNull(updatedCustomer);
        assertEquals("imana", updatedCustomer.getFullName());
    }

    @Test
    void DeleteCustomer() {
        userService.deleteCustomer(customer.getId());
        verify(customerRepository, times(1)).deleteById(customer.getId());
    }
}
