package com.myapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.CookieGenerator;

import com.myapp.jwt.TokenUtil;
import com.myapp.pojo.shop.Articolo;
import com.myapp.pojo.shop.Ordine;
import com.myapp.repository.ArticoloRepository;
import com.myapp.repository.UserRepository;

import io.jsonwebtoken.Claims;

@Controller
@RequestMapping("shop")
public class ShopController {
	
	private String tokenHeader = "x-auth-token";
	
	@Autowired
    private ArticoloRepository articoloRepository;
	@Autowired
	private UserRepository userRepository;
	
	private static final String articoliClaim = "articoli";
	private static final String usernameClaim = "username";
	private static final String indirizzoSpedizioneClaim = "indirizzoSpedizione";
	private static final String prezzoTotaleClaim = "prezzoTotale";
	
	@GetMapping("addarticolo")
	public String addArticoloPage(HttpServletRequest request) {
		request.setAttribute("articoli", articoloRepository.getAll());
		
		return "shopMainPage";
	}
	
	@PostMapping("addarticolo")
	public String addArticolo(HttpServletRequest request, HttpServletResponse response, @RequestParam("idArticolo") int idArticolo, @RequestParam("quantita") int quantita) throws Exception {
		Articolo a = articoloRepository.findById(idArticolo);
		if(a == null) {
			throw new Exception("Articolo non trovato");
		}
		if(quantita <1) {
			throw new Exception("Quantita deve essere almeno di 1");
		}
		
		TokenUtil util = new TokenUtil();
		String token = getToken(request);
		Claims claims = util.getClaimsFromToken(token);
		
	 	Map<String, Integer> articoli = getArticoliClaim(claims);
	 	if(articoli == null) {
	 	    articoli = new HashMap();	
	 	    claims.put(articoliClaim, articoli);
	 	}
	 	
	 	if(articoli.containsKey(Integer.toString(idArticolo))) {
	 		quantita += articoli.get(Integer.toString(idArticolo));
	 	}
	 	articoli.put(Integer.toString(idArticolo), quantita);
	 	
	 	
	 	token = util.generateToken(claims, null);
	 	Cookie cookie = new Cookie(tokenHeader, token);
        response.addCookie(cookie); 
	 	
	 	request.setAttribute("articoli", articoloRepository.getAll());
		
		return "shopMainPage";
	}
	
	@GetMapping("aggiungiindirizzipage")
	public String aggiungiIndirizzoPage(HttpServletRequest request, HttpServletResponse response) {
		String token = getToken(request);
		System.out.println("aggiungiIndirizzoPage token: " + token);
		
		return "aggiungiIndirizzo";
	}
	
	@PostMapping("aggiungiindirizzo")
	public String aggiungiIndirizzo(HttpServletRequest request, HttpServletResponse response, @RequestParam("indirizzo") String indirizzoSpedizione) {
		TokenUtil util = new TokenUtil();
		
		String token = getToken(request);
        Claims claims = util.getClaimsFromToken(token);
        String username = getUsernameClaim(claims);
        
        claims.put(indirizzoSpedizioneClaim, indirizzoSpedizione);
		
		double prezzoTotale = 0;		
		Map<String, Integer> articoli = getArticoliClaim(claims);
		List<Articolo> articoliSelezionati = new ArrayList<Articolo>();

		Ordine ordine = new Ordine();
		ordine.setUser(userRepository.findByUsername(username));
		ordine.setIndirizzoSpedizione(indirizzoSpedizione);
		
		for(Map.Entry<String, Integer> entry : articoli.entrySet()) {
			System.out.println("id articolo: " + entry.getKey() + " " + entry.getValue());
			Object o = entry.getKey();
			System.out.println("PROVA: " + o.getClass());
			int idArticolo = Integer.parseInt(entry.getKey());
			int quantita = entry.getValue();
			Articolo articolo = articoloRepository.findById(idArticolo);
			articoliSelezionati.add(articolo);
			prezzoTotale += articolo.getPrezzo() * quantita;
			
			ordine.getArticoliOrdine().put(articolo.getId(), quantita);
		}
		ordine.setPrezzoFinale(prezzoTotale);
		claims.put(prezzoTotaleClaim, prezzoTotale);
		
		request.setAttribute("articoliSelezionati", articoliSelezionati);
		request.setAttribute("ordine", ordine);
		
		token = util.generateToken(claims, null);
		
		Cookie cookie = new Cookie(tokenHeader, token);
        response.addCookie(cookie);
		
		return "confermaOrdine";
	}
	
	@PostMapping("confermaOrdine")
	public String confermaOrdine(HttpServletRequest request, HttpServletResponse response) {
		String token = getToken(request);
		
        request.setAttribute(tokenHeader, token);
        
        CookieGenerator cg = new CookieGenerator();
        cg.setCookieName(tokenHeader);
        cg.removeCookie(response);
		
		return "fineShop";
	}
	
	private String getToken(HttpServletRequest request) {
		Cookie[] cookie = request.getCookies();
		for(Cookie c : cookie) {
			if(c.getName().equals(tokenHeader))
				return c.getValue();
		}
		
		return null;
	}
	
	private Map<String,Integer> getArticoliClaim(Claims claims){
	 	Map<String,Integer> articoli = null;
	 	if(claims.containsKey(articoliClaim)) {
	 		articoli = (Map<String,Integer>)claims.get(articoliClaim);
	 	}
	 	
	 	return articoli;
	}
	
	private String getUsernameClaim(Claims claims) {
		String username = null;
		if(claims.containsKey(usernameClaim)) {
			username = (String)claims.get(usernameClaim);
		}
		
		return username;
	}
	
	private String getIndirizzoSpedizioneClaim(Claims claims) {
		String indirizzoSpedizione = null;
		if(claims.containsKey(indirizzoSpedizioneClaim)) {
			indirizzoSpedizione = (String)claims.get(indirizzoSpedizioneClaim);
		}
		
		return indirizzoSpedizione;
	}
	
	private double getPrezzoTotaleClaim(Claims claims) {
		double prezzoTotale = -1;
		if(claims.containsKey(prezzoTotaleClaim)) {
			prezzoTotale = (double)claims.get(prezzoTotaleClaim);
		}
		
		return prezzoTotale;
	}
	
}
