<%@page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cineflix - Dodaj film</title>
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
                        <h2>Novi film</h2>
                        <div class="wrapper">
                            <%
                                String error=(String)request.getAttribute("errorMsg");
                                if(error!= null && !(error.trim().equals("")))
                                {%>
                                    <h2 style="color: red"><%=error%></h2>
                                <%}%>
                            <form method="post" action="DodajFilm" enctype="multipart/form-data">                                
                                <label for="filmNaziv">Naziv</label>
                                <input type="text" name="filmNaziv" required="required" minlength="2" maxlength="50" class="form-control">
                                                                
                                <label for="filmGodina">Godina premijere</label>
                                <input type="text" name="filmGodina" maxlength="4" required="required" minlength="4" class="form-control">
                                
                                <label for="filmZanr">Zanrovi</label>
                                <input type="text" name="filmZanr" required="required" minlength="3" maxlendth="50" class="form-control">
                                
                                <label for="filmReziser">Rezija</label>
                                <input type="text" name="filmReziser" required="required" minlength="3" maxlendth="50" class="form-control">
                                
                                <label for="filmPoster">Poster</label>
                                <input type="file" name="filePoster" required="required" class="form-control" >
                                
                                <button type="submit">Dodaj<i class="zmdi zmdi-arrow-right"></i></button>
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
                        $("body").height($(window).height());
                    }
                </script>
    </body>
</html>
