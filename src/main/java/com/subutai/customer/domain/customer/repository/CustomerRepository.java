package com.subutai.customer.domain.customer.repository;

import com.subutai.customer.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 회원 관련 레파지토리
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findCustomerByEmail(String email);
}
