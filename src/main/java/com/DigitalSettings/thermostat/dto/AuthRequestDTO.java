package com.DigitalSettings.thermostat.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthRequestDTO {
    @NotNull
    @Size(min = 5)
    private String username;
    @NotNull
    @Size(min = 8)
    private String password;
}
