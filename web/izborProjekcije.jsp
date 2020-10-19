<%@page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="vm" class="viewModels.projekcijeViewModel">
    <jsp:setProperty name="vm" property="bioskop" value="${viewModel.bioskop}"/> 
    <jsp:setProperty name="vm" property="film" value="${viewModel.film}"/> 
    <jsp:setProperty name="vm" property="projekcije" value="${viewModel.projekcije}"/> 
</jsp:useBean>
<!-- value="  " -->
<!DOCTYPE html>
<html>
    <head>
        <title>Cineflix - Projekcije</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/style.css" />
        <link rel="stylesheet" href="assets/css/navbar.css" />
        <link rel="stylesheet" href="assets/css/IzborProjekcije.css" />
        <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
        <style>
            h1{color:black;}
            h2{color:black;}
        </style>
    </head>
    <body>
        <jsp:include page="/navbar.jsp">
                <jsp:param name="title" value="Main title" />
            </jsp:include>
        <%@page import="java.sql.*"%>
            <!-- Content -->
		<div id="content">
                    <h1 class="customh1">Projekcije</h1>
                    <div class="container">
                        <div class="levi">
                            <ul class="projekcije">
                                <%
                                    for(int i=0; i<vm.getProjekcije().size();i++)
                                    {
                                %>
                                        <li>
                                            <div class="datum">
                                                    <h2 class="datumHeader"><%= vm.getProjekcije().get(i).getProjekcijaDatum() %></h2>
                                            </div>
                                            <div clas="dugmici">
                                                <div class="form">
                                                    <form action="IzborSedista" method="get">
                                                        <input type="hidden" name="projekcijaId"  value="<%= vm.getProjekcije().get(i).getProjekcijaId() %>"/>
                                                        <%
                                                            String projekcija3D="2D";
                                                            if(vm.getProjekcije().get(i).projekcijaJe3D())
                                                                projekcija3D="3D";
                                                        %>
                                                        <button type="submit"><span><%= vm.getProjekcije().get(i).getSala().getSalaNaziv()%> - <%= projekcija3D %>
                                                                <br/><%= vm.getProjekcije().get(i).getProjekcijaVreme()%></span></button>                                        
                                                    </form>
                                                </div>
                                            <%
                                            while(i<vm.getProjekcije().size()-1)
                                            {
                                                if(vm.getProjekcije().get(i).getProjekcijaDatum().toString().equals(vm.getProjekcije().get(i+1).getProjekcijaDatum().toString()))
                                                {%>
                                                    <div class="form">
                                                        <form action="IzborSedista" method="get">
                                                            <input type="hidden" name="projekcijaId"  value="<%= vm.getProjekcije().get(i+1).getProjekcijaId() %>"/>
                                                            <%
                                                            projekcija3D="2D";
                                                            if(vm.getProjekcije().get(i+1).projekcijaJe3D())
                                                                projekcija3D="3D";
                                                            %>
                                                            <button type="submit"><span><%= vm.getProjekcije().get(i+1).getSala().getSalaNaziv()%> - <%= projekcija3D %>
                                                                    <br/><%= vm.getProjekcije().get(i+1).getProjekcijaVreme()%></span></button>                                        
                                                        </form>
                                                    </div>                                                
                                                <%i++;
                                                }
                                                else{
                                                    break;
                                                }
                                            }
                                            %>
                                            </div>
                                        </li>
                                <%  }%>
                            </ul>
                        </div>
                        <div class="desni">
                            <h1 class="customh1"><%= vm.getBioskop().getBioskopNaziv() %></h1>
                            <img src="<%= vm.getBioskop().getBioskopBanner() %>" alt=""/>
                            <h2>Adresa: <%= vm.getBioskop().getBioskopAdresa() %></h2>
                            <h2>Broj telefona: <%= vm.getBioskop().getBioskopTelefon() %></h2>
                            <br/>
                            <h1 class="customh1"><%= vm.getFilm().getFilmNaziv() %></h1>
                            <img src="<%= vm.getFilm().getFilmPoster() %>" alt=""/>
                            <h2>Reziser <%= vm.getFilm().getFilmReziser() %></h2>
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
