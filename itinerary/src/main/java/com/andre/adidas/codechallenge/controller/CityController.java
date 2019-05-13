package com.andre.adidas.codechallenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andre.adidas.codechallenge.entities.City;
import com.andre.adidas.codechallenge.service.CityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;

/**
 * @author André da Silva Medeiros
 */
@RestController
@RequestMapping("v1/cities")
@Slf4j
@Api(value = "Endpoints to manage cities")
public class CityController {
	@Autowired
    private CityService courseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "List all cities", response = City[].class, authorizations = @Authorization("Bearer") )
    public ResponseEntity<Iterable<City>> list(Pageable pageable) {
        return new ResponseEntity<>(courseService.list(pageable), HttpStatus.OK);
    }
}
