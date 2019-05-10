package com.andre.adidas.codechallenge.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author andr3medeiros
 * https://github.com/andr3medeiros
 */

@Entity(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
	private static final long serialVersionUID = 5084175272429416041L;

	@Id
    @GeneratedValue
    private Long id;

    private String label;

    public Role(String label) {
        this.label = label;
    }

}
