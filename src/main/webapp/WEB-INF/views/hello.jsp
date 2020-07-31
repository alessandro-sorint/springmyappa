	<%
		Integer id = (Integer) request.getAttribute("id");
		String name = (String) request.getAttribute("name");
		Integer salary = (Integer) request.getAttribute("salary");
		out.println("ID: " + id);
		out.println("Name: " + name);
		out.println("salary: " + salary);
	%>
	<br /> 
	Id:
	<b>${id}</b> 
	 Name
	<b>${name}</b> 
	Salary:
	<b>${salary}</b>
	
	<p><%= id+"-" + name + "-" + salary %></p>
	<p><%= request.getAttribute("id")+"-" + request.getAttribute("name") + "-" + request.getAttribute("salary") %></p>
