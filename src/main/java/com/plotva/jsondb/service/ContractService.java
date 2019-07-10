package com.plotva.jsondb.service;

import com.plotva.jsondb.domain.Contract;
import com.plotva.jsondb.repository.ContractRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractService {

    private ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Iterable<Contract> list() {
        return contractRepository.findAll();
    }

    public Contract save(Contract contract) {
        return contractRepository.save(contract);
    }

    public void save(List<Contract> contracts) {
        contractRepository.saveAll(contracts);
    }

}
