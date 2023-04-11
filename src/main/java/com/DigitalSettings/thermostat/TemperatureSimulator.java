package com.DigitalSettings.thermostat;

import com.DigitalSettings.thermostat.repository.ThermostatRepository;
import com.DigitalSettings.thermostat.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class TemperatureSimulator {

    private final MailSenderService mailSenderService;
    private final ThermostatRepository thermostatRepository;

    @Value("${mintemperature}")
    private float MIN_TEMPERATURE;
    @Value("${maxtemperature}")
    private float MAX_TEMPERATURE;

    @Autowired
    public TemperatureSimulator(MailSenderService mailSenderService, ThermostatRepository thermostatRepository) {
        this.mailSenderService = mailSenderService;
        this.thermostatRepository = thermostatRepository;
    }

    @Scheduled(fixedRate = 20*60*1000)
    public void changeTemp() {
        thermostatRepository.findAll().stream()
                .forEach(
                        thermostat -> {
                            Random random = new Random();
                            final float temp = random.nextFloat()*(MAX_TEMPERATURE-MIN_TEMPERATURE) + MIN_TEMPERATURE;
                            thermostat.setCurrentTemperature(temp);
                            if(thermostat.getCurrentTemperature() <= thermostat.getInitTemperature() - thermostat.getThresholdTemperature()
                                    || thermostat.getCurrentTemperature() >= thermostat.getInitTemperature() + thermostat.getThresholdTemperature()) {
                                thermostat.setCritical(true);
                                mailSenderService.sendMessage(thermostat.getUser().getEmail(),
                                        "Critical temperature", "Critical temperature for device: " + thermostat.getDeviceName());
                            } else {
                                thermostat.setCritical(false);
                            }
                            thermostatRepository.save(thermostat);
                        }
                );
    }

}
