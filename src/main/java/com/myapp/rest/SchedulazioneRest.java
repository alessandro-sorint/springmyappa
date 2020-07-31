package com.myapp.rest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.entity.Schedulazione;
import com.myapp.entity.User;
import com.myapp.framework.MyController;
import com.myapp.repository.SchedulazioneRepository;
import com.myapp.repository.UserRepository;

@RestController
@RequestMapping("schedulazioni")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class SchedulazioneRest extends MyController{
	
	@Autowired
	private SchedulazioneRepository repository;
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping(path="getAll", consumes = MediaType.ALL_VALUE)
	public List<Schedulazione> getAll() {
		System.out.println("sched getAll begin " + getUser());
		return repository.findAll();
	}
	
	@DeleteMapping("deleteById/{id}")
	public String deleteById(@PathVariable("id") int id) {
		repository.deleteById(id);
		
		return "SUCCESS";
	}
	
	@PostMapping("deleteAllByUsername")
	public String deleteAllByUsername(@RequestBody Utente utente) {
		User user = userRepository.findByUsername(utente.getUsername());
		
		repository.deleteAllByUser(user.getInternalid());
		
		return "SUCCESS";
	}
}
