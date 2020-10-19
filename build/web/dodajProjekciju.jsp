<%@page import="beans.Korisnik"%>
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
        <title>Cineflix - Dodaj projekciju</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/style.css" />
        <link rel="stylesheet" href="assets/css/navbar.css" />
        <link rel="stylesheet" href="assets/css/login.css" />
        <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
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
                        <h2>Nova projekcija</h2>
                        <div class="wrapper">
                            <%
                                String error=(String)request.getAttribute("errorMsg");
                                if(error!= null && !(error.trim().equals("")))
                                {%>
                                    <h2 style="color: red"><%=error%></h2>
                                <%}%>
                            <form method="post" action="DodajProjekciju">                                
                                <label for="salaId">Sala</label>
                                <select name="salaId" class="form-control" >
                                <%
                                    for(int i=0; i<model.getSale().size();i++){%>
                                    <option value="<%= model.getSale().get(i).getSalaId() %>"><%= model.getSale().get(i).getSalaNaziv() %></option>
                                    <%}
                                %>    
                                </select>
                                
                                <label for="filmId">Film</label>
                                <select name="filmId" class="form-control">
                                <%
                                    for(int i=0; i<model.getFilmovi().size();i++){%>
                                    <option value="<%= model.getFilmovi().get(i).getFilmId() %>"><%= model.getFilmovi().get(i).getFilmNaziv() %></option>
                                    <%}
                                %>    
                                </select>
                                <label for="projekcijaJe3D">Tip projekcije</label>
                                <select name="projekcijaJe3D" class="form-control">
                                    <option value="1">3D</option>
                                    <option value="0">2D</option>
                                </select>
                                
                                <label for="datum">Datum</label>
                                <input type="date" id="datum" name="datum" class="form-control">
                                
                                
                                <label for="vreme">Vreme</label>
                                <input type="time" id="vreme" name="vreme" class="form-control">
                                
                                <button type="submit">Potvrdi<i class="zmdi zmdi-arrow-right"></i></button>
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
                        $("body").height($(window).height())
                    }
                </script>
                <script>
                    let today = new Date(),
                    day = today.getDate(),
                    month = today.getMonth()+1, //January is 0
                    year = today.getFullYear();
                         if(day<10){
                                day='0'+day
                            } 
                        if(month<10){
                            month='0'+month
                        }
                        today = year+'-'+month+'-'+day;

                        document.getElementById("datum").setAttribute("min", today);
                        document.getElementById("datum").setAttribute("value", today);
                        document.getElementById("vreme").value = "20:00:00";
                </script>
    </body>
</html>
