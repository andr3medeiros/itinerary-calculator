package com.andre.adidas.codechallenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andre.adidas.codechallenge.entities.Role;

/**
 * @author andr3medeiros
 * https://github.com/andr3medeiros
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByLabelEquals(String label);
}
