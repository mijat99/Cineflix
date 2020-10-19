<%@page import="java.util.ArrayList"%>
<%@page import="beans.Film"%>
<%@page isELIgnored="false"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% ArrayList<Film> filmovi = (ArrayList<Film>) request.getAttribute("filmovi");%>
<html>
    <head>
        <title>Cineflix - Spisak filmova</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/style.css" />
        <link rel="stylesheet" href="assets/css/navbar.css" />
        <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" href="assets/css/spisakFilmova.css" />
        <style>
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
                            <td><h1 class="customh1">Spisak filmova</h1></td>
                            <td align="right" style="padding-right: 36px;"><form action="DodajFilm" method="get"><button type="submit">Dodaj film<i class="zmdi zmdi-arrow-right"></i></button></form>
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
                            <ul class="filmovi">
                                    <%for(Film f : filmovi){
                                        %>
                                        <li class="film">
                                            <div class="filmoviSlika">
                                                <img src="<%= f.getFilmPoster() %>" height="150px" alt=""/>                                        
                                            </div>
                                            <div class="filmoviTekst">
                                                <h2><%= f.getFilmNaziv() %></h2>
                                                <h3>Godina: <%= f.getFilmGodina() %></h3>
                                                <h3>Zanr: <%= f.getFilmZanr() %></h3>
                                                <h3>Rezija: <%= f.getFilmReziser() %></h3>
                                            </div> 
                                            <div class="filmoviDugme">
                                                <form method="post" action="ObrisiFilm">
                                                    <input type="hidden" name="pathSlika" value="<%= f.getFilmPoster() %>"/>
                                                    <input type="hidden" name="filmId" value="<%= f.getFilmId() %>"/>
                                                    <button type="submit">X<i class="zmdi zmdi-arrow-right"></i></button>
                                                </form>
                                            </div>
                                            <div class="filmoviDugme">
                                                <form method="GET" action="IzmeniFilm">
                                                    <input type="hidden" name="filmId" value="<%= f.getFilmId() %>"/>
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
