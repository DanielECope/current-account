package com.nttdata.currentaccount.service;

import com.nttdata.currentaccount.models.CurrentAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CurrentAccountService {
    Flux<CurrentAccount> findAll();
    public Mono<CurrentAccount> findByCustomerId(String dni);
    //public Mono<CurrentAccount> findByCustomerDni(String dni);
    public Mono<CurrentAccount> findById(String id);
    public Mono<CurrentAccount> save(CurrentAccount current);
    public Mono<CurrentAccount> update(CurrentAccount current);
    public Mono<Boolean> delete(String id);
}
