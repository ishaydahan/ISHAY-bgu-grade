<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BGU - grades</title>
</head>
<body>

	<form action="ServletEx" method="post">
		username <br>
		<input type="text" name="user" /><br>
		password <br>
		<input type="text" name="pass" /><br>
		Id <br>
		<input type="text" name="id" /><br>
		p_year <br>
		<input type="text" name="p_year" /><br>
		p_semester <br>
		<input type="text" name="p_semester" /><br>
		out_institution <br>
		<input type="text" name="out_institution" /><br>
		list_department <br>
		<input type="text" name="list_department" /><br>
		list_degree_level <br>
		<input type="text" name="list_degree_level" /><br>
		list_course <br>
		<input type="text" name="list_course" /><br>

		<input type="submit" value="Submit" />
	</form>
<br>
	Pay attention - the request may take up to 1 min.

</body>
</html>
