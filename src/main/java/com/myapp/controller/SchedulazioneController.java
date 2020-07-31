package com.myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myapp.entity.Schedulazione;
import com.myapp.framework.MyControllerMVC;
import com.myapp.pojo.SchedulazioneModel;
import com.myapp.repository.SchedulazioneRepository;

@Controller
@RequestMapping("schedulazioni")
//@PreAuthorize("isAuthenticated()")
public class SchedulazioneController extends MyControllerMVC<SchedulazioneModel>{

	private SchedulazioneModel model;
	
	@Autowired
	private SchedulazioneRepository repository;
	
	@Override
	public SchedulazioneModel getModel() {
		if(model==null)
			model = new SchedulazioneModel();
		
		return model;
	}
	
	@GetMapping
	public String index() {
		return "schedulazionimainpage";
	}

	@GetMapping("userschedulazioni")
	public String getUserSchedulazioni() {
		List<Schedulazione> shedulazioni = repository.findByUserid(getUser().getInternalid());
		request.setAttribute("schedulazioni", shedulazioni);
		
		return "userschedulazioni";
	}
	
	@PostMapping("creaschedulazioneforuser")
	public String creaSchedulazioneForUser() {
		Schedulazione s = new Schedulazione();
		s.setGiorno(getModel().getGiorno());
		s.setMese(getModel().getMese());
		s.setAnno(getModel().getAnno());
		s.setUser(getUser());
		
		s = repository.save(s);
		request.setAttribute("newschedulazione", s);
		
		return "schedulazionimainpage";
	}
}
