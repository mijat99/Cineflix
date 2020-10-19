<%@page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="vm" class="viewModels.RezervacijeViewModel">
    <jsp:setProperty name="vm" property="rezervacije" value="${viewModel.rezervacije}"/>
</jsp:useBean>
<!-- value="  " -->
<!DOCTYPE html>
<html id="html">
    <head>
        <title>Cineflix - Rezervacije</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" href="assets/css/navbar.css" />
        <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" href="assets/css/rezervacije.css" />
        <style>
            p{color:black;}
            h2{color:black;}
            td{color:black;}
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
                    <h1 class="customh1">Rezervacije</h1>
                    <section>
                        <% 
                            String msg= (String)request.getAttribute("errorMsg"); 
                            if(msg!= null && !msg.isEmpty()){
                                %><h1><%= msg %></h1> <%}%>
                    </section>
                    <div class="container">
                        <div>
                            <ul class="rezervacije"><%
                                for(int i=0;i<vm.getRezervacije().size();i++)
                                {%>
                                    <li>
                                        <div class="rezervacija">
                                            <div>
                                                <img src="<%= vm.getRezervacije().get(i).getProjekcija().getFilm().getFilmPoster() %>" height="200px">
                                            </div>
                                            <div class="info">
                                                <h2><%= vm.getRezervacije().get(i).getProjekcija().getFilm().getFilmNaziv() %></h2>
                                                <p><%= vm.getRezervacije().get(i).getProjekcija().getSala().getBioskop().getBioskopNaziv() %></p>
                                                <%
                                                if(vm.getRezervacije().get(i).getProjekcija().projekcijaJe3D()){%>
                                                    <p><%= vm.getRezervacije().get(i).getProjekcija().getSala().getSalaNaziv() %> - 3D </p>
                                                <%}
                                                else{%>
                                                    <p><%= vm.getRezervacije().get(i).getProjekcija().getSala().getSalaNaziv() %> - 2D </p>
                                                <%}%>
                                                <p><%= vm.getRezervacije().get(i).getProjekcija().getProjekcijaDatum()%> - <%= vm.getRezervacije().get(i).getProjekcija().getProjekcijaVreme() %></p>
                                                <p>Cena rezervacije: <%= vm.getRezervacije().get(i).getRezervacijaCena() %></p>
                                            </div>
                                            <div>
                                                <table class="karte">
                                                    <%for(int j=0;j<vm.getRezervacije().get(i).getKolone().size();j++)
                                                    {%>
                                                        <tr>

                                                            <td>Red <%= vm.getRezervacije().get(i).getRedovi().get(j)+1 %> - Sediste <%= vm.getRezervacije().get(i).getKolone().get(j)+1 %></td>
                                                            <td>
                                                                <form action="ObrisiSediste" method="post">
                                                                    <%
                                                                        double cena;
                                                                        if(vm.getRezervacije().get(i).getProjekcija().projekcijaJe3D()){
                                                                            cena=vm.getRezervacije().get(i).getProjekcija().getSala().getBioskop().getBioskopCena3D();
                                                                        }else{
                                                                            cena=vm.getRezervacije().get(i).getProjekcija().getSala().getBioskop().getBioskopCena2D();
                                                                        }
                                                                        if(vm.getRezervacije().get(i).getProjekcija().getSala().getSalaRedovi()-1==vm.getRezervacije().get(i).getRedovi().get(j)) { cena=cena*1.3; }
                                                                        else if(vm.getRezervacije().get(i).getProjekcija().getSala().getSalaRedovi()-2==vm.getRezervacije().get(i).getRedovi().get(j)) { cena=cena*2; }
                                                                    %>
                                                                    <input type="hidden" name="cenaKarte" value="<%= cena %>">
                                                                    <input type="hidden" name="sediste" id="sediste" value="<%= vm.getRezervacije().get(i).getRedovi().get(j) %>-<%= vm.getRezervacije().get(i).getKolone().get(j) %>">
                                                                    <input type="hidden" name="rezervacijaId" value="<%= vm.getRezervacije().get(i).getRezervacijaId() %>" >
                                                                    <button type="submit" id="" value="x">Obrisi<i class="zmdi zmdi-arrow-right"></i></button>
                                                                </form>
                                                            </td>

                                                        </tr>
                                                    <%}%>
                                                </table>
                                            </div>
                                            <div class="dugmeOtkazi">
                                                <form action="ObrisiRezervaciju" method="post">
                                                    <input type="hidden" name="rezervacijaId" value="<%= vm.getRezervacije().get(i).getRezervacijaId() %>">
                                                    <button type="submit">Otkazi<i class="zmdi zmdi-arrow-right"></i></button>                                               
                                                </form>
                                            </div>
                                        </div>
                                    </li>
                                <%}%>                                
                            </ul>
                            <%
                                if(vm.getRezervacije().size()==0){
                                    %><h2>Nemate rezervacija.</h2><%
                                }
                            %>
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
