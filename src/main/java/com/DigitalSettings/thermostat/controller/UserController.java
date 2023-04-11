package com.DigitalSettings.thermostat.controller;

import com.DigitalSettings.thermostat.dto.ThermostatDTO;
import com.DigitalSettings.thermostat.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/thermostats")
    public ResponseEntity<List> getMyThermostats() {
        return ResponseEntity.ok(userService.getThermostats());
    }

    @PostMapping("/thermostats/add-thermostat")
    @ResponseStatus(HttpStatus.CREATED)
    public void addThermostat(@RequestBody @Valid ThermostatDTO thermostatDTO) {
        userService.addThermostat(thermostatDTO);
    }

    @PutMapping("/thermostats/set-threshold")
    //OK status is default but it's more visible this way
    @ResponseStatus(HttpStatus.OK)
    public void setThermostatThreshold(@RequestParam("thermostatname") @NotNull String thermostatName,
                                       @RequestParam("thresholdtemperature") @NotNull float temp) {
        userService.setThermostatThreshold(thermostatName, temp);
    }

    @GetMapping("/thermostats/critical")
    public ResponseEntity<List> getCriticalThermostats() {
        return ResponseEntity.ok(userService.getCriticalThermostats());
    }

}
