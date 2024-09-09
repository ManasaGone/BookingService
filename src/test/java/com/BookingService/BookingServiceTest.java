package com.BookingService;

import com.BookingService.dto.BookingDto;
import com.BookingService.dto.Route;
import com.BookingService.dto.Vehicle;
import com.BookingService.entity.Booking;
import com.BookingService.exception.ResourceNotFoundException;
import com.BookingService.feign.RouteServiceClient;
import com.BookingService.feign.VehicleServiceClient;
import com.BookingService.repository.BookingRepository;
import com.BookingService.service.BookingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private VehicleServiceClient vehicleService;

    @Mock
    private RouteServiceClient routeService;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBooking_Success() {
        // Arrange
        Booking booking = new Booking();
        booking.setVehicleName("Vehicle1");
        booking.setRouteId(1);
        booking.setJourneyDate(LocalDate.now().plusDays(1));

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleNo("AP36AL3691");
        vehicle.setVehicleName("Vehicle1");

        Route route = new Route();
        route.setSource("Source");
        route.setDestination("Destination");

        when(vehicleService.getVehicleByName("Vehicle1")).thenReturn(vehicle);
        when(routeService.getRouteById(1)).thenReturn(route);
        when(bookingRepository.save(booking)).thenReturn(booking);

        // Act
        Booking result = bookingService.createBooking(booking);

        // Assert
        assertNotNull(result);
        assertEquals("AP36AL3691", result.getVehicleNo());
        assertEquals("Source", result.getSource());
        assertEquals("Destination", result.getDestination());
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void createBooking_VehicleNotFound() {
        // Arrange
        Booking booking = new Booking();
        booking.setVehicleName("UnknownVehicle");
        booking.setRouteId(1);
        booking.setJourneyDate(LocalDate.now().plusDays(1));

        when(vehicleService.getVehicleByName("UnknownVehicle")).thenReturn(null);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.createBooking(booking);
        });

        assertEquals("Vehicle not found for name :: UnknownVehicle", exception.getMessage());
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void getBookingById_Success() {
        // Arrange
        Booking booking = new Booking();
        booking.setBookingId(1L);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        // Act
        Booking result = bookingService.getBookingById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getBookingId());
    }

    @Test
    void getBookingById_NotFound() {
        // Arrange
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.getBookingById(1L);
        });

        assertEquals("Booking not found for this id :: 1", exception.getMessage());
    }

    @Test
    void cancelBooking_Success() {
        // Arrange
        Booking booking = new Booking();
        booking.setBookingId(1L);
        booking.setBookingStatus("Upcoming");

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(bookingRepository.save(booking)).thenReturn(booking);

        // Act
        bookingService.cancelBooking(1L);

        // Assert
        assertEquals("Cancelled", booking.getBookingStatus());
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void cancelBooking_NotFound() {
        // Arrange
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            bookingService.cancelBooking(1L);
        });

        assertEquals("Booking not found for this id :: 1", exception.getMessage());
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void viewAllBookings_EmptyList() {
        // Arrange
        when(bookingRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<BookingDto> result = bookingService.viewAllBookings();

        // Assert
        assertTrue(result.isEmpty());
    }

 }

    

