package com.andre.adidas.codechallenge.pojo;

import java.time.LocalDateTime;
import java.util.List;

import com.andre.adidas.codechallenge.entities.City;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Itinerary {
	private City departure;
	private City arrival;
	private Integer distanceCost;
	private List<City> cities;
	private LocalDateTime departureDatetime;
	private LocalDateTime arriveDatetime;
	
	public Itinerary(City departure, City arrival) {
		this.departure = departure;
		this.arrival = arrival;
	}
}