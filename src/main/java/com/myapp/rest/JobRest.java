package com.myapp.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.dao.impl.UserDaoImpl;
import com.myapp.entity.Job;
import com.myapp.entity.User;
import com.myapp.framework.MyController;
import com.myapp.repository.JobRepository;
import com.myapp.repository.UserRepository;

@RestController
@RequestMapping("/jobs")
//@Secured({"ADMIN"}) //non funziona
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class JobRest extends MyController{
	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserDaoImpl userDao;

	@GetMapping
	public Iterable<Job> getProducts() {
		return jobRepository.findAll();
	}
	
	@PostMapping(path="createjob", consumes = MediaType.ALL_VALUE)
	public Map create(@RequestBody(required = false) MultiValueMap<String, String> paramMap) {
		if(paramMap == null && paramMap.get("name") == null) {
	        throw new IllegalArgumentException("name not provided");
	    }
		
		Job job = new Job();
		job.setName(paramMap.getFirst("name"));
		job.setUser(getUser());
		
		job = jobRepository.save(job);
		
		Map<String, String> retVal = new HashMap<String,String>();
		retVal.put("name", job.getName());
		retVal.put("id", Integer.toString(job.getId()));
	
	    return retVal;
	}
	
	@PutMapping
	public Job update(@RequestBody Job job) {
		return jobRepository.save(job);
	}
	
	@GetMapping("/userjobs/{userid}")
	public List<Job> getProductForUser(@PathVariable("userid") int userid) {
		System.out.println("PROVA   REST!!!");
		User user = userRepository.findById(userid).get();
		return jobRepository.findByUser(user);
	}
	
	@GetMapping("/userdao/{userid}")
	public User getUserById(@PathVariable("userid") int userid) {
		return userDao.findUser(userid);
	}
	
	//cancella tutti i job dell'utente
	@DeleteMapping("/userjobsdelete")
	public String deleteAllJobs() {
		System.out.println("User: " + getUser());
		
		jobRepository.deleteByUserid(getUser().getInternalid());
		
		return "OK";
	}
}
