package com.andre.adidas.codechallenge.services;

import com.andre.adidas.codechallenge.entities.Role;

/**
 * @author andr3medeiros
 * https://github.com/andr3medeiros
 */
public interface RoleService {
  public static String DEFAULT_LABEL = "EMPLOYEE";

  Role findDefault();
}
