<%@page import="java.util.ArrayList"%>
<%@page import="beans.Sala"%>
<%@page isELIgnored="false"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% ArrayList<Sala> sale = (ArrayList<Sala>) request.getAttribute("sale");%>
<html>
    <head>
        <title>Cineflix - Spisak sala</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/style.css" />
        <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" href="assets/css/navbar.css" />
        <link rel="stylesheet" href="assets/css/spisakSala.css" />
        <style>
            h1{color:black;}
            h2{color:black;}
            td{color:black;}
        </style>
    </head>
    <body>
        <!-- Navigation -->
            <jsp:include page="/navbar.jsp">
                <jsp:param name="title" value="Main title" />
            </jsp:include>
        <!-- Content -->
		<div id="content">
                    <h1 class="customh1">Spisak sala</h1>                    
                    <div class="container">
                        <div class="levi">
                             <section>
                                <% 
                                    String msg= (String)request.getAttribute("errorMsg"); 
                                    if(msg!= null && !msg.isEmpty()){
                                        %><h1><%= msg %></h1> <%}%>
                            </section>
                            <ul class="bioskopi">
                                    <%for(int i=0;i<sale.size();i++){
                                        %>
                                        <li class="bioskop">
                                            <div>
                                                <table width="100%">
                                                    <tr>
                                                        <td><h2><%= sale.get(i).getBioskop().getBioskopNaziv() %></h2></td>
                                                        <td align="right" style="padding-right: 36px;">
                                                            <form action="DodajSalu" method="get">
                                                                <input type="hidden" name="bioskopId" value="<%= sale.get(i).getBioskopId() %>">
                                                                <button type="submit">Dodaj salu<i class="zmdi zmdi-arrow-right"></i></button>                                                                
                                                            </form>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <table class="sale" >
                                                    <tr>
                                                        <td><%= sale.get(i).getSalaNaziv() %></td>
                                                        <td> Redovi - <%= sale.get(i).getSalaRedovi() %></td>
                                                        <td> Kolone - <%= sale.get(i).getSalaKolone() %></td>
                                                        <td class="dugmici">
                                                            <form method="get" action="IzmeniSalu"> 
                                                                <input type="hidden" value="<%= sale.get(i).getSalaId() %>" name="salaId">
                                                                <button type="submit">Izmeni<i class="zmdi zmdi-arrow-right"></i></button>
                                                            </form>
                                                            <form method="post" action="ObrisiSalu"> 
                                                                <input type="hidden" value="<%= sale.get(i).getSalaId() %>" name="salaId">
                                                                <button type="submit">X<i class="zmdi zmdi-arrow-right"></i></button>
                                                            </form>
                                                        </td>
                                                    </tr>
                                                <%
                                                    while(i<sale.size()-1 && sale.get(i).getBioskopId()==sale.get(i+1).getBioskopId()){%>
                                                        <tr>
                                                            <td><%= sale.get(i+1).getSalaNaziv() %></td>
                                                            <td> Redovi - <%= sale.get(i+1).getSalaRedovi() %></td>
                                                            <td> Kolone - <%= sale.get(i+1).getSalaKolone() %></td>
                                                            <td class="dugmici">
                                                                <form method="get" action="IzmeniSalu"> 
                                                                    <input type="hidden" value="<%= sale.get(i+1).getSalaId() %>" name="salaId">
                                                                    <button type="submit">Izmeni<i class="zmdi zmdi-arrow-right"></i></button>
                                                                </form>
                                                                <form method="post" action="ObrisiSalu"> 
                                                                    <input type="hidden" value="<%= sale.get(i+1).getSalaId() %>" name="salaId">
                                                                    <button type="submit">X<i class="zmdi zmdi-arrow-right"></i></button>
                                                                </form>
                                                            </td>
                                                        </tr>                                                        
                                                        <%i++;}%>
                                                </table>
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
