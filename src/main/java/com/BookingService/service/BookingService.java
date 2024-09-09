package com.BookingService.service;

import java.time.LocalDate;


import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BookingService.dto.BookingDto;
import com.BookingService.dto.Route;
import com.BookingService.dto.Vehicle;
import com.BookingService.entity.Booking;
import com.BookingService.exception.ResourceNotFoundException;
import com.BookingService.feign.RouteServiceClient;
import com.BookingService.feign.VehicleServiceClient;
import com.BookingService.repository.BookingRepository;

@Service
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private VehicleServiceClient vehicleService;
    @Autowired
    private RouteServiceClient routeService;
    public Booking createBooking(Booking booking) {
        logger.info("Creating booking for vehicle name: {}", booking.getVehicleName());

        // Retrieve vehicle details using vehicle name
        Vehicle vehicle = vehicleService.getVehicleByName(booking.getVehicleName());
        if (vehicle == null) {
            logger.error("Vehicle not found for name: {}", booking.getVehicleName());
            throw new ResourceNotFoundException("Vehicle not found for name :: " + booking.getVehicleName());
        }
        booking.setVehicleNo(vehicle.getVehicleNo());
        booking.setVehicleName(vehicle.getVehicleName()); // Optionally set the vehicle name

        // Retrieve route details using route ID
        Route route = routeService.getRouteById(booking.getRouteId());
        if (route == null) {
            logger.error("Route not found for ID: {}", booking.getRouteId());
            throw new ResourceNotFoundException("Route not found for id :: " + booking.getRouteId());
        }
        booking.setSource(route.getSource());
        booking.setDestination(route.getDestination());

        // Set initial values for fields not provided
        if (booking.getBookingDate() == null) {
            booking.setBookingDate(LocalDate.now());
        }
        if (booking.getJourneyDate() == null) {
            throw new IllegalArgumentException("Journey date is required");
        }

        // Set booking status based on journey date
        if (booking.getJourneyDate().isBefore(LocalDate.now())) {
            booking.setBookingStatus("Completed");
        } else {
            booking.setBookingStatus("Upcoming");
        }

        // Save booking
        Booking savedBooking = bookingRepository.save(booking);
        logger.info("Booking created successfully with ID: {}", savedBooking.getBookingId());
        return savedBooking;
    }


    public Booking getBookingById(Long bookingId) {
        logger.info("Fetching booking by ID: {}", bookingId);
        return bookingRepository.findById(bookingId)
            .orElseThrow(() -> {
                logger.error("Booking not found for ID: {}", bookingId);
                return new ResourceNotFoundException("Booking not found for this id :: " + bookingId);
            });
    }

    public void cancelBooking(Long bookingId) {
        logger.info("Cancelling booking with ID: {}", bookingId);
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> {
                logger.error("Booking not found for ID: {}", bookingId);
                return new ResourceNotFoundException("Booking not found for this id :: " + bookingId);
            });
        booking.setBookingStatus("Cancelled");
        bookingRepository.save(booking);
        logger.info("Booking cancelled successfully for ID: {}", bookingId);
    }

	
    public List<BookingDto> viewAllBookings() {
        logger.info("Fetching all bookings");

        List<Booking> bookings = bookingRepository.findAll();
        if (bookings.isEmpty()) {
            logger.warn("No bookings found.");
           
        }

        return bookings.stream().map(booking -> {
            BookingDto bookingDto = new BookingDto();
            bookingDto.setBookingId(booking.getBookingId().toString());
            bookingDto.setUsername(booking.getUsername().toString());
            bookingDto.setVehicleNo(booking.getVehicleNo());
            bookingDto.setVehicleName(booking.getVehicleName());
            bookingDto.setSource(booking.getSource());
            bookingDto.setDestination(booking.getDestination());
            bookingDto.setJourneyDate(booking.getJourneyDate());
            bookingDto.setBookingDate(booking.getBookingDate());
            bookingDto.setBoardingPoint(booking.getBoardingPoint());
            bookingDto.setDropPoint(booking.getDropPoint());
            bookingDto.setContactNo(booking.getContactNo());
            bookingDto.setFare(booking.getFare());
            bookingDto.setNoOfPassengers(booking.getNoOfPassengers());
            bookingDto.setBookingStatus(booking.getBookingStatus());
            return bookingDto;
        }).collect(Collectors.toList());
    }


    public List<Vehicle> getAllVehicles() {
        logger.info("Fetching all vehicles");
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        if (vehicles.isEmpty()) {
            logger.warn("No vehicles found.");
        }
        return vehicles;
    }
   
    public List<Route> getAllRoutes() {
        logger.info("Fetching all routes");
        List<Route> routes = routeService.getAllRoutes(); // Assuming routeService is used for fetching routes
        if (routes.isEmpty()) {
            logger.warn("No routes found.");
            throw new ResourceNotFoundException("No routes found");
        }
        return routes;
    }

    public List<Booking> getBookingsByCustomerId(int customerId) {
        return bookingRepository.findByCustomerId(customerId);
    }
}


