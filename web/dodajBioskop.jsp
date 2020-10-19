<%@page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cineflix - Dodaj bioskop</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/style.css" />
        <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" href="assets/css/navbar.css" />
        <link rel="stylesheet" href="assets/css/login.css" />
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
                        <h2>Novi bioskop</h2>
                        <div class="omotac">
                            <%
                                String error=(String)request.getAttribute("errorMsg");
                                if(error!= null && !(error.trim().equals("")))
                                {%>
                                    <h2 style="color: red"><%=error%></h2>
                                <%}%>
                                <div class="wrapper">
                            <form method="post" action="DodajBioskop" enctype="multipart/form-data">                                
                                <label for="bioskopNaziv">Naziv</label>
                                <input type="text" name="bioskopNaziv" class="form-control" required="required" minlength="2" maxlength="50" >
                                  
                                <label for="bioskopAdresa">Adresa</label>
                                <input type="text" name="bioskopAdresa" class="form-control" required="required" minlength="2" maxlength="50" >
                                
                                <label for="bioskopTelefon">Telefon</label>
                                <input type="text" name="bioskopTelefon" class="form-control" required="required" minlength="2" maxlength="50" >
                                                                
                                <label for="bioskopCena2D">2D karta (RSD)</label>
                                <input type="number" name="bioskopCena2D" class="form-control" required="required" min="1">
                                
                                <label for="bioskopCena3D">3D karta (RSD)</label>
                                <input type="number" name="bioskopCena3D" class="form-control" required="required" min="1">
                                
                                <label for="bioskopBanner">Poster</label>
                                <input type="file" name="bioskopBanner" class="form-control" required="required" >
                                
                                <button type="submit">Dodaj<i class="zmdi zmdi-arrow-right"></i></button>
                            </form>        
                                </div>
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
                        $("body").height($(window).height());
                    }
                </script>
    </body>
</html>
