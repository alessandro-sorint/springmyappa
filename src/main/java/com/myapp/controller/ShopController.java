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

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.myapp.jwt.TokenUtil;
import com.myapp.pojo.shop.Articolo;
import com.myapp.pojo.shop.Ordine;
import com.myapp.repository.ArticoloRepository;
import com.myapp.repository.UserRepository;

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

		String username = getUsernameClaim(token);
		
	 	Map<String,Object> articoli = getArticoliClaim(token);
	 	if(articoli == null) {
	 	    articoli = new HashMap();	
	 	}
	 	
	 	if(articoli.containsKey(Integer.toString(idArticolo))) {
	 		quantita += ((Integer)articoli.get(Integer.toString(idArticolo)));
	 	}
	 	articoli.put(Integer.toString(idArticolo), quantita);
	 	
	 	token = util.getBuilder().withClaim("username", username).withClaim("articoli", articoli).sign(util.getAlgorithm());
	 	Cookie cookie = new Cookie(tokenHeader, token);
        response.addCookie(cookie); 
	 	
	 	request.setAttribute("articoli", articoloRepository.getAll());
		
		return "shopMainPage";
	}
	
	@GetMapping("aggiungiindirizzipage")
	public String aggiungiIndirizzoPage(HttpServletRequest request, HttpServletResponse response) {
		return "aggiungiIndirizzo";
	}
	
	@PostMapping("aggiungiindirizzo")
	public String aggiungiIndirizzo(HttpServletRequest request, HttpServletResponse response, @RequestParam("indirizzo") String indirizzoSpedizione) {
		TokenUtil util = new TokenUtil();
		
		String token = getToken(request);
		String username = getUsernameClaim(token);
		
		double prezzoTotale = 0;		
		Map<String, Object> articoli = getArticoliClaim(token);
		List<Articolo> articoliSelezionati = new ArrayList<Articolo>();

		Ordine ordine = new Ordine();
		ordine.setUser(userRepository.findByUsername(username));
		ordine.setIndirizzoSpedizione(indirizzoSpedizione);
		
		for(Map.Entry<String, Object> entry : articoli.entrySet()) {
			int idArticolo = Integer.parseInt(entry.getKey());
			int quantita = (Integer)entry.getValue();
			Articolo articolo = articoloRepository.findById(idArticolo);
			articoliSelezionati.add(articolo);
			prezzoTotale += articolo.getPrezzo() * quantita;
			
			ordine.getArticoliOrdine().put(articolo.getId(), quantita);
		}
		ordine.setPrezzoFinale(prezzoTotale);
		
		request.setAttribute("articoliSelezionati", articoliSelezionati);
		request.setAttribute("ordine", ordine);
		
		token = util.getBuilder().withClaim("username", username)
				.withClaim("articoli", articoli)
				.withClaim("indirizzoSpedizione", indirizzoSpedizione)
				.withClaim("prezzoTotale", prezzoTotale)
				.sign(util.getAlgorithm());
		
		Cookie cookie = new Cookie(tokenHeader, token);
        response.addCookie(cookie);
		
		return "confermaOrdine";
	}
	
	@PostMapping("confermaOrdine")
	public String confermaOrdine(HttpServletRequest request, HttpServletResponse response) {
		TokenUtil util = new TokenUtil();
		
		String token = getToken(request);
		
		String username = getUsernameClaim(token);
		String indirizzoSpedizione = getIndirizzoSpedizioneClaim(token);
		double prezzoTotale = getPrezzoTotaleClaim(token);
		Map<String, Object> articoli = getArticoliClaim(token);
		
		Ordine ordine = new Ordine();
		ordine.setUser(userRepository.findByUsername(username));
		ordine.setIndirizzoSpedizione(indirizzoSpedizione);
		ordine.setPrezzoFinale(prezzoTotale);
		
		for(Map.Entry<String, Object> entry : articoli.entrySet()) {
			int idArticolo = Integer.parseInt(entry.getKey());
			int quantita = (Integer)entry.getValue();
		    ordine.getArticoliOrdine().put(idArticolo, quantita);	
		}
		
		token = util.getBuilder().withClaim("username", username)
				.withClaim("articoli", articoli)
				.withClaim("indirizzoSpedizione", indirizzoSpedizione)
				.withClaim("prezzoTotale", prezzoTotale)
				.sign(util.getAlgorithm());
		
        request.setAttribute(tokenHeader, token);
		
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
	
	private Map<String,Object> getArticoliClaim(String token){
		TokenUtil util = new TokenUtil();
		DecodedJWT decoded = util.decodeToken(token);
		
	 	Map<String,Claim> claims =  decoded.getClaims();
	 	
	 	Map<String,Object> articoli = null;
	 	if(claims.containsKey(articoliClaim)) {
	 		articoli = claims.get(articoliClaim).asMap();
	 	}
	 	
	 	return articoli;
	}
	
	private String getUsernameClaim(String token) {
		TokenUtil util = new TokenUtil();
		DecodedJWT decoded = util.decodeToken(token);
		String username = null;
		if(decoded.getClaims().containsKey(usernameClaim)) {
			username = decoded.getClaim(usernameClaim).asString();
		}
		
		return username;
	}
	
	private String getIndirizzoSpedizioneClaim(String token) {
		TokenUtil util = new TokenUtil();
		DecodedJWT decoded = util.decodeToken(token);
		String indirizzoSpedizione = null;
		if(decoded.getClaims().containsKey(indirizzoSpedizioneClaim)) {
			indirizzoSpedizione = decoded.getClaim(indirizzoSpedizioneClaim).asString();
		}
		
		return indirizzoSpedizione;
	}
	
	private double getPrezzoTotaleClaim(String token) {
		TokenUtil util = new TokenUtil();
		DecodedJWT decoded = util.decodeToken(token);
		double prezzoTotale = -1;
		if(decoded.getClaims().containsKey(prezzoTotaleClaim)) {
			prezzoTotale = decoded.getClaim(prezzoTotaleClaim).asDouble();
		}
		
		return prezzoTotale;
	}
	
}
