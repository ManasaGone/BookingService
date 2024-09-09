package com.BookingService.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long driverId;

    @NotBlank(message = "Vehicle number cannot be blank")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$", message = "Vehicle number must be in the format 'XX00XX0000'")
    private String vehicleNo;

    @NotBlank(message = "Vehicle name cannot be blank")
    private String vehicleName;

    @Min(value = 1, message = "Seating capacity must be at least 1")
    private int seatingCapacity;

    @NotBlank(message = "Vehicle type cannot be blank")
    private String vehicleType;

    @Positive(message = "Fare per km must be a positive value")
    private double farePerKm;
}
