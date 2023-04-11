package com.DigitalSettings.thermostat.adapter;


import com.DigitalSettings.thermostat.dto.ThermostatDTO;
import com.DigitalSettings.thermostat.entity.Thermostat;
import com.DigitalSettings.thermostat.entity.User;

public class ThermostatAdapter {

    public static ThermostatDTO toDTO(Thermostat thermostat) {
        return new ThermostatDTO(
                thermostat.getDeviceName(),
                thermostat.getInitTemperature(),
                thermostat.getCurrentTemperature(),
                thermostat.getThresholdTemperature(),
                thermostat.isCritical()
        );
    }

    public static Thermostat toEntity(ThermostatDTO thermostatDTO, User user) {
        return Thermostat.builder()
                .deviceName(thermostatDTO.getDeviceName())
                .initTemperature(thermostatDTO.getInitTemperature())
                .currentTemperature(thermostatDTO.getCurrentTemperature())
                .thresholdTemperature(thermostatDTO.getThresholdTemperature())
                .critical(thermostatDTO.isCritical())
                .user(user)
                .build();
    }
}
