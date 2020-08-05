package com.myapp.pojo.shop;

import java.math.BigDecimal;

public class Articolo {
	private int id;
	private String nomeProdotto;
	private double prezzo;
	
	public Articolo() {
		
	}
	
	public Articolo(int id, String nomeProdotto, double prezzo) {
		this.id = id;
		this.nomeProdotto = nomeProdotto;
		this.prezzo = prezzo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeProdotto() {
		return nomeProdotto;
	}

	public void setNomeProdotto(String nomeProdotto) {
		this.nomeProdotto = nomeProdotto;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

}
