package com.DigitalSettings.thermostat.service;

import com.DigitalSettings.thermostat.dto.AuthRequestDTO;
import com.DigitalSettings.thermostat.dto.AuthResponseDTO;
import com.DigitalSettings.thermostat.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
        );
        return new AuthResponseDTO(jwtUtil.createToken(authRequestDTO.getUsername(), 1440*60*1000));// 30 days
    }
}
