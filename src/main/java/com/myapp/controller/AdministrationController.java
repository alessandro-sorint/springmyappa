package com.myapp.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myapp.entity.Job;
import com.myapp.entity.User;
import com.myapp.framework.MyController;
import com.myapp.framework.MyControllerMVC;
import com.myapp.pojo.JobModel;
import com.myapp.repository.JobRepository;

@Controller
@RequestMapping("administration")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdministrationController extends MyController {

	@Autowired
	private JobRepository jobRepository;

	@GetMapping("newjobpage")
	public String getNewJobPage() {
		return "newJob";
	}
	
	@GetMapping("newjobpagewithrest")
	public String getNewJobPageRest() {
		return "newJobRest";
	}
	
	@RequestMapping(value = "createjob", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public @ResponseBody JobModel createProva(@RequestBody(required = false) MultiValueMap<String, String> values) {
		System.out.println("mappp: " + values);
		System.out.println("user: " + getUser());
		
		Job job = new Job();
		job.setName(values.getFirst("name"));
		job.setUser(getUser());
        jobRepository.save(job);

        JobModel myJob = new JobModel();
		myJob.setName(job.getName());
		
		return myJob;
	}
}
