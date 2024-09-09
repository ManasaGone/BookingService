package com.BookingService.dto;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    @NotBlank(message = "Booking ID is mandatory")
    private String bookingId;
    @NotNull(message = "Please enter Name")
    private String username;
    @NotBlank(message = "Vehicle number is mandatory")
    @Size(max = 20, message = "Vehicle number should not exceed 20 characters")
    private String vehicleNo;

    @NotBlank(message = "Vehicle name is mandatory")
    @Size(max = 50, message = "Vehicle name should not exceed 50 characters")
    private String vehicleName;

    @NotNull(message = "Booking date is mandatory")
    private LocalDate bookingDate;

    @NotNull(message = "Journey date is mandatory")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Journey date must be in the format yyyy-MM-dd")
    private LocalDate journeyDate;

    @NotBlank(message = "Source is mandatory")
    @Size(max = 100, message = "Source should not exceed 100 characters")
    private String source;

    @NotBlank(message = "Destination is mandatory")
    @Size(max = 100, message = "Destination should not exceed 100 characters")
    private String destination;

    @Size(max = 100, message = "Boarding point should not exceed 100 characters")
    private String boardingPoint;

    @Size(max = 100, message = "Drop point should not exceed 100 characters")
    private String dropPoint;
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be exactly 10 digits.")
    @Column(name = "contact_no")
    private String contactNo;
    @NotNull(message = "Fare is mandatory")
    @Min(value = 0, message = "Fare must be a positive number")
    private Double fare;

    @Min(value = 1, message = "Number of passengers must be at least 1")
    private int noOfPassengers;

    @NotBlank(message = "Booking status is mandatory")
    @Size(max = 20, message = "Booking status should not exceed 20 characters")
    private String bookingStatus;

}
