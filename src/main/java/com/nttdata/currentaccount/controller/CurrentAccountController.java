package com.nttdata.currentaccount.controller;

import com.nttdata.currentaccount.models.CurrentAccount;
import com.nttdata.currentaccount.service.CurrentAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/CurrentAccount")
public class CurrentAccountController {
    @Autowired
    private CurrentAccountService service;

    @GetMapping("/list")
    public Flux<CurrentAccount> list(){
        log.info("Saving_account: controller list() method ");
        return service.findAll();
    }
    @GetMapping("/list/{id}")
    public Mono<CurrentAccount> findById(@PathVariable String id){
        log.info("Saving_account: controller findById() method ");
        return service.findById(id);
    }
    @PostMapping(path = "/create")
    public Mono<CurrentAccount> create(@RequestBody CurrentAccount obj)
    {
        log.info("Saving_account: controller create() method : {}",obj.toString());
        return service.save(obj);
    }
    @PutMapping(path = "/update")
    public Mono<CurrentAccount> update(@RequestBody CurrentAccount obj)
    {
        log.info("Saving_account: controller update() method : {}",obj.toString());
        return service.update(obj);
    }
    @DeleteMapping(path = "/delete/{id}")
    public Mono<String> delete(@PathVariable String id)
    {
        log.info("Saving_account: controller delete() method : {}",id);
        service.delete(id).map(obj->{
            if (obj){
                return Mono.just("Cuenta de ahorro eliminado");
            }else{
                return Mono.just("La Cuenta de ahorro no puedo ser eliinada");
            }
        });
        return Mono.just("La Cuenta de ahorro no puedo ser eliinada");
    }
}
