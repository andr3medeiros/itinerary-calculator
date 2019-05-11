package com.andre.adidas.codechallenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.andre.adidas.codechallenge.entities.User;
import com.andre.adidas.codechallenge.exception.UserNotFoundException;
import com.andre.adidas.codechallenge.repository.UserRepository;

/**
 * @author André da Silva Medeiros
 * UserService provides basic CRUD funtionality for User entity
 */
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User findById(Long id) {
        if(userRepository.existsById(id)) {
            return userRepository.findById(id).get();
        } else {
            throw new UserNotFoundException();
        }
    }
    
    @Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Page<User> findByName(String name, Pageable pageable) {
        return userRepository.findByName(name, pageable);
    }

    @Override
    public User save(User user) {
    	User one = userRepository.findById(user.getId()).get();
        if(one == null) {
           user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
	public Page<User> findByNameContainsIgnoreCase(String name, Pageable pageable) {
		return userRepository.findByNameLikeIgnoreCase(name, pageable);
	}

	@Override
	public Page<User> findByEmailContainsIgnoreCase(String email, Pageable pageable) {
		return userRepository.findByEmailLikeIgnoreCase(email, pageable);
	}
}
