<%@ page import="java.util.ArrayList" %>
<%@ page import="I_Recovery.SearchResultObject" %>
<%@ page import="I_Recovery.LuceneDocConstants" %><%--
  Author: Rshad Zhran
  Github Account : https://github.com/rshad
  LinkedIn Account : https://www.linkedin.com/in/rshad-zhran-b65b5012a/

  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<% ArrayList<SearchResultObject> Document_Result = (ArrayList<SearchResultObject>) request.getAttribute("Document_Resulted"); %>
<% String QueryValue = (String)request.getAttribute("QueryText");%>
<% String Query_Text = (String) request.getAttribute("QueryText"); %>

<html>
<head>
    <style>
        @import url(https://fonts.googleapis.com/css?family=Open+Sans);
        @import url(https://fonts.googleapis.com/css?family=Oswald);


        .search {
            width: 100%;
            position: relative
        }

        .searchTerm {
            position:absolute;
            float: left;
            width: 100%;
            border: 3px solid #003bff;
            padding: 5px;
            height: 36px;
            border-radius: 5px;
            outline: none;
            color: #9DBFAF;
            font-family:"Oswald";
            font-size: 20px;
        }

        .searchTerm:focus{
            color: #003bff;
        }
        .FieldsSelect{
            position: absolute;
            right: -78px;
            width: 130px;
            height: 36px;
            border: 1px solid #003bff;
            background: #003bff;
            text-align: center;
            color: #fff;
            border-radius: 5px;
            cursor: pointer;
            font-size: 20px;
            font-family:"Oswald";
        }
        .searchButton {
            position: absolute;
            right: -120px;
            width: 40px;
            height: 36px;
            border: 1px solid #003bff;
            background: #003bff;
            text-align: center;
            color: #fff;
            border-radius: 5px;
            cursor: pointer;
            font-size: 20px;
        }

        /*Resize the wrap to see the search bar change!*/
        .wrap{
            width: 30%;
            position: absolute;
            top: 1%;
            left:28%;
            transform: translate(-50%, -50%);
        }
        body {
            background: #f2f2f2;
            margin: 0;
            padding: 0;
            font-family: arial, sans-serif;
        }

        .top-bar {
            height: 60px;
            background-color: #f5f5f5;
            border-bottom: 1px solid #ccc;
            position: relative;
        }

        .left-side {
            float: left;
            height: 60px;
            font-size: 0;
        }

        #main-logo {
            height: 40px;
            margin-left: 16px;
            margin-right: 25px;
            margin-top: 14px;
        }

        #search-bar {
            width: 600px;
            height: 33px;
            border-top: 1px solid #b5b5b5;
            border-left: 1px solid #b5b5b5;
            border-bottom: 1px solid #b5b5b5;
            border-right: none;
            transform: translateY(-51%);
        }

        #mic {
            height: 23px;
            border-top: 1px solid #b5b5b5;
            border-right: 1px solid #b5b5b5;
            border-bottom: 1px solid #b5b5b5;
            border-left: none;
            padding: 6px 10px 6px 6px;
            background: white;
            margin-bottom: 5px;
        }

        .mag-background {
            background-color: #3b78e7;
            display: inline-block;
            width: 37px;
            height: 37px;
            transform: translateY(-11%);
        }

        #mag {
            height: 24px;
            width: 24px;
            display: inline-block;
            color: transparent;
            margin: 7px;
            background: url("https://www.google.com/images/nav_logo242.png") no-repeat -107px -55px;

        }

        .right-side {
            float: right;
        }

        .right-side ul {
            margin-right: 30px;
        }

        .right-side ul li {
            margin-left: 8px;
            margin-right: 8px;
            display: inline-block;
            vertical-align: middle;
        }

        #nine-square {
            background-image: url("https://ssl.gstatic.com/gb/images/i1_1967ca6a.png");
            background-position: -132px -38px;
            background-size: 528px 68px;
            opacity: 0.55;
            height: 30px;
            width: 30px;
        }

        #circle-bell {
            background-image: url("https://ssl.gstatic.com/gb/images/i1_1967ca6a.png");
            background-position: -194px -21px;
            background-size: 528px 68px;
            opacity: 0.55;
            height: 20px;
            width: 20px;
            background-color: rgba(0,0,0,.85);
            border-radius: 50%;
        }

        #profile-pic {
            width: 32px;
            height: 32px;
            background-image: url("https://cdn-img-3.wanelo.com/p/770/842/3b5/50160de6ec9a55381fcf492/x354-q80.jpg");
            background-size: 32px 32px;
            border-radius: 50%;
        }

        .scnd-bar {
            display: inline-block;
            width: 100%;
            margin-top: 10px;
            height: 59px;
            border-bottom: 1px solid #ebebeb;
        }

        .scnd-left {
            float: left;
            margin-left: 110px;
        }

        .scnd-right {
            float: right;
        }

        .nav-list {
            list-style: none;
            font-size: 15px;
        }

        .nav-list li {
            display: inline-block;
            margin-left: 16px;
            margin-right: 16px;
        }

        .nav-list li a{
            text-decoration: none;
            color: #777;

        }

        .arrow-down {
            width: 0;
            height: 0;
            border-left: 5px solid transparent;
            border-right: 5px solid transparent;
            border-top: 5px solid #777;
            position: absolute;
            top: 97px;
            left: 586px;
        }

        #active {
            color: #4285f4;
            font-weight: bold;
            padding-bottom: 23px;
            padding-right: 10px;
            padding-left: 10px;
            border-bottom: 3px solid #4285f4;
        }

        .scnd-right {
            font-size: 0;
            margin-top: 8px;
            margin-right: 20px;
        }

        .user {
            width: 26px;
            height: 27px;
            padding: 0 8px;
            border: 1px solid #c6c6c6;
            background: #f5f5f5;
            display: inline-block;
        }

        #user-span {
            background: url("https://www.google.com/images/nav_logo242.png") no-repeat;
            background-position: 0 -328px;
            height: 14px;
            width: 14px;
            margin: 5px auto;
        }

        .globe {
            display: inline-block;
            width: 26px;
            height: 27px;
            padding: 0 8px;
            border: 1px solid #c6c6c6;
            background: #eee;
            margin-right: 10px;
        }

        #globe-span {
            background: url("https://www.google.com/images/nav_logo242.png") no-repeat;
            background-position: -45px -328px;
            height: 14px;
            width: 14px;
            margin: 6px auto;
        }

        .settings {
            display: inline-block;
            height: 27px;
            width: 54px;
            padding: 0 8px;
            border: 1px solid #c6c6c6;
            background: #f5f5f5;
        }

        #settings-span {
            background: url("https://www.google.com/images/nav_logo242.png") no-repeat;
            background-position: -42px -259px;
            height: 17px;
            width: 17px;
            margin: 5px auto;
            opacity: 0.667;
        }

        .results {
            margin-left: 174px;
            margin-top: 10px;
        }

        #num-results {
            font-size: 14px;
            color: #00B4CC;
            margin-bottom: 20px;
        }

        .results {
            width: 40%;
            font-family: Oswald;
            color: #9d090d;
        }

        .search-results {
            margin-bottom: 23px;
        }

        .search-results h2, .search-results p {
            margin: 0;
            display: block;
        }

        .search-results h2 a{
            width:80%;
            text-decoration: none;
            color: rgb(26, 13, 171);
            font-weight: normal;
            font-size: 18px;
            color: #003bff;

        }

        .link {
            color: #006621;
            line-height: 16px;
            text-decoration: none;
        }

        .summary {
            color: #545454;
            font-size: 13px;
        }

        .related-searches h3 {
            color: rgb(128, 128, 128);
            font-size: 18px;
            padding-bottom: 0px;
            font-weight: normal;
            padding-bottom: 7px;
            margin-bottom: 0px;
        }

        .related-searches td {
            width: 240px;
            height: 20px;
            font-size: 13px;
        }

        .related-searches td a {
            text-decoration: none;
            color: rgb(26, 13, 171);
        }

        .footer {
            height: 65px;
            padding-top: 18px;
            border-top: 1px solid rgb(235, 235, 235);
            background-color: rgb(242, 242, 242);
        }

        .footer #location {
            height: 8px;
            width: 8px;
            background: rgb(170, 170, 170);
            border-radius: 50%;
            display: inline-block;
            margin-left: 175px;
            margin-right: 5px;
        }

        .footer p {
            display: inline-block;
            color: rgb(170, 170, 170);
            font-size: 13px;
            line-height: 15px;
            margin-top: 0px;
        }

        #location-search {
            color: rgb(51, 51, 51);
        }

        .footer ul {
            margin-bottom: 0px;
            margin-top: 2px;
            margin-left: 110px;
        }

        .footer ul li{
            display: inline-block;

        }

        .footer ul li a {
            text-decoration: none;
            color: rgb(51, 51, 51);
            font-size: 13px;
            margin-bottom: 0px;
            padding-left: 27px;
        }
        .categories_table{
            width: 400px;
            display: inline-flex;
            margin-right: -500px;
            float: right;
        }
        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }
        .numberOfdoc{
            width: 50%;
        }
        .numberOfdoc th{
            border: 0px;
        }
        .submitCategory{
            width: 100px;
            height: 36px;
            border: 1px solid #003bff;
            background: #003bff;
            text-align: center;
            color: #fff;
            border-radius: 5px;
            cursor: pointer;
            font-size: 15px;
            font-family: Oswald;
        }
        .submitCategoryTH{
            border: 0px;
        }

    </style>
    <title>Title</title>
</head>
<body>
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Oswald" />

   <!--<h1><%=QueryValue%> We Are in SearchResult.jsp</h1>!-->
    <div class="container">
        <div class="scnd-bar">
            <form class="wrap" name="myForm" action="${pageContext.request.contextPath}/SearchServletURL" method="post">
                <div class="search">

                    <input type="text" class="searchTerm" placeholder="What are you looking for?" name="searchBox">

                    <select class="FieldsSelect" name="FieldToSelect">
                        <option class="SelectedField" value="None" selected="selected">All Fields</option>
                        <option class="SelectedField" value=<%=LuceneDocConstants.Field_1%>><%=LuceneDocConstants.Field_1%></option>
                        <option class="SelectedField" value=<%=LuceneDocConstants.Field_2%>><%=LuceneDocConstants.Field_2%></option>
                        <option class="SelectedField" value=<%=LuceneDocConstants.Field_2%>><%=LuceneDocConstants.Field_3%></option>
                    </select>

                    <button type="submit" class="searchButton">
                        <i class="fa fa-search"></i>
                    </button>

                </div>
            </form>
        </div>
        <div class="results">

            <table class="categories_table">
                <tr>
                    <th>Category</th>
                </tr>
                <form action="${pageContext.request.contextPath}/SearchServletURL" method="post">
                    <tr>
                        <th>
                            <input type="radio" name="CatToSelect" value="none" checked>No Facets<br>
                        </th>
                    </tr>
                    <tr>
                        <th>
                            <input type="radio" name="CatToSelect" value="estudiantes">estudiantes<br>
                        </th>
                    </tr>
                    <tr>
                        <th>
                            <input type="radio" name="CatToSelect" value="grados">grados<br>
                        </th>
                    </tr>
                    <tr>
                        <th>
                            <input type="radio" name="CatToSelect" value="becas-y-ayudas">becas-y-ayudas<br>
                        </th>
                    </tr>
                    <tr>
                        <th>
                            <input type="radio" name="CatToSelect" value="movilidad-e-intercambio">movilidad-e-intercambio<br>
                        </th>

                    <tr>
                        <th class="submitCategoryTH">
                            <input type="submit" class="submitCategory" value="Submit">
                        </th>
                    </tr>
                </form>
            </table>


            <% for( SearchResultObject document_details : Document_Result ){ %>
                <div class="search-results">
                    <h2><a target="_blank" href=http://<%=document_details.get_Doc_Path()%> class="docName"><%=document_details.get_Doc_Title()%></a></h2>
                    <p><a target="_blank" href=http://<%=document_details.get_Doc_Path()%> class="link"><%=document_details.get_Doc_Path()%></a><p>
                    <p class="summary">Reference site about Lorem Ipsum, giving information on its origins, as well as a random Lipsum generator.</p>
                </div>
            <%}%>

        </div>


        <div class="footer">
            <div id="location"></div><p>Tysons, VA - From your Internet address - <span id="location-search">Use precise location - Learn more</span></p>
            <ul>
                <li><a href="#">Help</a></li>
            </ul>
        </div>
    </div>

</body>
</html>
