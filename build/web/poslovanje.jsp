<%@page import="beans.Bioskop"%>
<%@page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="vm" class="viewModels.poslovanjeViewModel">
<jsp:setProperty name="vm" property="bioskop" value="${viewModel.bioskop}"/> 
<jsp:setProperty name="vm" property="brojRezervacija" value="${viewModel.brojRezervacija}"/> 
<jsp:setProperty name="vm" property="ukupnoRezervacije" value="${viewModel.ukupnoRezervacije}"/>
<jsp:setProperty name="vm" property="filmovi" value="${viewModel.filmovi}"/>  
<jsp:setProperty name="vm" property="sumaPoFilmu" value="${viewModel.sumaPoFilmu}"/>  
<jsp:setProperty name="vm" property="brojProjekcijaPoFilmu" value="${viewModel.brojProjekcijaPoFilmu}"/>  
</jsp:useBean>
<!-- value="  " -->
<!DOCTYPE html>
<html>
    <head>
        <title>Cineflix - Poslovanje</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/style.css" />
        <link rel="stylesheet" href="assets/css/navbar.css" />
        <link rel="stylesheet" href="assets/css/poslovanje.css" />
        <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
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
                    <h1 class="customh1">Poslovanje bioskopa - <%= vm.getBioskop().getBioskopNaziv() %></h1>
                    <div class="container">
                        <div class="levi">
                            <h1>Ukupan promet od rezervacija: <strong class="zeleno"><%= vm.getUkupnoRezervacije() %> RSD</strong></h1>
                            <p>Ukupan broj rezervacija: <strong class="zeleno"><%= vm.getBrojRezervacija() %></strong></p>
                            <%
                                if(vm.getFilmovi()!=null && vm.getFilmovi().size()>0){
                                    %><h2>Poslovanje po filmovima:</h2><ul class="filmovi"><%
                                    for(int i=0;i<vm.getFilmovi().size();i++){%>
                                         <li class="film">
                                            <div class="filmoviSlika">
                                                <img src="<%= vm.getFilmovi().get(i).getFilmPoster() %>" height="150px" alt=""/>                                        
                                            </div>
                                            <div class="filmoviText">
                                                <h2><%= vm.getFilmovi().get(i).getFilmNaziv() %></h2>
                                                <h3>Godina: <%= vm.getFilmovi().get(i).getFilmGodina() %></h3>
                                                <h3>Zanrovi: <%= vm.getFilmovi().get(i).getFilmZanr() %></h3>
                                                <h3>Rezija <%= vm.getFilmovi().get(i).getFilmReziser() %></h3>
                                            </div>  
                                            <div class="filmoviDugme">
                                                <h2>Promet: <span class="zeleno"><%= vm.getSumaPoFilmu().get(i) %> RSD</span></h2>
                                                <h3>Broj rezervacija: <span class="zeleno"><%= vm.getBrojProjekcijaPoFilmu().get(i) %></span></h3>
                                            </div>
                                        </li>
                                    <%}%></ul><% 
                                }%>    
                            
                               
                            
                        </div>
                        <div class="desni">
                            <img src="<%= vm.getBioskop().getBioskopBanner() %>" alt=""/>
                            <h2>Adresa: <%= vm.getBioskop().getBioskopAdresa() %></h2>
                            <h2>Broj telefona: <%= vm.getBioskop().getBioskopTelefon() %></h2>
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
