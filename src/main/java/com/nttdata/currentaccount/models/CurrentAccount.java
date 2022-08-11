package com.nttdata.currentaccount.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "CurrentAccount")
public class CurrentAccount {
    @Id
    @NotNull
    private String accountNumber;
    @NotNull
    private Customer customer;
    @NotNull
    private float commission;
    @NotNull
    private int movement_limit;
    private float amountAvailable;
    @NotNull
    private LocalDateTime registration_date;
    private List<String> holders;
    private List<String> signers;
}
