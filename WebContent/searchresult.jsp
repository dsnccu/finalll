<%@ page language="java" contentType="text/html; charset=BIG5"
	pageEncoding="BIG5"%>
<!DOCTYPE html>
<%!public class Keyword {
		public String name;
		public double weight;

		public Keyword(String name, double weight) {
			this.name = name;
			this.weight = weight;
		}

		public String toString() {
			return "[ " + name + ", " + weight + " ]";
		}
	}%>

<html>
<head>
<meta charset="BIG5">
<style>
a:link {
	color: blue;
	background-color: transparent;
	text-decoration: none;
}

a:visited {
	color: green;
	background-color: transparent;
	text-decoration: none;
}

a:hover {
	color: green;
	background-color: transparent;
	text-decoration: underline;
}

a:active {
	color: blue;
	background-color: transparent;
	text-decoration: underline;
}
</style>
<title>UMEB</title>
</head>
<body>
	<%
		String rqname = request.getParameter("keyword");
		Keyword k = new Keyword(rqname, 39);
		String s = k.toString();
	%>
	<div align="left">
		<form name="search result" action="searchresult.jsp" target="_self"
			method="post">
			<input type="text" name="keyword" size="50" value="<%=k.name%>">
			<input type="submit" value="Enter">
		</form>
		<br>

		<div class="eachresult">
			<a href="https://www.w3schools.com/tags/att_input_align.asp">Visit
				our HTML tutorial </a>
<a href="https://www.w3schools.com/tags/att_input_align.asp"></a>

		</div>

	</div>

</body>
</html>