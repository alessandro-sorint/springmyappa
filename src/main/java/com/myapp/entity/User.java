package com.myapp.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myapp.framework.MyAspect;

@Entity(name = "user")
public class User implements UserDetails, Comparable<User> {

	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy=GenerationType.AUTO)
	private int internalid;

	private String username;
	private String password;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private Set<Job> jobs;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private Set<Schedulazione> schedulazioni;
	
	private String authority;
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof User)) {
			return false;
		} else {
			return this.getInternalid() == ((User) o).getInternalid();
		}
	}

	public int getInternalid() {
		return internalid;
	}

	public void setInternalid(int internalid) {
		this.internalid = internalid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Job> getJobs() {
		return jobs;
	}

	public void setJobs(Set<Job> jobs) {
		this.jobs = jobs;
	}
	
	public Set<Schedulazione> getSchedulazioni() {
		return schedulazioni;
	}

	public void setSchedulazioni(Set<Schedulazione> schedulazioni) {
		this.schedulazioni = schedulazioni;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(authority));
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

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	@Override
	public String toString() {
		return "User: " + internalid + " " + username;
	}

	@Override
	public int compareTo(User o) {
		return Integer.compare(o.getInternalid(),this.getInternalid());
	}
}