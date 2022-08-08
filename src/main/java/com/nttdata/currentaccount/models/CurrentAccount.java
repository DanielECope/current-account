package com.nttdata.currentaccount.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @NotNull
    private LocalDateTime registration_date;
}
