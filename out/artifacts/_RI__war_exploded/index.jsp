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
<%@ page import="java.util.ArrayList" %>

<%
    /*
      String IndexDirPath = "/home/rshad/IdeaProjects/_RI_/Index";
      String FacetsIndexDirPath ="/home/rshad/IdeaProjects/_RI_/facets";
      ContentSearch SearchEngine = new ContentSearch(IndexDirPath);
      ArrayList<String> result = SearchEngine.GeneralSearchQuery("defensor@ugr.es");
    */
%>

<!doctype html>
<html lang="en">
<head>
  <title>Hello, world!</title>
  <!-- Required meta tags -->
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>


  <style>
    body {
      padding-top: 50px;
    }
    .dropdown.dropdown-lg .dropdown-menu {
      margin-top: -1px;
      padding: 6px 20px;
    }
    .input-group-btn .btn-group {
      display: flex !important;
    }
    .btn-group .btn {
      border-radius: 0;
      margin-left: -1px;
    }
    .btn-group .btn:last-child {
      border-top-right-radius: 4px;
      border-bottom-right-radius: 4px;
    }
    .btn-group .form-horizontal .btn[type="submit"] {
      border-top-left-radius: 4px;
      border-bottom-left-radius: 4px;
    }
    .form-horizontal .form-group {
      margin-left: 0;
      margin-right: 0;
    }
    .form-group .form-control:last-child {
      border-top-left-radius: 4px;
      border-bottom-left-radius: 4px;
      width: 200px;
    }
    @media screen and (min-width: 768px) {
      #adv-search {
        width: 500px;
        margin: 0 auto;
      }
      .dropdown.dropdown-lg {
        position: static !important;
      }
      .dropdown.dropdown-lg .dropdown-menu {
        min-width: 500px;
      }

    }


  </style>

</head>
<body class="mainBody">

<div class="container">
  <div class="row">
    <div class="col-md-12">
      <div class="input-group" id="adv-search">
        <input type="text" class="form-control" placeholder="Search for snippets" />
        <div class="input-group-btn">
          <div class="btn-group" role="group">
            <div class="dropdown dropdown-lg">
              <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><span class="caret"></span></button>
              <div class="dropdown-menu dropdown-menu-right" role="menu">
                <form class="form-horizontal" role="form">
                  <div class="form-group">
                    <label for="filter">Filter by</label>
                    <select class="form-control">
                      <option class="option_form" value="0" selected>All Snippets</option>
                      <option class="option_form" value="1">Featured</option>
                      <option class="option_form" value="2">Most popular</option>
                      <option class="option_form" value="3">Top rated</option>
                      <option class="option_form" value="4">Most commented</option>
                    </select>
                  </div>
                  <div class="form-group">
                    <label for="contain">Author</label>
                    <input class="form-control" type="text" />
                  </div>
                  <div class="form-group">
                    <label for="contain">Contains the words</label>
                    <input class="form-control" type="text" />
                  </div>
                  <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
                </form>
              </div>
            </div>
            <button type="button" class="btn btn-primary"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</div>

</body>
</html>