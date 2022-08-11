package com.nttdata.currentaccount.service;

import com.nttdata.currentaccount.models.CurrentAccount;
import com.nttdata.currentaccount.models.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrentAccountService {
    Flux<CurrentAccount> findAll();
    Mono<CurrentAccount> findByCustomerId(String document);
    //public Mono<CurrentAccount> findByCustomerDni(String dni);
    Mono<CurrentAccount> findById(String id);
    Mono<CurrentAccount> save(CurrentAccount current);
    Mono<CurrentAccount> update(CurrentAccount current);
    Mono<Boolean> delete(String id);
    Mono<Customer> findCustomerById(String id);
    Mono<CurrentAccount> findByAccountNumber(String accountNumber);
}
