package com.andre.adidas.codechallenge;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.andre.adidas.codechallenge.entities.City;
import com.andre.adidas.codechallenge.enums.CalcType;
import com.andre.adidas.codechallenge.pojo.Itinerary;
import com.andre.adidas.codechallenge.utils.RandomUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@Slf4j
public class Calculator {
	private Itinerary itinerary;
	private List<City> cities;
	
	public Itinerary startParalel(CalcType calcType) {
		Itinerary processedItinerarie = new Itinerary(itinerary.getDeparture(), itinerary.getArrival());
		processedItinerarie.setDepartureDatetime(LocalDateTime.now());
	
		int citiesCount = cities.size();
		log.debug("total cities: " + citiesCount);
		
		int pieceSize = 10;
		int reminder = citiesCount % pieceSize;
		
		List<Integer> order = range(0, cities.size());
		List<List<Integer>> splitedRoutes = splitList(order, pieceSize, reminder);
		List<List<City>> splitedCities = splitList(cities, pieceSize, reminder);
		
		int alternativesOrdersAmount = 10000;
		
		List<Future<Map.Entry<List<Integer>, List<Integer>>>> results = new ArrayList<>();
		
		ExecutorService threadPool = Executors.newFixedThreadPool(pieceSize);
		
		for(int i = 0; i < splitedCities.size(); i++) {
			final List<City> cityList = splitedCities.get(i);
			final List<Integer> pieceOfListOrder = splitedRoutes.get(i);
			
			Future<Entry<List<Integer>, List<Integer>>> future = threadPool.submit(() -> {
				List<Integer> orderList = range(0, cityList.size());
				List<Integer> costs = new ArrayList<>();
				List<Integer> bestOrderList = orderList.stream().map(item -> pieceOfListOrder.get(item)).collect(Collectors.toList());
				int bestTotal = Integer.MAX_VALUE;
				
				List<Integer> currentCost = new ArrayList<>();
				for (int j = 0; j < alternativesOrdersAmount; j++) {
					int total = 0;
					
					if(calcType == CalcType.DISTANCE) {
						total = totalDistance(cityList, orderList, currentCost);
					} else {
						total = totalTime(cityList, orderList, currentCost);
					}
					
					if(total < bestTotal) {
						bestTotal = total;
						bestOrderList = orderList.stream().map(item -> pieceOfListOrder.get(item)).collect(Collectors.toList());
						costs.clear();
						costs.addAll(currentCost);
					}
					
					Collections.shuffle(orderList);
					currentCost.clear();
				}
				
				return new AbstractMap.SimpleEntry<List<Integer>, List<Integer>>(costs, bestOrderList);
			});
			
			results.add(future);
		}
		
		threadPool.shutdown();
		
		List<Integer> costs = results.stream().flatMap(item -> {
			try {
				return item.get().getKey().stream();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
		
		order.clear();
		order.addAll(results.stream().flatMap(item -> {
			try {
				return item.get().getValue().stream();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList()));
		
		List<City> reorderedCities = order.stream().map(item -> cities.get(item)).collect(Collectors.toList());
		
		int departureIndex = reorderedCities.indexOf(processedItinerarie.getDeparture());
		int arrivalIndex = reorderedCities.indexOf(processedItinerarie.getArrival());
		
		if(departureIndex > arrivalIndex) {
			int temp = departureIndex;
			departureIndex = arrivalIndex;
			arrivalIndex = temp;
		}
		
		reorderedCities = reorderedCities.subList(departureIndex, arrivalIndex + 1);
		int bestTotal = costs.stream().mapToInt(Integer::intValue).sum();
		
		processedItinerarie.setCities(reorderedCities);
		
		if(calcType == CalcType.DISTANCE) {
			processedItinerarie.setDistanceCost(bestTotal);
			processedItinerarie.setDepartureDatetime(null);
		} else {
			int timeInMinutes = bestTotal;
			processedItinerarie.setArriveDatetime(processedItinerarie.getDepartureDatetime().plusMinutes(timeInMinutes));
		}
		
		log.debug("Itinerary: " + processedItinerarie);
		
		return processedItinerarie;
	}
	
	@Synchronized
	private List<Integer> range(int start, int end) {
		return IntStream.range(start, end).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
	}
	
	@Synchronized
	private int totalDistance(List<City> cities, List<Integer> order) {
		return totalDistance(cities, order, null);
	}

	@Synchronized
	private int totalDistance(List<City> cities, List<Integer> order, List<Integer> costList) {
		int sum = 0;
		
		for (int i = 0; i < order.size() - 1; i++) {
			int aIndex = order.get(i);
			int bIndex = order.get(i + 1);
			
			City cityA = cities.get(aIndex);
			City cityB = cities.get(bIndex);
			
			int distance = distance(cityA, cityB);
			sum += distance;
			
			if(costList != null) {
			  costList.add(distance);
			}
		}
		
		return sum;
	}
	
	@Synchronized
	private int distance(City cityA, City cityB) {
		return (int) Math.hypot(cityA.getX() - cityB.getX(), cityA.getY() - cityB.getY());
	}
	
	@Synchronized
	private int totalTime(List<City> cities, List<Integer> order, List<Integer> timeCosts) {
		int sum = 0;
		double timePerDistanceUnit = RandomUtils.nextDouble(1, 1.3); // Let's give it a random number to simulate a bad road for example
		
		for (int i = 0; i < order.size() - 1; i++) {
			int aIndex = order.get(i);
			int bIndex = order.get(i + 1);
			
			City cityA = cities.get(aIndex);
			City cityB = cities.get(bIndex);
			
			int distance = distance(cityA, cityB);
			sum += distance;
			
			if(timeCosts != null) {
				timeCosts.add(distance);
			}
		}
		
		return (int) (sum * timePerDistanceUnit);
	}
	
	@Synchronized
	private <T> List<List<T>> splitList(List<T> list, int pieceSize, int reminder) {
		List<List<T>> allLists = new ArrayList<>(pieceSize);
		int lastIndex = 0;
		int times = list.size() / pieceSize;
		
		for (int i = 0; i < times; i++) {
			List<T> listPiece = new ArrayList<>(list.subList(lastIndex, lastIndex + pieceSize));
			allLists.add(listPiece);
			
			lastIndex = lastIndex + pieceSize;
		}
		
		List<T> lastList = allLists.get(allLists.size() -1);
		int j = list.size() -1;
		for (int i = 0; i < reminder; i++) {
			lastList.add(list.get(j--));
		}
		
		return allLists;
	}
}
