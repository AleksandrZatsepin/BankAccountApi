package ru.aston.bankaccountapi.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {

    @Min(value = 0, message = "Minimal value 1 durdulik")
    private BigDecimal amount;

    @Pattern(regexp = "^\\d{4}$", message = "Pin code must be 4 digit format")
    private String pin;
}
