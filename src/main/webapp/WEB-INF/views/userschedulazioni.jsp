<%@ page
	import="
   java.util.List, 
   com.myapp.entity.Schedulazione"%>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

<%
	List<Schedulazione> schedulazioni = (List<Schedulazione>) request.getAttribute("schedulazioni");
%>

<%
	out.print("giorno mese anno user");
%>
</br>
</br>
<%
	for (Schedulazione s : schedulazioni) {
		out.print(s.getGiorno() + " " + s.getMese() + " " + s.getAnno() + " " + s.getUser().getUsername());
%></br>
<%
	}
%>

</br>

<button id="getAllSchedulazioniButton">ALL</button>
(ritorna tutte le schedulazioni di tutti gli utenti)
<div id="restgetAllResult"></div>


</br>
Inserisci l'id della schedulazione da rimuovere <input type="text" id="deleteSchedulazioniid"/> <button id="deleteSchedulazioniButton">delete</button>
<div id="deleteSchedulazioniResult"></div>

Inserisci lo username dell'utente <input type="text" id="deleteSchedulazioniUsername"/> <button id="deleteSchedulazioniUserButton">delete all</button>
<div id="deleteSchedulazioniUserResult"></div>

<script>
	$(document).ready(function() {
		$("#getAllSchedulazioniButton").on('click', function() {

			$.ajax({

				url : '/schedulazioni/getAll',
				data : {
				
				},
				success : function(responseText) {

					if (responseText != "") {
						$("#restgetAllResult").text(JSON.stringify(responseText));
					}else{
						$("#restgetAllResult").text('');
					}

				}

			});

		});
		
		////////
		
		$("#deleteSchedulazioniButton").on('click', function() {

			var idSchedulazione = $('#deleteSchedulazioniid').val();
			if(idSchedulazione != '' && $.isNumeric(idSchedulazione)){
				$.ajax({
	
					url : '/schedulazioni/deleteById/' + idSchedulazione,
					method: 'DELETE',
					data : {
					
					},
					success : function(responseText) {
	
						if (responseText != "") {
							$("#deleteSchedulazioniResult").text(JSON.stringify(responseText));
						}else{
							$("#deleteSchedulazioniResult").text('');
						}
	
					}
	
				});
			}else{
				$("#deleteSchedulazioniResult").text('il valore inserito non è corretto, deve essere numerico!');
			}
		});
			//////
			
		$("#deleteSchedulazioniUserButton").on('click', function() {

		var username = $('#deleteSchedulazioniUsername').val();
		if(username != ''){
			$.ajax({
		        contentType: "application/json",
				url : '/schedulazioni/deleteAllByUsername',
				method: 'POST',
				data: JSON.stringify({ username: username }),
				success : function(responseText) {
					if (responseText != "") {
						$("#deleteSchedulazioniUserResult").text(JSON.stringify(responseText));
					}else{
						$("#deleteSchedulazioniUserResult").text('');
					}
	
				}
	
			});
		}else{
			$("#deleteSchedulazioniUserResult").text('il valore inserito non è corretto!');
		}
		});
	});
</script>