package com.DigitalSettings.thermostat.entity;


import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "thermostats")
public class Thermostat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "thermostat_id")
    private Long id;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "initial_temperature")
    private float initTemperature;

    @Column(name = "current_temperature")
    private float currentTemperature;

    @Column(name = "threshold_temperature")
    private float thresholdTemperature;

    @Column(name = "critical")
    private boolean critical;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}

