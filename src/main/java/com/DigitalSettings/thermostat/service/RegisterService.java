package com.DigitalSettings.thermostat.service;

import com.DigitalSettings.thermostat.adapter.UnverifiedUserAdapter;
import com.DigitalSettings.thermostat.dto.RegisterResponseDTO;
import com.DigitalSettings.thermostat.dto.UserVerifyDTO;
import com.DigitalSettings.thermostat.entity.UnverifiedUser;
import com.DigitalSettings.thermostat.entity.User;
import com.DigitalSettings.thermostat.exception.*;
import com.DigitalSettings.thermostat.repository.UnverifiedUserRepo;
import com.DigitalSettings.thermostat.repository.UserRepo;
import com.DigitalSettings.thermostat.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Service
@AllArgsConstructor
public class RegisterService {

    private final PasswordEncoder passwordEncoder;
    private final MailSenderService mailSenderService;
    private final UnverifiedUserRepo unverifiedUserRepo;
    private final UserRepo userRepo;
    private final JwtUtil jwtUtil;

    public RegisterResponseDTO sendVerificationCode(final UserVerifyDTO userVerifyDTO) {
        User user = userRepo.findByUsername(userVerifyDTO.getUsername()).orElse(null);
        if(user != null) throw new UserAlreadyExistsException();
        user = userRepo.findByEmail(userVerifyDTO.getEmail()).orElse(null);
        if(user != null) throw new MailAlreadyInUseException(user.getEmail());
        String verificationCode = RandomStringUtils.randomAlphanumeric(4);
        unverifiedUserRepo.save(UnverifiedUserAdapter.toUnverifiedUser(userVerifyDTO, verificationCode));
        mailSenderService.sendMessage(userVerifyDTO.getEmail(), "Thermostat app verification code", verificationCode);
        return new RegisterResponseDTO(jwtUtil.createToken(userVerifyDTO.getUsername(), 7*60*1000));//valid for 7 minutes
    }

    public void getVerified(final String verificationCode) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String jwt = request.getHeader("jwt-token-for-verification");
        if (Objects.isNull(jwt)) {
            throw new HeaderNotFoundException("JWT header not found");
        }
        if(jwtUtil.isTokenExpired(jwt)) {
            throw new JwtExpiredException();
        }
        String username = jwtUtil.extractUsername(jwt);
        UnverifiedUser unverifiedUser = unverifiedUserRepo.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        if (unverifiedUser.getVerificationCode().equals(verificationCode)) {
            userRepo.save(UnverifiedUserAdapter.toUser(unverifiedUser, passwordEncoder));
        }
    }

}
