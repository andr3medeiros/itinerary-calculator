package com.andre.adidas.codechallenge.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andre.adidas.codechallenge.entities.User;

/**
 * @author André da Silva Medeiros
 * Provides basic CURD operations with User entity
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
    Page<User> findByName(String name, Pageable pageable);
    Page<User> findByNameLikeIgnoreCase(String name, Pageable pageable);
    User findByEmail(String email);
    Page<User> findByEmailLikeIgnoreCase(String email, Pageable pageable);
	User findByPasswordResetUUID(String token);
}
