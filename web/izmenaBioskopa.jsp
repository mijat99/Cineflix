<%@page import="beans.Bioskop"%>
<%@page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cineflix - Izmena bioskopa</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/style.css" />
        <link rel="stylesheet" href="assets/css/navbar.css" />
        <link rel="stylesheet" href="assets/css/login.css" />
        <link rel="stylesheet" href="assets/css/izmenaSaSlikom.css">
        <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
    </head>
    <% Bioskop b=(Bioskop)request.getAttribute("bioskop"); %>
    <body>
        <!-- Navigation -->
            <jsp:include page="/navbar.jsp">
                <jsp:param name="title" value="Main title" />
            </jsp:include>
        <%@page import="java.sql.*"%>
            <!-- Content -->
		<div id="content">
                    <div class="sredina">
                        <h2>Izmena bioskopa</h2>
                        <div class="omotac">
                            <%
                                String error=(String)request.getAttribute("errorMsg");
                                if(error!= null && !(error.trim().equals("")))
                                {%>
                                    <h2 style="color: red"><%=error%></h2>
                                <%}%>
                            <div class="levi">
                                <div class="wrapper">
                                <form method="post" action="IzmeniBioskop" enctype="multipart/form-data">  
                                    <input type="hidden" name="bioskopId" value="<%= b.getBioskopId() %>" class="form-control">
                                    
                                    <label for="bioskopNaziv">Naziv</label>
                                    <input type="text" name="bioskopNaziv" value="<%= b.getBioskopNaziv() %>" required="required" minlength="2" maxlength="50" class="form-control" >
                                    
                                    <label for="bioskopAdresa">Adresa</label>
                                    <input type="text" name="bioskopAdresa" value="<%= b.getBioskopAdresa() %>" required="required" minlength="2" maxlength="50" class="form-control" >
                                    
                                    <label for="bioskopTelefon">Telefon</label>
                                    <input type="text" name="bioskopTelefon" value="<%= b.getBioskopTelefon() %>" required="required" minlength="2" maxlength="50" class="form-control" >
                                    
                                    <label for="bioskopCena2D">2D karta (RSD)</label>
                                    <input type="number" name="bioskopCena2D" value="<%= b.getBioskopCena2D() %>"  required="required" min="1" class="form-control">

                                    <label for="bioskopCena3D">3D karta (RSD)</label>
                                    <input type="number" name="bioskopCena3D" value="<%= b.getBioskopCena3D() %>"  required="required" min="1" class="form-control">

                                    <label for="bioskopBanner">Banner</label>
                                    <input type="file" id="img" name="bioskopBanner">
                                    <input type="hidden" name="stariFilePath" value="<%= b.getBioskopBanner() %>" class="form-control" >

                                    <button type="submit">Izmeni<i class="zmdi zmdi-arrow-right"></i></button>
                                </form>  
                                </div>
                            </div>
                            <div class="desni">
                                <img src="<%= b.getBioskopBanner() %>" id="prikazSlike" alt="" width="100%">
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
                <script>
                    function readURL(input) 
                    {
                        if (input.files && input.files[0]) {
                            var reader = new FileReader();

                            reader.onload = function (e) {
                                $('#prikazSlike').attr('src', e.target.result);
                            }

                            reader.readAsDataURL(input.files[0]);
                        }
                    }

                    $("#img").change(function(){
                        readURL(this);
                    });
                </script>
    </body>
</html>
