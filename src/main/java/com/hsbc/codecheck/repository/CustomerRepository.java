package com.hsbc.codecheck.repository;

import com.hsbc.codecheck.entity.CustomerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerDocument, String> {

    @Query(value="{_id : ?0,'accounts.accountNo': ?1}", fields = "{'accounts.$': 1}")
    CustomerDocument findByIdAndAccountNo(final String customerId, final long accountNo);
}
