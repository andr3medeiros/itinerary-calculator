package com.andre.adidas.codechallenge.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andre.adidas.codechallenge.Calculator;
import com.andre.adidas.codechallenge.entities.City;
import com.andre.adidas.codechallenge.enums.CalcType;
import com.andre.adidas.codechallenge.pojo.Itinerary;
import com.andre.adidas.codechallenge.service.CityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author André da Silva Medeiros
 */
@RestController
@RequestMapping("v1/itineraries")
@Slf4j
@Api(value = "Endpoints to calculate itineraries")
public class ItinerayController {
	@Autowired
    private CityService courseService;
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Get the itinerary from two cities", response = Itinerary.class)
    public ResponseEntity<Itinerary> calculate(@RequestParam("departure") String departureCityName, 
    						   @RequestParam("arrival") String arrivalCityName,
    						   @RequestParam(value = "calcType", defaultValue = "DISTANCE") String calcType) {
    	
    	if(departureCityName.equalsIgnoreCase(arrivalCityName)) {
    		throw new IllegalArgumentException("Sorry, but the cities have the same name and therfore we can't do any calculations");
    	}
    	
    	Iterable<City> allCities = courseService.listAll();
    	List<City> cities = new ArrayList<>();
    	allCities.forEach(cities::add);
    	
    	Optional<City> departreCity = findCity(cities, departureCityName);
    	if(!departreCity.isPresent()) {
    		throw new IllegalArgumentException("City '" + departureCityName + "' not found");
    	}
    	
    	Optional<City> arriavalCity = findCity(cities, arrivalCityName);
    	if(!arriavalCity.isPresent()) {
    		throw new IllegalArgumentException("City '" + arriavalCity + "' not found");
    	}
    	
    	Itinerary itinerary = new Itinerary();
    	itinerary.setDeparture(departreCity.get());
    	itinerary.setArrival(arriavalCity.get());
		Calculator calculator = new Calculator(itinerary, cities);
		
		return new ResponseEntity<>(calculator.startParalel(CalcType.valueOf(calcType)), HttpStatus.OK);
    }
    
    private Optional<City> findCity(List<City> cities, String cityName) {
    	return cities.stream().filter(city -> city.getName().equalsIgnoreCase(cityName)).findFirst();
    }
}
