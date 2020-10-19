<%@page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="model" class="viewModels.dodajProjekcijuViewModel" scope="page" >
    <jsp:setProperty name="model" property="projekcija" value="${ viewModel.projekcija }" />
    <jsp:setProperty name="model" property="sale" value="${ viewModel.sale }" />
    <jsp:setProperty name="model" property="filmovi" value="${ viewModel.filmovi }" />
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <title>Cineflix - Dodaj salu</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/style.css" />
        <link rel="stylesheet" href="assets/css/navbar.css" />
        <link rel="stylesheet" href="assets/css/login.css" />
        <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
        <style>
            h2{color:black;}
        </style>
    </head>
    <body>
         <!-- Navigation -->
            <jsp:include page="/navbar.jsp">
                <jsp:param name="title" value="Main title" />
            </jsp:include>
        <%@page import="java.sql.*"%>
            <!-- Content -->
		<div id="content">
                    <div class="sredina">
                        <h2>Nova sala</h2>
                        <div class="wrapper">
                            <%
                                String error=(String)request.getAttribute("errorMsg");
                                if(error!= null && !(error.trim().equals("")))
                                {%>
                                    <h2 style="color: red"><%=error%></h2>
                                <%}%>
                            <form method="post" action="DodajSalu">
                                <input type="hidden" name="bioskopId" class="form-control" value="<%= request.getParameter("bioskopId") %>">
                                
                                <label for="salaNaziv">Naziv</label>
                                <input type="text" name="salaNaziv" class="form-control">
                                                                
                                <label for="salaRedovi">Broj redova</label>
                                <input type="number" name="salaRedovi" class="form-control">
                                
                                <label for="salaKolone">Broj kolona</label>
                                <input type="number" name="salaKolone" class="form-control">
                                
                                <button type="submit">Dodaj<i class="zmdi zmdi-arrow-right"></i></button>
                            </form>   
                        </div>
                    </div>
                    
                </div>

            <!-- Scripts -->
		<script src="assets/js/jquery.min.js"></script>
		<script src="assets/js/browser.min.js"></script>
		<script src="assets/js/breakpoints.min.js"></script>
		<script src="assets/js/util.js"></script>
		<script src="assets/js/main.js"></script>
                <script>
                    if($("body").height()<$(window).height())
                    {
                        $("body").height($(window).height());
                    }
                </script>
    </body>
</html>
