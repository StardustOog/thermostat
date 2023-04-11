package com.DigitalSettings.thermostat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ThermostatManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThermostatManagementSystemApplication.class, args);
	}

}
