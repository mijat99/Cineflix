<%@page import="beans.Bioskop"%>
<%@page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="b" class="beans.Bioskop">
<jsp:setProperty name="b" property="bioskopId" value="${bioskop.bioskopId}"/> 
<jsp:setProperty name="b" property="bioskopNaziv" value="${bioskop.bioskopNaziv}"/> 
<jsp:setProperty name="b" property="bioskopAdresa" value="${bioskop.bioskopAdresa}"/> 
<jsp:setProperty name="b" property="bioskopTelefon" value="${bioskop.bioskopTelefon}"/>
<jsp:setProperty name="b" property="bioskopBanner" value="${bioskop.bioskopBanner}"/>  
</jsp:useBean>
<!-- value="  " -->
<!DOCTYPE html>
<html>
    <head>
        <title>Cineflix - Filmovi</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/style.css" />
        <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" href="assets/css/navbar.css" />
        <link rel="stylesheet" href="assets/css/IzborFilma.css" />
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
        <%@page import="java.sql.*"%>
            <!-- Content -->
		<div id="content">
                    <h1 class="customh1"><jsp:getProperty name="b" property="bioskopNaziv"/></h1>
                    <div class="container">
                        <div class="levi">
                            <%
                                try{
                                    String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                                    String user="root";
                                    String pass="";
                                    Class.forName("com.mysql.jdbc.Driver");

                                    Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                                    Statement statement=conn.createStatement();
                                    String subUpit="SELECT DISTINCT(f.filmId) FROM film f "
                                            + "JOIN projekcija p ON p.filmId=f.filmId "
                                            + "JOIN sala s ON p.salaId=s.salaId "
                                            + "JOIN bioskop b ON s.bioskopId=b.bioskopId "
                                            + "WHERE b.bioskopId="+b.getBioskopId() ;
                                    String upit="SELECT * FROM film WHERE filmId in ("+subUpit+")";
                                    ResultSet rezultat=null;
                                    try{
                                        rezultat=statement.executeQuery(upit);
                                    }
                                    catch(SQLException sqle){
                                        %><h1>Greska u izvrsavanju upita!<br/> <%= sqle %></h1><%
                                    }
                                    if(rezultat!=null){
                                        %><ul class="filmovi"><%
                                        while(rezultat.next()){                                       
                                            %><li class="film">
                                                <div class="filmoviSlika">
                                                    <img src="<%=rezultat.getString("filmPoster") %>" height="200px" alt=""/>                                        
                                                </div>
                                                <div class="filmoviText">
                                                    <h2><%=rezultat.getString("filmNaziv") %></h2>
                                                    <h3>Godina: <%=rezultat.getString("filmGodina") %></h3>
                                                    <h3>Zanrovi: <%=rezultat.getString("filmZanr") %></h3>
                                                    <h3>Reziser: <%=rezultat.getString("filmReziser") %></h3>
                                                </div>  
                                                <div class="filmoviDugme">
                                                    <form method="get" action="IzborProjekcije">
                                                        <input type="hidden" name="bioskopId" value="<%= b.getBioskopId()%>"/>
                                                        <input type="hidden" name="filmId" value="<%= rezultat.getString("filmId")%>"/>
                                                        <button type="submit">Projekcije<i class="zmdi zmdi-arrow-right"></i></button>
                                                    </form>
                                                </div>
                                            </li><%
                                            }
                                        }   
                                        %></ul><%
                                }
                                catch(ClassNotFoundException cnfe){ %><h1>Greska u povezivanju klasa nije pronadjena!<br/> <%= cnfe %></h1><% }
                                catch(SQLException e){ %><h1>Greska u povezivanju, baza nije pronadjena ili su proslednjeni pogresni parametri!<br/> <%= e %> </h1><% }
                                %>
                        </div>
                        <div class="desni">
                            <img src="<jsp:getProperty name="b" property="bioskopBanner" />" alt=""/>
                            <h2>Adresa: <jsp:getProperty name="b" property="bioskopAdresa"/></h2>
                            <h2>Broj telefona: <jsp:getProperty name="b" property="bioskopTelefon"/></h2>
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
