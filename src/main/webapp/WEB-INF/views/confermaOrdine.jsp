<%@ page
	import="
   java.util.List, 
   com.myapp.pojo.shop.Articolo,
   com.myapp.pojo.shop.Ordine"%>

<%
	List<Articolo> articoliSelezionati = (List<Articolo>) request.getAttribute("articoliSelezionati");
	Ordine ordine = (Ordine) request.getAttribute("ordine");
%>
<p>Articoli selezionati per l'ordine</p>

<% for(Articolo articolo : articoliSelezionati) { %>
      <%= ordine.getArticoliOrdine().get(articolo.getId()) + " " + articolo.getNomeProdotto() + " Prezzo: " +  articolo.getPrezzo() + " euro"%>
      </br>
<% } %>

</br><p>Indirizzo di spedizione: <%= ordine.getIndirizzoSpedizione() %></p>

</br><p>Prezzo finale <%=ordine.getPrezzoFinale() %></p>
</br>
<form action="/shop/confermaOrdine" method="post">
	<input type="submit" value="Conferma ordine" />
</form>