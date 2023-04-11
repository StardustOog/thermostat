package com.DigitalSettings.thermostat.repository;

import com.DigitalSettings.thermostat.entity.Thermostat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThermostatRepository extends JpaRepository<Thermostat, Long> {
    Optional<Thermostat> findByDeviceName(String name);
}
