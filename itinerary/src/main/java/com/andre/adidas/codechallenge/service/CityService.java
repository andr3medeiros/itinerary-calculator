package com.andre.adidas.codechallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.andre.adidas.codechallenge.entities.City;
import com.andre.adidas.codechallenge.repository.CityRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author André da Silva Medeiros
 */
@Service
@Slf4j
public class CityService {
	@Autowired
    private CityRepository cityRepository;

    public Iterable<City> list(Pageable pageable) {
        log.info("Listing all cities paged");
        return cityRepository.findAll(pageable);
    }
    
    public Iterable<City> listAll() {
        log.info("Listing all cities");
        return cityRepository.findAll();
    }
}
