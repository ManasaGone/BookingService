package com.BookingService.dto;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int routeId;

    @NotBlank(message = "Source is mandatory")
    private String source;

    @NotBlank(message = "Destination is mandatory")
    private String destination;

    @NotNull(message = "Distance cannot be null")
    @Positive(message = "Distance must be a positive number")
    private Float distance;

    @NotNull(message = "Duration cannot be null")
    @Positive(message = "Duration must be a positive number")
    private Float duration;
}
