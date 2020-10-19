<%@page import="beans.Bioskop"%>
<%@page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="vm" class="viewModels.IzborBioskopaViewModel">
<jsp:setProperty name="vm" property="film" value="${viewModel.getFilm()}"/>
<jsp:setProperty name="vm" property="bioskopi" value="${viewModel.getBioskopi()}"/>
</jsp:useBean>
<!-- value="  " -->
<!DOCTYPE html>
<html>
    <head>
        <title>Cineflix - Bioskopi</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/style.css" />
        <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" href="assets/css/navbar.css" />
        <link rel="stylesheet" href="assets/css/IzborBioskopa.css" />
        <style>
            h1{color:black;}
            h2{color:black;}
            h3{color:black;}
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
                    <h1 class="customh1"><%= vm.getFilm().getFilmNaziv() %></h1>
                    <div class="container">
                        <div class="levi">
                            <ul class="bioskopi">
                                <%
                                    for(Bioskop b : vm.getBioskopi()){
                                        %>
                                        <li class="bioskop">
                                            <div class="bioskopiSlika">
                                                <img src="<%= b.getBioskopBanner() %>" height="150px" alt=""/>                                        
                                            </div>
                                            <div class="bioskopiTekst">
                                                <h2><%= b.getBioskopNaziv() %></h2>
                                                <h3>Adresa: <%= b.getBioskopAdresa() %></h3>
                                                <h3>Telefon<%= b.getBioskopTelefon()%></h3>
                                            </div>  
                                            <div class="bioskopiDugme">
                                                <form method="get" action="IzborProjekcije">
                                                    <input type="hidden" name="bioskopId" value="<%= b.getBioskopId() %>"/>
                                                    <input type="hidden" name="filmId" value="<%= vm.getFilm().getFilmId() %>"/>
                                                    <button type="submit">Projekcije<i class="zmdi zmdi-arrow-right"></i></button>
                                                </form>
                                            </div>
                                        </li>
                                        <%
                                    }
                                
                                %>
                            
                            </ul>                                
                        </div>
                        <div class="desni">
                            <h1 class="customh1"><%= vm.getFilm().getFilmNaziv() %></h1>
                            <img src="<%= vm.getFilm().getFilmPoster() %>" alt=""/>
                            <h2>Rezija: <%= vm.getFilm().getFilmReziser() %></h2>
                            <h2>Godina: <%= vm.getFilm().getFilmGodina() %></h2>
                            <h2>Zanr: <%= vm.getFilm().getFilmZanr() %></h2>
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
                        $("body").height($(window).height())
                    }
                </script>
    </body>
</html>
