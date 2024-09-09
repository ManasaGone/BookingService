package com.BookingService;

import com.BookingService.controller.BookingController;
import com.BookingService.dto.BookingDto;
import com.BookingService.dto.Route;
import com.BookingService.dto.Vehicle;
import com.BookingService.entity.Booking;
import com.BookingService.exception.ResourceNotFoundException;
import com.BookingService.feign.RouteServiceClient;
import com.BookingService.feign.VehicleServiceClient;
import com.BookingService.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.function.RequestPredicate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private VehicleServiceClient vehicleService;

    @Mock
    private RouteServiceClient routeService;

    @InjectMocks
    private BookingController bookingController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    void viewAllBookings_Success() throws Exception {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setBookingId("1");

        List<BookingDto> bookingDtos = Collections.singletonList(bookingDto);

        when(bookingService.viewAllBookings()).thenReturn(bookingDtos);

        mockMvc.perform(get("/bookings/viewAllBookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bookingId").value("1"));
    }

    @Test
    void cancelBooking_Success() throws Exception {
        doNothing().when(bookingService).cancelBooking(1L);

        mockMvc.perform(delete("/bookings/deleteBooking/{bookingId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking cancelled successfully"));
    }

  


}
