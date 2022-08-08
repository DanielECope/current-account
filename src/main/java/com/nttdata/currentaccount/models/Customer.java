package com.nttdata.currentaccount.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    String dni;
    String name;
    String lastName;
    TypeCustomer typeCustomer;
}
