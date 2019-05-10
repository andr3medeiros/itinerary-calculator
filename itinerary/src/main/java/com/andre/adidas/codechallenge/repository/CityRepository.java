package com.andre.adidas.codechallenge.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.andre.adidas.codechallenge.entities.City;

public interface CityRepository extends PagingAndSortingRepository<City, Long> {
}