package org.vsredshift.customer;

import javax.annotation.processing.Generated;
import javax.enterprise.context.ApplicationScoped;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-11T11:10:00+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.9 (Oracle Corporation)"
)
@ApplicationScoped
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerEntity toEntity(Customer domain) {
        if ( domain == null ) {
            return null;
        }

        CustomerEntity customerEntity = new CustomerEntity();

        return customerEntity;
    }

    @Override
    public Customer toDomain(CustomerEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Customer customer = new Customer();

        return customer;
    }
}
