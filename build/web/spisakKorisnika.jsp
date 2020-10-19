<%@page import="java.util.ArrayList"%>
<%@page import="beans.Korisnik"%>
<%@page isELIgnored="false"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% ArrayList<Korisnik> korisnici = (ArrayList<Korisnik>) request.getAttribute("korisnici");%>
<html>
    <head>
        <title>Cineflix - Spisak korisnika</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/style.css" />
        <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" href="assets/css/navbar.css" />
        <link rel="stylesheet" href="assets/css/spisakKorisnika.css" />
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
        <!-- Content -->
		<div id="content">
                    <table>
                        <tr>
                            <td><h1 class="customh1">Spisak korisnika</h1></td>
                            <td align="right" style="padding-right: 36px;"><form action="Register" method="get"><button type="submit">Dodaj korisnika
						<i class="zmdi zmdi-arrow-right"></i></button></form>
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
                                <table class="korisnici">
                                    <tr>
                                        <th>Username</th>
                                        <th>Role</th>
                                        <th>BioskopId</th>
                                    </tr>
                                    <%for(Korisnik k : korisnici){
                                        %>
                                        <tr>
                                            <td>
                                                <h3><%= k.getKorisnikUsername() %></h3>    
                                            </td>
                                            <td>
                                                <h3><%= k.getKorisnikRole() %></h3>
                                            </td>
                                            <td>
                                                <h3><%= k.getBioskopId() %></h3>
                                            </td> 
                                            <td align="right">
                                                <form method="GET" action="IzmeniKorisnika">
                                                    <input type="hidden" name="korisnikId" value="<%= k.getKorisnikId() %>"/>
                                                    <button type="submit">Izmeni<i class="zmdi zmdi-arrow-right"></i></button>
                                                </form>
                                            </td>
                                            <td align="right">
                                                <form method="post" action="ObrisiKorisnika">
                                                    <input type="hidden" name="korisnikId" value="<%= k.getKorisnikId() %>"/>
                                                    <button type="submit">X<i class="zmdi zmdi-arrow-right"></i></button>
                                                </form>
                                            </td>
                                        </tr>
                                        <%
                                    }%>
                                </table>                         
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
