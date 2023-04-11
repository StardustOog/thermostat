package com.DigitalSettings.thermostat.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ThermostatDTO {
    @NotNull
    @Size(min = 5)
    private String deviceName;
    private float initTemperature;
    private float currentTemperature;
    @NotNull
    private Float thresholdTemperature;
    private boolean critical;
}
