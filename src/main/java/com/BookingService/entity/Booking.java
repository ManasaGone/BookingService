package com.BookingService.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookingId;
    @NotNull(message = "Please enter Name")
    private String username;
    @NotBlank(message = "Vehicle number is mandatory")
    @Size(max = 20, message = "Vehicle number should not exceed 20 characters")
    @Column(name = "vehicle_No")
    private String vehicleNo;

    @NotBlank(message = "Vehicle name is mandatory")
    @Size(max = 50, message = "Vehicle name should not exceed 50 characters")
    @Column(name = "vehicle_name")
    private String vehicleName;

    @NotNull(message = "Route ID is mandatory")
    @Column(name = "route_id")
    private int routeId;

    @NotBlank(message = "Source is mandatory")
    @Size(max = 100, message = "Source should not exceed 100 characters")
    @Column(name = "source")
    private String source;

    @NotBlank(message = "Destination is mandatory")
    @Size(max = 100, message = "Destination should not exceed 100 characters")
    @Column(name = "destination")
    private String destination;

    @NotNull(message = "Booking date is mandatory")
    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @NotNull(message = "Journey date is mandatory")
    @Future(message = "Journey date must be a future date")
    @Column(name = "journey_date")
    private LocalDate journeyDate;

    @Size(max = 100, message = "Boarding point should not exceed 100 characters")
    @Column(name = "boarding_point")
    private String boardingPoint;

    @Size(max = 100, message = "Drop point should not exceed 100 characters")
    @Column(name = "drop_point")
    private String dropPoint;
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be exactly 10 digits.")
    private String contactNo;

    @NotNull(message = "Fare is mandatory")
    @Column(name = "fare")
    private Double fare;

    @NotNull(message = "Number of passengers is mandatory")
    @Column(name = "no_of_passengers")
    private Integer noOfPassengers;

    @NotBlank(message = "Booking status is mandatory")
    @Size(max = 20, message = "Booking status should not exceed 20 characters")
    @Column(name = "booking_status")
    private String bookingStatus;

    private int customerId;
}
