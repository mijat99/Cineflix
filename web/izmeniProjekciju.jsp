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
        <title>Cineflix - Izmena projekcije</title>
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
                        <h2>Izmena projekcije</h2>
                        <div class="wrapper">
                            <%
                                String error=(String)request.getAttribute("errorMsg");
                                if(error!= null && !(error.trim().equals("")))
                                {%>
                                    <h2 style="color: red"><%=error%></h2>
                                <%}%>
                            <form method="post" action="IzmeniProjekciju">                              
                                <label for="projekcijaId">Projekcija ID</label>
                                <input type="text" readonly disabled value="<%= model.getProjekcija().getProjekcijaId() %>" class="form-control">
                                <input type="hidden" name="projekcijaId" value="<%= model.getProjekcija().getProjekcijaId() %>" class="form-control">
                                
                                <label for="salaId">Sala</label>
                                <select name="salaId" class="form-control">
                                <%
                                    for(int i=0; i<model.getSale().size();i++){%>
                                    <option value="<%= model.getSale().get(i).getSalaId() %>"
                                            <% if(model.getSale().get(i).getSalaId() == model.getProjekcija().getSala().getSalaId()){ %> selected <% }%>>
                                            <%= model.getSale().get(i).getSalaNaziv() %></option>
                                    <%}
                                %>    
                                </select>
                                
                                <label for="filmId">Film</label>
                                <select name="filmId" class="form-control">
                                <%
                                    for(int i=0; i<model.getFilmovi().size();i++){%>
                                    <option value="<%= model.getFilmovi().get(i).getFilmId() %>"
                                            <% if(model.getFilmovi().get(i).getFilmId() == model.getProjekcija().getFilmId()){ %> selected <% }%>>
                                            <%= model.getFilmovi().get(i).getFilmNaziv() %></option>
                                    <%}
                                %>    
                                </select>
                                <label for="projekcijaJe3D">Tip projekcije</label>
                                <select name="projekcijaJe3D" class="form-control">
                                    <option value="1" <% if(model.getProjekcija().projekcijaJe3D()){%>selected<%} %>>3D</option>
                                    <option value="0" <% if(!model.getProjekcija().projekcijaJe3D()){%>selected<%} %>>2D</option>
                                </select>
                                
                                <label for="datum">Datum</label>
                                <input type="date" name="datum" value="<%= model.getProjekcija().getProjekcijaDatum() %>" class="form-control">
                                
                                
                                <label for="vreme">Vreme</label>
                                <input type="time" name="vreme" value="<%= model.getProjekcija().getProjekcijaVreme() %>" class="form-control">
                                
                                <button type="submit">Izmeni<i class="zmdi zmdi-arrow-right"></i></button>
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
    </body>
</html>
