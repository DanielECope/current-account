package com.nttdata.currentaccount.serviceImpl;

import com.nttdata.currentaccount.models.CurrentAccount;
import com.nttdata.currentaccount.models.Customer;
import com.nttdata.currentaccount.models.TypeCustomer;
import com.nttdata.currentaccount.repository.CurrentAccountRepository;
import com.nttdata.currentaccount.service.CurrentAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@Slf4j
public class CurrentAccountImpl implements CurrentAccountService {
    private final WebClient webClientCustomer=WebClient.create("http://localhost:8080/customer");
    @Autowired
    private CurrentAccountRepository repository;
    @Override
    public Flux<CurrentAccount> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<CurrentAccount> findByCustomerId(String document) {
        return repository.findById(document);
    }

    @Override
    public Mono<CurrentAccount> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<CurrentAccount> save(CurrentAccount current) {
        current.setRegistration_date(LocalDateTime.now());
        log.info("CurrentAccount: implements save() method : {}",current.toString());
        Mono<Customer> customer = findCustomerById(current.getCustomer().getDocument());
        customer.switchIfEmpty(Mono.error(new Exception("customer does not exist"))).subscribe();
        Customer client =new Customer();
        customer.map(c->{
            client.setName(c.getName());
            client.setLastName(c.getLastName());
            client.setId(c.getId());
            client.setTypeCustomer(c.getTypeCustomer());
            client.setDocument(c.getDocument());
            current.setCustomer(client);
            return c;
        }).subscribe();

        Mono<CurrentAccount> obj=repository.findByCustomerDocument(current.getCustomer().getDocument());
        obj
                .map(c->{
                    if (c.getCustomer().getTypeCustomer()==TypeCustomer.PERSONAL){
                        throw new RuntimeException("The customer already has an account. Customer has a "+TypeCustomer.PERSONAL+" account");
                        //El cliente ya tiene una cuenta. Cliente tiene una cuenta Peronsal
                    }
                    c.setCommission((float) 0.15);
                    return c;
                })
                .switchIfEmpty(obj=repository.save(current))
                .subscribe(c->{
                    log.info(c.toString());
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

    @Override
    public Mono<Customer> findCustomerById(String document) {
        log.info("findCustomerById -document: "+document);
        return webClientCustomer.get().uri("/findDcoument/{document}",document)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Customer.class);

                //.map(c->{log.info("Nombre de cliente: "+c.getName());return c;});
    }

    @Override
    public Mono<CurrentAccount> findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber);
    }
}
