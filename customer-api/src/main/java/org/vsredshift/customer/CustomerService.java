package org.vsredshift.customer;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRespository customerRespository;
    private final CustomerMapper customerMapper;
    private final Logger logger;

    public List<Customer> findAll() {
        return customerRespository.findAll().stream()
                .map(customerMapper::toDomain)
                .collect(Collectors.toList());
    }

    public Optional<Customer> findById(Integer customerId) {
        return customerRespository.findByIdOptional(customerId).map(customerMapper::toDomain);
    }

    @Transactional
    public Customer save(Customer customer) {
        CustomerEntity entity = customerMapper.toEntity(customer);
        customerRespository.persist(entity);
        return customerMapper.toDomain(entity);
    }

    @Transactional
    public Customer update(Customer customer) {
        if (customer.getCustomerId() == null) {
            throw new ServiceException("Customer does not have a customer id");
        }
        Optional<CustomerEntity> optional = customerRespository.findByIdOptional(customer.getCustomerId());
        if (optional.isEmpty()) {
            throw new ServiceException("No customer found for customer id: " + customer.getCustomerId());
        }
        CustomerEntity entity = optional.get();
        entity.setFirstName(customer.getFirstName());
        entity.setMiddleName(customer.getMiddleName());
        entity.setLastName(customer.getLastName());
        entity.setSuffix(customer.getSuffix());
        entity.setEmail(customer.getEmail());
        entity.setPhone(customer.getPhone());
        logger.info("Updating customer...");
        customerRespository.persist(entity);
        return customerMapper.toDomain(entity);
    }
}
