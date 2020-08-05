<%@ page
	import="
   java.util.List, 
   com.myapp.pojo.shop.Articolo"%>
   
<% 
    List<Articolo> articoli = (List<Articolo>)request.getAttribute("articoli");
	for(Articolo articolo : articoli){

%>
    <form action="/shop/addarticolo" method="post">
		<%= articolo.getNomeProdotto() %> prezzo: <%=articolo.getPrezzo() %> euro </br> 
		<input type="text" name="quantita" /> 
		<input type="hidden" name="idArticolo" value="<%=articolo.getId()%>" />
		<input type="submit" value="aggiungi articolo" />
	</form>


<%  } %>   

Vai alla pagina di <a href="/shop/aggiungiindirizzipage">spedizione</a>