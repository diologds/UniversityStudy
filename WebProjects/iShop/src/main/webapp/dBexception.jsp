
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.StringWriter" %>
<%@ page import="lv.javaguru.java2.ishop.database.DBException" %>
<html>
<head><title>DB Exception</title></head>
<body>

<h3>DB Exception!</h3>
<% StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    DBException e = (DBException) request.getAttribute("exception");
    e.printStackTrace(pw);
    %>
<%=sw.toString()
%>
</body>
</html>