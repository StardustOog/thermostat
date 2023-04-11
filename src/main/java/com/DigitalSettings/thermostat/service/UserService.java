package com.DigitalSettings.thermostat.service;

import com.DigitalSettings.thermostat.adapter.ThermostatAdapter;
import com.DigitalSettings.thermostat.dto.ThermostatDTO;
import com.DigitalSettings.thermostat.entity.Thermostat;
import com.DigitalSettings.thermostat.entity.User;
import com.DigitalSettings.thermostat.exception.UnreacheableThermostatException;
import com.DigitalSettings.thermostat.repository.ThermostatRepository;
import com.DigitalSettings.thermostat.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private  UserRepo userRepo;
    @Autowired
    private  ThermostatRepository thermostatRepository;
    @Autowired
    private  MailSenderService mailSenderService;

    @Value("${mintemperature}")
    private float MIN_TEMPERATURE;
    @Value("${maxtemperature}")
    private float MAX_TEMPERATURE;

    public List<ThermostatDTO> getThermostats() {
        return getUser().getThermostats()
                .stream()
                .map(thermostat -> ThermostatAdapter.toDTO(thermostat))
                .collect(Collectors.toList());
    }

    public void addThermostat(ThermostatDTO thermostatDTO) {
        Thermostat thermostat = ThermostatAdapter.toEntity(thermostatDTO, getUser());
        thermostat.setCritical(false);
        Random random = new Random();
        final float temp = random.nextFloat()*(MAX_TEMPERATURE-MIN_TEMPERATURE) + MIN_TEMPERATURE;
        thermostat.setInitTemperature(temp);
        thermostat.setCurrentTemperature(temp);
        if (getUser().getThermostats().contains(thermostat)) {
            //I know that I should create new exception type with BAD_REQUEST here but...
            throw new UnreacheableThermostatException("You already have thermostat with that name");
        }
        thermostatRepository.save(thermostat);
    }

    public void setThermostatThreshold(String thermostatName, float thermostatThreshold) {
        final User user = getUser();
        final Thermostat thermostat = thermostatRepository
                .findByDeviceName(thermostatName).orElseThrow(() ->
                        new UnreacheableThermostatException(
                                "Thermostat with that name doesn't exist")
                );
        if (!user.getThermostats().contains(thermostat)) {
            throw new UnreacheableThermostatException("You don't own thermostat with that name");
        }
        thermostat.setThresholdTemperature(thermostatThreshold);
        if(thermostat.getCurrentTemperature() <= thermostat.getInitTemperature() - thermostatThreshold
                || thermostat.getCurrentTemperature() >= thermostat.getInitTemperature() + thermostatThreshold) {
            thermostat.setCritical(true);
            mailSenderService.sendMessage(user.getEmail(),
                    "Critical temperature", "Critical temperature for device: " + thermostatName);
        } else {
            thermostat.setCritical(false);
        }
        thermostatRepository.save(thermostat);
    }

    public List<ThermostatDTO> getCriticalThermostats() {
        return getThermostats().stream().filter(thermostat -> thermostat.isCritical())
                .collect(Collectors.toList());
    }


    private User getUser() {
        //I use get() because if the user is authenticated I know that it exists in user table
        return userRepo.findByUsername(getUsername()).get();
    }

    private String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
