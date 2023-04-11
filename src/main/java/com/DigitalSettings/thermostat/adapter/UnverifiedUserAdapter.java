package com.DigitalSettings.thermostat.adapter;

import com.DigitalSettings.thermostat.entity.UnverifiedUser;
import com.DigitalSettings.thermostat.dto.UserVerifyDTO;
import com.DigitalSettings.thermostat.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class UnverifiedUserAdapter {

    public static UserVerifyDTO toUserVerifyDTO(UnverifiedUser unverifiedUser) {
        if (unverifiedUser == null) {
            return null;
        }

        return new UserVerifyDTO(
                unverifiedUser.getUsername(),
                unverifiedUser.getPassword(),
                unverifiedUser.getEmail()
        );
    }

    public static UnverifiedUser toUnverifiedUser(UserVerifyDTO userVerifyDTO, String verificationCode) {
        UnverifiedUser unverifiedUser = new UnverifiedUser();
        unverifiedUser.setUsername(userVerifyDTO.getUsername());
        unverifiedUser.setPassword(userVerifyDTO.getPassword());
        unverifiedUser.setEmail(userVerifyDTO.getEmail());
        unverifiedUser.setCreatedAt(LocalDateTime.now());
        unverifiedUser.setVerificationCode(verificationCode);
        return unverifiedUser;
    }

    public static User toUser(UnverifiedUser unverifiedUser, PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setId(unverifiedUser.getId());
        user.setEmail(unverifiedUser.getEmail());
        user.setUsername(unverifiedUser.getUsername());
        user.setPassword(passwordEncoder.encode(unverifiedUser.getPassword()));
        return user;
    }

}

