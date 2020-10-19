<%@page import="java.util.ArrayList"%>
<%@page import="beans.Bioskop"%>
<%@page isELIgnored="false"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% ArrayList<Bioskop> bioskopi = (ArrayList<Bioskop>) request.getAttribute("bioskopi");%>
<html>
    <head>
        <title>Cineflix - Spisak bioskopa</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/style.css" />
        <link rel="stylesheet" href="assets/css/navbar.css" />
        <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" href="assets/css/spisakBioskopa.css" />
        <style>
            h1{color:black;}
            h2{color:black;}
            h3{color:black;}
            p{color:black;}
        </style>
    </head>
    <body>
        <!-- Navigation -->
            <jsp:include page="/navbar.jsp">
                <jsp:param name="title" value="Main title" />
            </jsp:include>
        <!-- Content -->
		<div id="content">
                    <table>
                        <tr>
                            <td><h1 class="customh1">Spisak bioskopa</h1></td>
                            <td align="right" style="padding-right: 36px;"><form action="DodajBioskop" method="get"><button type="submit">Dodaj bioskop<i class="zmdi zmdi-arrow-right"></i></button></form>
                        </tr>
                    </table>
                    
                    <div class="container">
                        <div class="levi">
                            <%
                                String error=(String)request.getAttribute("errorMsg");
                                if(error!= null && !(error.trim().equals("")))
                                {%>
                                    <h2 style="color: red"><%=error%></h2>
                                <%}%>
                            <ul class="bioskopi">
                                    <%for(Bioskop b : bioskopi){
                                        %>
                                        <li class="bioskop">
                                            <div class="bioskopiSlika">
                                                <img src="<%= b.getBioskopBanner() %>" height="170px" alt=""/>                                        
                                            </div>
                                            <div class="bioskopiTekst">
                                                <h2><%= b.getBioskopNaziv() %></h2>
                                                <h3>Adresa: <%= b.getBioskopAdresa() %></h3>
                                                <h3>Telefon: <%= b.getBioskopTelefon()%></h3>
                                                <p>Cena 2D karte - <%= b.getBioskopCena2D() %></p>
                                                <p>Cena 3D karte - <%= b.getBioskopCena3D() %></p>
                                            </div> 
                                            <div class="bioskopiDugme">
                                                <form method="post" action="ObrisiBioskop">
                                                    <input type="hidden" name="bioskopId" value="<%= b.getBioskopId() %>" >
                                                    <input type="hidden" name="pathSlika" value="<%= b.getBioskopBanner() %>">
                                                    <button type="submit">X<i class="zmdi zmdi-arrow-right"></i></button>
                                                </form>
                                            </div>
                                            <div class="bioskopiDugme">
                                                <form method="GET" action="IzmeniBioskop">
                                                    <input type="hidden" name="bioskopId" value="<%= b.getBioskopId() %>"/>
                                                    <button type="submit">Izmeni<i class="zmdi zmdi-arrow-right"></i></button>
                                                </form>
                                            </div>                                     
                                        </li>
                                        <%
                                    }%>
                            
                            </ul>                                
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
