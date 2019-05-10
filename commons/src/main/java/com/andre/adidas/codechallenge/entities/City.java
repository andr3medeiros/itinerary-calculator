package com.andre.adidas.codechallenge.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author andr3medeiros
 * https://github.com/andr3medeiros
 */

@Entity(name = "cities")
@Data
@NoArgsConstructor
public class City implements Serializable {
	private static final long serialVersionUID = 5084175272429416041L;

	@Id
    @GeneratedValue
    private Long id;
	private String name;
	private Integer x;
	private Integer y;
	
	public City(String name, Integer x, Integer y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
}
