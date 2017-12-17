<%--
  Author: Rshad Zhran
  Github Account : https://github.com/rshad
  LinkedIn Account : https://www.linkedin.com/in/rshad-zhran-b65b5012a/

  To change this template use File | Settings | File Templates.
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="I_Recovery.IndexCreator"  %>
<%@ page import="I_Recovery.ContentSearch" %>
<%@ page import="I_Recovery.MyClass" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.ArrayList" %>


<!doctype html>
<html lang="en">
  <head>
    <title>Hello, world!</title>

    <style>
      * {box-sizing: border-box;}

      body {margin:0;font-family:Arial;}

      .topnav {
        overflow: hidden;
        background-color: #e9e9e9;
      }

      .topnav a {
        float: left;
        display: block;
        color: black;
        text-align: center;
        padding: 14px 16px;
        text-decoration: none;
        font-size: 17px;
      }

      .topnav a:hover {
        background-color: #ddd;
        color: black;
      }

      .topnav a.active {
        background-color: #2196F3;
        color: white;
      }

      .topnav .search-container {
        float: right;
      }

      .topnav input[type=text]{
        padding: 6px;
        margin-top: 8px;
        font-size: 17px;
        border: none;
      }

      .topnav .search-container button {
        float: right;
        padding: 6px;
        margin-top: 8px;
        margin-right: 16px;
        background: #ddd;
        font-size: 17px;
        border: none;
        cursor: pointer;
      }

      .topnav .search-container button:hover {
        background: #ccc;
      }

      @media screen and (max-width: 600px) {
        .topnav .search-container {
          float: none;
        }
        .topnav a, .topnav input[type=text], .topnav .search-container button {
          float: none;
          display: block;
          text-align: left;
          width: 100%;
          margin: 0;
          padding: 14px;
        }
        .topnav input[type=text] {
          border: 1px solid #ccc;
        }
      }
    </style>

  </head>
  <body>
      <%-- ${pageContext.request.contextPath} -> dynamic path --%>
      <form name="myForm" action="${pageContext.request.contextPath}/SearchServletURL" method="post">
          <input type="text" placeholder="Search.." name="searchBox">
          <button type="submit">Submit</button>
      </form>

  </body>
</html>

