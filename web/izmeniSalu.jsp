<%@page import="java.util.ArrayList"%>
<%@page import="beans.*"%>
<%@page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cineflix - Izmena sale</title>
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
        <% Sala sala=(Sala)request.getAttribute("sala"); %>
            <!-- Content -->
		<div id="content">
                    <div class="sredina">
                        <h2>Izmena sale</h2>
                        <div class="wrapper">
                            <%
                                String error=(String)request.getAttribute("errorMsg");
                                if(error!= null && !(error.trim().equals("")))
                                {%>
                                    <h2 style="color: red"><%=error%></h2>
                                <%}%>
                            <form method="post" action="IzmeniSalu">                              
                                <label for="salaId">Sala ID</label>
                                <input type="text" readonly disabled value="<%= sala.getSalaId() %>" class="form-control">
                                <input type="hidden" name="salaId" value="<%= sala.getSalaId() %>">
                                <input type="hidden" name="bioskopId" value="<%= sala.getBioskopId() %>">
                                
                                <label for="salaNaziv">Naziv</label>
                                <input type="text" name="salaNaziv" value="<%= sala.getSalaNaziv() %>" class="form-control">
                                
                                <label for="salaRedovi">Broj redova</label>
                                <input type="number" name="salaRedovi" value="<%= sala.getSalaRedovi() %>" class="form-control">
                                
                                
                                <label for="salaKolone">Broj kolona</label>
                                <input type="number" name="salaKolone" value="<%= sala.getSalaKolone() %>" class="form-control">
                                
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
