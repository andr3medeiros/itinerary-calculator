package com.andre.adidas.codechallenge.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author andr3medeiros
 * https://github.com/andr3medeiros
 */

@Entity(name = "users")
@Data
@NoArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = -6545833514964561834L;

	@Id
    @GeneratedValue
    private Long id;

    @Pattern(regexp = "^[A-Za-z0-9]*(?:[_-][A-Za-z0-9]+)*$")
    @Column(nullable = false, unique = true, length = 20)
    private String username;
    
    @Column(length = 100)
    private String name;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    private String password;
    @JsonIgnore
    private String passwordResetUUID;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @JsonIgnore
    public String getPassword() {
        return this.password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
