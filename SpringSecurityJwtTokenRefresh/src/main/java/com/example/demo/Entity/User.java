package com.example.demo.Entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user")
public class User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Role role;
	
		
//	Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
//	Unimplemented Abstract methods of UserDetails abstract class
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return List.of(new SimpleGrantedAuthority(role.name()));
	}
	
	
	@Override
	public String getPassword() {

		return password;
	}
	
	
	@Override
	public String getUsername() {

		return email;
	}
	
	
	@Override
	public boolean isAccountNonExpired() {

	return true;
	}
	
	
	@Override
	public boolean isAccountNonLocked() {

		return true;
	}
	
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	

}
