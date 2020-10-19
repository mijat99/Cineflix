<%@page import="beans.Korisnik"%>
<%@page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="model" class="viewModels.IzborSedistaViewModel" scope="page" >
    <jsp:setProperty name="model" property="projekcija" value="${ viewModel.projekcija }" />
    <jsp:setProperty name="model" property="kolone" value="${ viewModel.kolone }" />
    <jsp:setProperty name="model" property="redovi" value="${ viewModel.redovi}" />
</jsp:useBean>
<!DOCTYPE html>
<html>
    <head>
        <title>Cineflix - <%= model.getProjekcija().getSala().getSalaNaziv() %></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/css/style.css" />
        <link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
        <link rel="stylesheet" href="assets/css/navbar.css" />
        <link rel="stylesheet" href="assets/css/IzborSedista.css" />
    </head>
    <body>
        <!-- Navigation -->
            <jsp:include page="/navbar.jsp">
                <jsp:param name="title" value="Main title" />
            </jsp:include>
        <%@page import="java.sql.*"%>
            <!-- Content -->
            <% Korisnik korisnik=(Korisnik)session.getAttribute("korisnik"); %>
		<div id="content">
                    <h1 class="customh1">Izbor sedista</h1>
                    <div class="container">
                        <div class="levi">
                            <div class="sedista">
                                <table class="ikonice">
                                    <tr>
                                        <td><img src="images/sedista/ikonica.png" width="50px"/></td>
                                        <td><h2>- Slobodno sediste</h2></td>
                                        <td><img src="images/sedista/vip.png" width="50px"/></td>
                                        <td><h2>- VIP sediste</h2></td>
                                        <td><img src="images/sedista/ljubavna.png" width="50px"/></td>
                                        <td><h2>- Slobodno ljubavno sediste</h2></td>
                                    </tr>
                                </table>
                                <table class="ikonice">
                                    <tr>
                                        <td><img src="images/sedista/checked.png" width="50px"/></td>
                                        <td><h2>- Zauzeto sediste</h2></td>
                                        <td colspan="0.75"><img src="images/sedista/ljubavnaZauzeta.png" width="50px"/></td>
                                        <td colspan="0.75"><h2>- Zauzeto ljubavno sediste</h2></td>
                                    </tr>
                                </table>
                            <form method="post" action="Rezervacija">
                            <table>
                                <%
                                for(int i=0;i<model.getProjekcija().getSala().getSalaRedovi();i++){
                                    if(i==0){
                                        %>
                                        <tr><td align="center" colspan="<%= model.getProjekcija().getSala().getSalaKolone() %>"><h2>PLATNO</h2></td></tr>
                                        <%}                                    
                                    %><tr><%
                                    for(int j=0;j<model.getProjekcija().getSala().getSalaKolone();j++){%>
                                        <td>
                                            <label>
                                                <input type="checkbox" class="checkbox-toggle" id="<%= i %>-<%= j %>" name="checkbox" value="<%= i %>-<%= j %>" 
                                                <%
                                                    for(int x=0;x<model.getKolone().size();x++){
                                                        if(model.getKolone().get(x)==j && model.getRedovi().get(x)==i){%>checked disabled style="border:none;"<%}
                                                    }
                                                %>>
                                                <%
                                        if(i==model.getProjekcija().getSala().getSalaRedovi()-2){%>
                                                <img src="images/sedista/ljubavna.png" width="50px" height="50px" />
                                                <img src="images/sedista/ljubavnaZauzeta.png" width="50px" height="50px" />
                                            
                                        <%} 
                                        else if(i==model.getProjekcija().getSala().getSalaRedovi()-1){%>
                                                <img src="images/sedista/vip.png" width="50px" height="50px" />
                                                <img src="images/sedista/checked.png" width="50px" height="50px" />                             
                                        <%}
                                        else{%>
                                                <img src="images/sedista/ikonica.png" width="50px" height="50px" />
                                                <img src="images/sedista/checked.png" width="50px" height="50px" />                               
                                        <%}%>
                                            </label>
                                        </td>
                                    <%}
                                    %></tr><%                                       
                                }

                                %>                                
                            </table>
                            <input type="hidden" id="brojSelektovanih" value="0">
                            <input type="hidden" id="brojRedova" name="brojRedova" value="<%= model.getProjekcija().getSala().getSalaRedovi() %>" />
                            <input type="hidden" name="projekcijaId" value="<%= model.getProjekcija().getProjekcijaId() %>" >
                            <label for="ukCena">Ukupna cena sedista: </label><input type="text" readonly id="ukCena" name="ukCena" value="0"/>
                            <%
                                if(model.getProjekcija().projekcijaJe3D()){ }
                            %>
                            <input type="hidden" name="cenaProjekcije" 
                                   <% 
                                        if(model.getProjekcija().projekcijaJe3D()){%> value="<%= model.getProjekcija().getSala().getBioskop().getBioskopCena3D() %>" <%}
                                        else{%> value="<%= model.getProjekcija().getSala().getBioskop().getBioskopCena2D() %>" <%}
                                   %>/>
                            <button type="submit" id="submit" disabled >Nastavi<i class="zmdi zmdi-arrow-right"></i></button>
                            </form>
                        </div>
                        </div>
                        <div class="desni">
                            <% if(model.getProjekcija().projekcijaJe3D()){%><h1 class="customh1"><%= model.getProjekcija().getSala().getSalaNaziv()%> - 3D</h1><%}
                            else{%><h1 class="customh1"><%= model.getProjekcija().getSala().getSalaNaziv()%> - 2D</h1><%}%>
                            <h2> Datum: <%= model.getProjekcija().getProjekcijaDatum() %></h2>
                            <h2> Vreme <%= model.getProjekcija().getProjekcijaVreme() %></h2>
                            <% if(model.getProjekcija().projekcijaJe3D()){%>
                            <p style="margin:0;" class="cenaObicno" id="<%= model.getProjekcija().getSala().getBioskop().getBioskopCena3D() %> ">Cena sedista <%= model.getProjekcija().getSala().getBioskop().getBioskopCena3D() %></p>
                            <p style="margin:0;" class="cenaLjubavno" id="<%= model.getProjekcija().getSala().getBioskop().getBioskopCena3D() %> ">Cena ljubavnog sedista <%= model.getProjekcija().getSala().getBioskop().getBioskopCena3D()*2 %></p>
                            <p style="margin:0;" class="cenaVIP" id="<%= model.getProjekcija().getSala().getBioskop().getBioskopCena3D() %> ">Cena VIP sedista: <%= model.getProjekcija().getSala().getBioskop().getBioskopCena3D()*1.3 %></p>
                            <%}
                            else{%>
                            <p style="margin:0;" class="cenaObicno" id="<%= model.getProjekcija().getSala().getBioskop().getBioskopCena2D() %> ">Cena sedista <%= model.getProjekcija().getSala().getBioskop().getBioskopCena2D() %></p>
                            <p style="margin:0;" class="cenaLjubavno" id="<%= model.getProjekcija().getSala().getBioskop().getBioskopCena2D() %> ">Cena ljubavnog sedista <%= model.getProjekcija().getSala().getBioskop().getBioskopCena2D()*2 %></p>
                            <p style="margin:0;" class="cenaVIP" id="<%= model.getProjekcija().getSala().getBioskop().getBioskopCena2D() %> ">Cena VIP sedista: <%= model.getProjekcija().getSala().getBioskop().getBioskopCena2D()*1.3 %></p>
                            <%}%>
                            <br/>
                            <% if(korisnik!=null && korisnik.getKorisnikRole().equals("Menadzer") && model.getProjekcija().getSala().getBioskop().getBioskopId()== korisnik.getBioskopId() ) {%>
                            <form method="get" action="IzmeniProjekciju">
                                <input type="hidden" name="projekcijaId" value="<%= model.getProjekcija().getProjekcijaId() %>">
                                <input type="submit" value="Izmeni projekciju">
                            </form>
                                <br/>
                            <form method="post" action="ObrisiProjekciju">
                                <input type="hidden" name="projekcijaId" value="<%= model.getProjekcija().getProjekcijaId() %>">
                                <input type="submit" value="Obrisi projekciju">
                            </form>
                            <%}%>
                            <br/>
                            <h1 class="customh1"><%= model.getProjekcija().getSala().getBioskop().getBioskopNaziv() %></h1>
                            <img src="<%= model.getProjekcija().getSala().getBioskop().getBioskopBanner() %>" alt=""/>
                            <h2>Adresa: <%= model.getProjekcija().getSala().getBioskop().getBioskopAdresa() %></h2>
                            <h2>Broj telefona: <%= model.getProjekcija().getSala().getBioskop().getBioskopTelefon() %></h2>
                            <br/>
                            <h1 class="customh1"><%= model.getProjekcija().getFilm().getFilmNaziv() %></h1>
                            <img src="<%= model.getProjekcija().getFilm().getFilmPoster() %>" alt=""/>
                            <h2>Reziser <%= model.getProjekcija().getFilm().getFilmReziser() %></h2>
                            <h2>Godina: <%= model.getProjekcija().getFilm().getFilmGodina() %></h2>
                            <h2>Zanr: <%= model.getProjekcija().getFilm().getFilmZanr() %></h2>
                            
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
                    var projekcijaJe3D="<%= model.getProjekcija().projekcijaJe3D() %>"; 
                    var cenaObicno;
                    var cenaLjubavno;
                    var cenaVIP;
                    if(projekcijaJe3D==="true"){
                        cenaObicno=<%= model.getProjekcija().getSala().getBioskop().getBioskopCena3D() %>;
                        cenaLjubavno=<%= model.getProjekcija().getSala().getBioskop().getBioskopCena3D()*2 %>;
                        cenaVIP=<%= model.getProjekcija().getSala().getBioskop().getBioskopCena3D()*1.3 %>;
                    }
                    else{
                        cenaObicno=<%= model.getProjekcija().getSala().getBioskop().getBioskopCena2D() %>;
                        cenaLjubavno=<%= model.getProjekcija().getSala().getBioskop().getBioskopCena2D()*2 %>;
                        cenaVIP=<%= model.getProjekcija().getSala().getBioskop().getBioskopCena2D()*1.3 %>;
                    }                        
                    $(".checkbox-toggle").on("click",function(){
                        if(!($(this).is(":disabled")))
                        {
                            var temp=$("#brojSelektovanih").val();
                            var split = $(this).attr("id").split("-");
                            var brRedova=$("#brojRedova").val();
                            var ukCena=parseFloat($("#ukCena").val());
                            if($(this).is(":checked")){
                                temp++; 
                                $("#submit").prop("disabled",false);
                                if(split[0]==brRedova-1){
                                    
                                    ukCena=ukCena+cenaVIP;
                                    $("#ukCena").val(ukCena);
                                }
                                else if(split[0]==brRedova-2)
                                {
                                    ukCena=ukCena+cenaLjubavno;
                                    $("#ukCena").val(ukCena);
                                }
                                else
                                {
                                    ukCena=ukCena+cenaObicno;
                                    $("#ukCena").val(ukCena);
                                }
                                
                            }
                            else{
                                temp--; 
                                if(temp==0){$("#submit").prop("disabled",true);}
                                if(split[0]==brRedova-1){
                                    
                                    ukCena=ukCena-cenaVIP;
                                    $("#ukCena").val(ukCena);
                                }
                                else if(split[0]==brRedova-2)
                                {
                                    ukCena=ukCena-cenaLjubavno;
                                    $("#ukCena").val(ukCena);
                                }
                                else
                                {
                                    ukCena=ukCena-cenaObicno;
                                    $("#ukCena").val(ukCena);
                                }
                            }
                            $("#brojSelektovanih").val(temp);
                            $(this).next().next().toggleClass("border");
                        }
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
