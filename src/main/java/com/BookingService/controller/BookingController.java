package com.BookingService.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BookingService.dto.BookingDto;
import com.BookingService.dto.Route;
import com.BookingService.dto.Vehicle;
import com.BookingService.entity.Booking;
import com.BookingService.feign.RouteServiceClient;
import com.BookingService.feign.VehicleServiceClient;
import com.BookingService.service.BookingService;


@RestController
@RequestMapping("/bookings")
@CrossOrigin("*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private VehicleServiceClient vehicleService;

    @Autowired
    private RouteServiceClient routeService;

    @PostMapping("/addBooking")
    public Booking createBooking(@RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @GetMapping("/viewBooking/{bookingId}")
    public Booking getBookingById(@PathVariable Long bookingId) {
        return bookingService.getBookingById(bookingId);
    }
    
	
    @GetMapping("/viewAllBookings")
    public List<BookingDto> viewAllBookings() {
        return bookingService.viewAllBookings();
    }
    @DeleteMapping("/deleteBooking/{bookingId}")
    public String cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return "Booking cancelled successfully";
    }

   
    @GetMapping("/ViewAllVehicles")
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/ViewAllRoutes")
	public List<Route> getAllRoutes(){
		return routeService.getAllRoutes();
	}
    @GetMapping("/customer/{customerId}")
    public List<Booking> getBookingsByCustomerId(@PathVariable int customerId) {
        return bookingService.getBookingsByCustomerId(customerId);
    }
}
