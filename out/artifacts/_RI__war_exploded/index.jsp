<%--
  Created by IntelliJ IDEA.
  User: rshad
  Date: 9/12/17
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="I_Recovery.IndexCreator"  %>
<%@ page import="I_Recovery.ContentSearch" %>
<%@ page import="I_Recovery.MyClass" %>
<%@ page import="java.io.File" %>

<%
  MyClass example = new MyClass();
  String IndexDirPath = ( new File(".").getCanonicalPath() ) + "/" + "Index";
  String FacetsIndexDirPath = ( new File(".").getCanonicalPath() ) + "/" + "facets";
  IndexCreator myIndexCreator = new IndexCreator(IndexDirPath,FacetsIndexDirPath);



%>

<!DOCTYPE html>
<html>
<head>
  <title>Do Search</title>
</head>
<body>
  <header>
  </header>
  Hello World

</body>
</html>
