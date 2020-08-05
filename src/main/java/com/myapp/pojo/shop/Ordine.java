package com.myapp.pojo.shop;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myapp.entity.User;

public class Ordine {
	public int ordine;
	public double prezzoFinale;
	public String indirizzoSpedizione;
	public User user;
	public Map<Integer, Integer> articoliOrdine = new HashMap<Integer, Integer>(); //idarticolo,quantita

	public Ordine() {
		
	}
	
	public int getOrdine() {
		return ordine;
	}

	public void setOrdine(int ordine) {
		this.ordine = ordine;
	}

	public Map<Integer, Integer> getArticoliOrdine() {
		return articoliOrdine;
	}

	public void setArticoliOrdine(Map<Integer, Integer> articoliOrdine) {
		this.articoliOrdine = articoliOrdine;
	}

	public double getPrezzoFinale() {
		return prezzoFinale;
	}

	public void setPrezzoFinale(double prezzoFinale) {
		this.prezzoFinale = prezzoFinale;
	}

	public String getIndirizzoSpedizione() {
		return indirizzoSpedizione;
	}

	public void setIndirizzoSpedizione(String indirizzoSpedizione) {
		this.indirizzoSpedizione = indirizzoSpedizione;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
