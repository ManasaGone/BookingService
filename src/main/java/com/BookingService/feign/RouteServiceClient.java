package com.BookingService.feign;
import java.util.List;


import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.BookingService.dto.Route;

@FeignClient("ROUTESERVICE")
public interface RouteServiceClient {
    
	 @GetMapping("route/ViewAllRoutes")
		public List<Route> getAllRoutes();

		@GetMapping("route/ViewRouteById/{routeId}")
		public Route getRouteById(@PathVariable int routeId);
}
