package com.andre.adidas.codechallenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.adidas.codechallenge.entities.Role;
import com.andre.adidas.codechallenge.repository.RoleRepository;

/**
 * @author andr3medeiros
 * https://github.com/andr3medeiros
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository repository;

    @Override
    public Role findDefault() {
        return repository.findByLabelEquals(RoleService.DEFAULT_LABEL);
    }

}
