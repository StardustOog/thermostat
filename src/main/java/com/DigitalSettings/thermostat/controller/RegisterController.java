package com.DigitalSettings.thermostat.controller;

import com.DigitalSettings.thermostat.dto.RegisterResponseDTO;
import com.DigitalSettings.thermostat.dto.UserVerifyDTO;
import com.DigitalSettings.thermostat.service.RegisterService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody final UserVerifyDTO userVerifyDTO) {
        return ResponseEntity.ok(registerService.sendVerificationCode(userVerifyDTO));
    }

    @PostMapping("/verify")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void verifyUser(@RequestParam("code") @NotNull final String code) {
        registerService.getVerified(code);
    }

}
