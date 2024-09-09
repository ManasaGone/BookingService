package com.BookingService.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BookingService.dto.Vehicle;
import com.BookingService.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByVehicleNo(String vehicleNo);
    List<Booking> findByCustomerId(int customerId);
}
