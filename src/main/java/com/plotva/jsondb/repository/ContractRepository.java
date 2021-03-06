package com.plotva.jsondb.repository;

import com.plotva.jsondb.domain.Contract;
import org.springframework.data.repository.CrudRepository;

public interface ContractRepository extends CrudRepository<Contract, Long> {

    Contract findFirstById(String id);
}
