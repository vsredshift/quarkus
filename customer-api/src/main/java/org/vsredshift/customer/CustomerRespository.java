package org.vsredshift.customer;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRespository implements PanacheRepositoryBase<CustomerEntity, Integer> {

}
