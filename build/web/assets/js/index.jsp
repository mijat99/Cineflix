<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Cineflix</title>
     <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">

    <link href="assets/css/modern-business.css" rel="stylesheet">
    
    <link rel="stylesheet" href="assets/css/style.css">
    
    <link rel="stylesheet" href="assets/css/kartice.css" />
    
    <link rel="stylesheet" href="assets/css/navbar.css" />

</head>

<body>
  <!-- Navigation -->
            <jsp:include page="/navbar.jsp">
                <jsp:param name="title" value="Main title" />
            </jsp:include>

  <header>
    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
      <div class="carousel-inner" role="listbox">
        <!-- Slide One - Set the background image for this slide in the line below -->
        <div class="carousel-item active" style="background-image: url('https://images.unsplash.com/photo-1517604931442-7e0c8ed2963c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80')">
        </div>        
      </div>
    </div>
  </header>

  <!-- Page Content -->
  <div class="container">
        <br>
    <h1 class="my-4">Izdvajamo</h1>
        <br>
    <section class="sadrzaj">
                    <%
                        try{
                            String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                            String user="root";
                            String pass="";
                            Class.forName("com.mysql.jdbc.Driver");
        
                            Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                            Statement statement=conn.createStatement();
                            String upit="SELECT * FROM film ORDER BY filmGodina DESC, filmNaziv ASC";
                            ResultSet rezultat=null;
                            try{
                                rezultat=statement.executeQuery(upit);
                            }
                            catch(SQLException sqle){
                                %><h1>Greska u izvrsavanju upita!<br/> <%= sqle %></h1><%
                            }
                            if(rezultat!=null){
                                int i=0;
                                %>
                                <div class="kartice"><%
                                while(rezultat.next())
                                {
                                    if(i<3){                                        
                                    %><div class="kartica" id="<%= rezultat.getString("filmId") %>" >
                                        <h2><%= rezultat.getString("filmNaziv") %></h2>
                                        <p>Zanrovi: <%= rezultat.getString("filmZanr")%></p>
                                        <p>Godina: <%= rezultat.getString("filmGodina")%> </p>
                                        <img src="<%= rezultat.getString("filmPoster") %>" alt=""/>
                                    </div><%
                                    }
                                    i++;
                                }   
                                %></div><%
                            }
                        }
                        catch(ClassNotFoundException cnfe){ %><h1>Greska u povezivanju klasa nije pronadjena!<br/> <%= cnfe %></h1><% }
                        catch(SQLException e){ %><h1>Greska u povezivanju, baza nije pronadjena ili su proslednjeni pogresni parametri!<br/> <%= e %> </h1><% }
                        %>                        
                    </section>


  <!-- Bootstrap core JavaScript -->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
  <script>
        $(".kartica").on("click",function(event){
            location.href="/Cineflix/IzborBioskopa?id="+$(this).attr("id");
        });
    </script>
    <script>
        if($("body").height()<$(window).height())
            {
                $("body").height($(window).height())
            }
    </script>

</body>

</html>

