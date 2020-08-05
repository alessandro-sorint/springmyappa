package com.myapp.repository;

import java.util.ArrayList;
import java.util.List;

import com.myapp.pojo.shop.Articolo;
import com.myapp.pojo.shop.Ordine;

public class OrdineRespository {
    private List<Ordine> ordini; 
    
    public List<Ordine> getAll(){
 	   if(ordini == null) {
 		   createOrdini();
 	   }
 	   
 	   return ordini;
    }
    
    private void createOrdini() {
    	ordini = new ArrayList<Ordine>();
    	
    	
    }
}
