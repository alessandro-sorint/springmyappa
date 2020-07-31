CREA UN NUOVO JOB

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>

<script>
	$(document).ready(function() {
		$("#restButton").on('click', function() {

			$.ajax({

				url : '/jobs',
				data : {
				//id : $("#restResult").val()
				},
				success : function(responseText) {

					if (responseText != "") {
						$("#restResult").text(JSON.stringify(responseText));
					}else{
						$("#restResult").text('');
					}

				}

			});

		});

		////////

		$("#deleteButton").on('click', function() {

			$.ajax({

				url : '/jobs/userjobsdelete',
				type : "DELETE",
				data : {
				//id : $("#restResult").val()
				},
				success : function(responseText) {

					if (responseText != "") {
						$("#deleteResult").text(JSON.stringify(responseText));
					}

				}

			});

		});
	});
</script>

<form action="/jobs/createjob" method="post">
	Name: <input type="text" name="name" /> <input type="submit"
		value="crea job" />

</form>

<button id="restButton">cliccami</button>
(ritorna tutti i job di tutti gli utenti)
<div id="restResult"></div>

<button id="deleteButton">delete</button>
(cancella tutti i job dell'utente in sessione)
<div id="deleteResult"></div>