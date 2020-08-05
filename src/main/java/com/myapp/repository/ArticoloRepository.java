package com.myapp.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.myapp.pojo.shop.Articolo;

@Repository
public class ArticoloRepository {
   public static List<Articolo> articoli;
   
   public List<Articolo> getAll(){
	   if(articoli == null) {
		   createArticoli();
	   }
	   
	   return articoli;
   }
   
   private void createArticoli() {
	   articoli = new ArrayList<Articolo>();
	   
	   articoli.add(new Articolo(1, "bicchiere argento", 10));
	   articoli.add(new Articolo(2, "meglietta nera", 5));
	   articoli.add(new Articolo(3, "Play Station", 250));
	   articoli.add(new Articolo(4, "orologio", 50));
	   articoli.add(new Articolo(5, "mouse", 8));
   }
   
   public Articolo findById(int id) {
	   for(Articolo a : getAll()) {
		   if(a.getId()==id) {
			   return a;
		   }
	   }
	   
	   return null;
   }
   
}
