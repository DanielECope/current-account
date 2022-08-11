package com.nttdata.currentaccount.repository;

import com.nttdata.currentaccount.models.CurrentAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CurrentAccountRepository extends ReactiveMongoRepository<CurrentAccount,String> {
    public Mono<CurrentAccount> findByCustomerDocument(String document);
    public Mono<CurrentAccount> findByAccountNumber(String accountNumber);
}
