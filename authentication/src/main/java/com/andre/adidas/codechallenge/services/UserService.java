package com.andre.adidas.codechallenge.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.andre.adidas.codechallenge.entities.User;

/**
 * @author andr3medeiros
 * https://github.com/andr3medeiros
 */
public interface UserService {
  User save(User user);
  void delete(Long id);
  User findById(Long id);
  User findByEmail(String email);
  User findByUsername(String username);
  Page<User> findByName(String name, Pageable pageable);
  Page<User> findByNameContainsIgnoreCase(String name, Pageable pageable);
  Page<User> findByEmailContainsIgnoreCase(String email, Pageable pageable);
}
