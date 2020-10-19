<%@page import="beans.Film"%>
<%@page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cineflix - Izmena filma</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/style.css" />
        <link rel="stylesheet" href="assets/css/navbar.css" />
        <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" href="assets/css/login.css" />
        <link rel="stylesheet" href="assets/css/izmenaSaSlikom.css"
    </head>
    <% Film f=(Film)request.getAttribute("film"); %>
    <body>
            <!-- Navigation -->
            <jsp:include page="/navbar.jsp">
                <jsp:param name="title" value="Main title" />
            </jsp:include>
        <%@page import="java.sql.*"%>
            <!-- Content -->
		<div id="content">
                    <div class="sredina">
                        <h2>Izmena filma</h2>
                        <div class="omotac">
                            <%
                                String error=(String)request.getAttribute("errorMsg");
                                if(error!= null && !(error.trim().equals("")))
                                {%>
                                    <h2 style="color: red"><%=error%></h2>
                                <%}%>
                            <div class="levi">
                                <div class="wrapper">
                                <form method="post" action="IzmeniFilm" enctype="multipart/form-data">  
                                    <input type="hidden" name="filmId" value="<%= f.getFilmId() %>">
                                    <label for="filmNaziv">Naziv</label>
                                    <input type="text" name="filmNaziv" value="<%= f.getFilmNaziv() %>" required="required" minlength="2" maxlength="50" class="form-control">

                                    <label for="filmGodina">Godina premijere</label>
                                    <input type="text" name="filmGodina" value="<%= f.getFilmGodina() %>" maxlength="4" required="required" minlength="4" class="form-control">

                                    <label for="filmZanr">Zanrovi</label>
                                    <input type="text" name="filmZanr" value="<%= f.getFilmZanr() %>" required="required" minlength="3" maxlendth="50" class="form-control">

                                    <label for="filmReziser">Rezija</label>
                                    <input type="text" name="filmReziser" value="<%= f.getFilmReziser() %>" required="required" minlength="3" maxlendth="50" class="form-control">

                                    <label for="filmPoster">Poster</label>
                                    <input type="file" id="img" name="filePoster" class="form-control">
                                    <input type="hidden" name="stariFilePath" value="<%= f.getFilmPoster() %>" >

                                    <button type="submit">Izmeni<i class="zmdi zmdi-arrow-right"></i></button>
                                </form>   
                                </div>
                            </div>
                            <div class="desni">
                                <img src="<%= f.getFilmPoster() %>" id="prikazSlike" alt="" width="100%">
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
