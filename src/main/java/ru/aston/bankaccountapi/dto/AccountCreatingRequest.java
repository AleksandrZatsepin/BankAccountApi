package ru.aston.bankaccountapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountCreatingRequest {
    @NotNull(message = "username must be not empty")
    private String name;

    @Pattern(regexp = "^\\d{4}$", message = "Pin code must be 4 digit format")
    private String pin;

}
