<%--
  Author: Rshad Zhran
  Github Account : https://github.com/rshad
  LinkedIn Account : https://www.linkedin.com/in/rshad-zhran-b65b5012a/

  To change this template use File | Settings | File Templates.
--%>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="I_Recovery.LuceneDocConstants"%>

<!doctype html>
<html lang="en">
  <head>
    <title>Hello, world!</title>
    <style>
        @import url(https://fonts.googleapis.com/css?family=Open+Sans);

        body{
            background: #f2f2f2;
        }

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
            height: 20px;
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
            top: 50%;
            left: 45%;
            transform: translate(-50%, -50%);
        }
    </style>

  </head>
  <body>
  <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Oswald" />

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

  </body>
</html>

