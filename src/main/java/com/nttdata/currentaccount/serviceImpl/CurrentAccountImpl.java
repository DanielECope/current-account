package com.nttdata.currentaccount.serviceImpl;

import com.nttdata.currentaccount.models.CurrentAccount;
import com.nttdata.currentaccount.repository.CurrentAccountRepository;
import com.nttdata.currentaccount.service.CurrentAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class CurrentAccountImpl implements CurrentAccountService {
    @Autowired
    private CurrentAccountRepository repository;

    @Override
    public Flux<CurrentAccount> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<CurrentAccount> findByCustomerId(String dni) {
        return repository.findById(dni);
    }

    @Override
    public Mono<CurrentAccount> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<CurrentAccount> save(CurrentAccount current) {
        log.info("CurrentAccount: implements save() method : {}",current.toString());
        Mono<CurrentAccount> obj=repository.findByCustomerDni(current.getCustomer().getDni());
        obj
                .map(c->{
                    Mono.just(Mono.error(new RuntimeException("the current number already existed")));
                    if (! (current.getCustomer().getDni().equals(c.getCustomer().getDni())) ) {
                        current.setCustomer(c.getCustomer());
                    }
                    return c;
                })
                .switchIfEmpty(obj=repository.save(current))
                .subscribe(c->{
                    if (c.getAccountNumber().equals(current.getAccountNumber())){
                        log.info("son iguales");
                        throw new RuntimeException("the current number already existed");
                        //Mono.just(Mono.error(new ModelNotFoundException("the account number is already registered")));
                    }
                });
        return obj;
    }

    @Override
    public Mono<CurrentAccount> update(CurrentAccount current) {
        log.info("CurrentAccount: implements update() method : {}",current.toString());
        Mono<CurrentAccount> obj=repository.findById(current.getAccountNumber());
        try {

            obj
                    .flatMap(c -> {
                        return repository.save(current);
                    })
                    .switchIfEmpty(Mono.error(() ->new RuntimeException("The customer does not exist")))
                    .subscribe();

        }catch (Exception e)
        {
            log.info("catch: "+e.getLocalizedMessage());
            throw new RuntimeException("The customer does not exist");
        }
        return obj;
    }

    @Override
    public Mono<Boolean> delete(String id) {
        return  repository.findById(id)
                .flatMap(obj -> repository.delete(obj)
                        .then(Mono.just(Boolean.TRUE))
                )
                .defaultIfEmpty(Boolean.FALSE);
    }
}
