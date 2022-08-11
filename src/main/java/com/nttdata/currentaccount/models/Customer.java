package com.nttdata.currentaccount.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String lastName;
    @Valid
    private TypeCustomer typeCustomer;
    @NotNull
    private String document;
}
