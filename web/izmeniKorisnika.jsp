<%@page import="java.util.ArrayList"%>
<%@page import="beans.*"%>
<%@page isELIgnored="false" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cineflix - Registracija</title>
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
        <% ArrayList<Bioskop> b = (ArrayList<Bioskop>)request.getAttribute("bioskopi");
            Korisnik k=(Korisnik)request.getAttribute("korisnik");%>
        <%@page import="java.sql.*"%>
            <!-- Content -->
		<div id="content">
                    <div class="sredina">
                        
                                    <h2>Izmena korisnika</h2>
                        <div class="wrapper">
                            <%
                                String error=(String)request.getAttribute("errorMsg");
                                if(error!= null && !(error.trim().equals("")))
                                {%>
                                    <h2 style="color: red"><%=error%></h2>
                                <%}%>
                            <form method="post" action="IzmeniKorisnika">   
                                <input type="hidden" name="korisnikId" value="<%= k.getKorisnikId() %>" class="form-control">
                                <label for="username">Korisnicko ime</label>
                                <input type="text" placeholder="Novo korisnicko ime" value="<%= k.getKorisnikUsername() %>" name="username" id="username" maxlength="50" class="form-control" required="required" onchange="return user()" >
                                <span class="help-block" style="color:red;" id="erroruser"></span>
                                <label for="password">Lozinka</label>
                                <input type="password" placeholder="Nova lozinka" name="password" id="password" class="form-control" minlength="10" maxlength="14" >
                                <span class="help-block" style="color:red;" id="errorpass"></span>
                                <label for="passwordConf">Potvrdite lozinku</label>
                                <input type="password" placeholder="Potvrda nove lozinke" name="passwordConf" id="passwordConf" class="form-control" minlength="10" maxlength="14" onchange="return passConf()">
                                <span class="help-block" style="color:red;" id="errorPassConf"></span>
                                
                                
                                        <label for="role">Uloga</label>
                                        <select name="role" id="role">
                                            <option value="Administrator" <% if(k.getKorisnikRole().equals("Administrator")){%>selected<%} %> >Administrator</option>
                                            <option value="Menadzer" <% if(k.getKorisnikRole().equals("Menadzer")){%>selected<%} %> >Menadzer</option>
                                            <option value="Korisnik" <% if(k.getKorisnikRole().equals("Korisnik")){%>selected<%} %> >Korisnik</option>
                                        </select>
                                        <div id="bioskopi">
                                            <label for="bioskopId">Bioskop</label>
                                            <select name="bioskopId" id="bioskopId">
                                                <option value="0">Izaberite bioskop</option><%
                                                if(b.size()>0)
                                                {
                                                for(int i=0;i<b.size();i++)
                                                {%> <option value="<%= b.get(i).getBioskopId() %>">
                                                        <%= b.get(i).getBioskopNaziv() %>
                                                    </option> <%}
                                                }
                                                else{%>
                                                <option value="0">Nema bioskopa u bazi podataka!</option>
                                                <%}
                                            %></select>
                                        </div>
                                        <button type="submit" id="posalji">Izmeni<i class="zmdi zmdi-arrow-right"></i></button>
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
                <script type="text/javascript">
		function user()
		{
			var user = /^[a-z\d]+\.?[a-z\d]+\@[a-z]{2,6}\.[a-z]{2,6}$/;
			var tekst = document.getElementById('username').value;
			var rezultat = tekst.match(user);
			if(document.getElementById('username').value=="")
			{
				document.getElementById('erroruser').innerHTML="";
				document.getElementById('posalji').disabled=false;
			}
			else if(rezultat==null)
			{
				document.getElementById('erroruser').innerHTML="Korisnicko ime mora biti u formatu e-mail adrese! Sadrzi se od vaseg imena koje sme sadrzati '.', nakon toga '@' zatim sufiks email providera.<br/>";
				document.getElementById('posalji').disabled=true;
			}
			else
			{
				document.getElementById('erroruser').innerHTML="";
				document.getElementById('posalji').disabled=false;
			}
		}

		function pass()
		{
			var pass = /^[a-zA-Z]{6,10}[\d]{3,5}[!@#$%^&*?.]{1}$/;
			var tekst = document.getElementById('password').value;
			var rezultat = tekst.match(pass);
			if(document.getElementById('password').value=="")
			{
				document.getElementById('errorpass').innerHTML="";
				document.getElementById('posalji').disabled=false;
			}
			else if(rezultat==null)
			{
				document.getElementById('errorpass').innerHTML="Lozinka mora da ima izmedju 6-10 karaktera, 3 cifre i jedan znak!<br/>";
				document.getElementById('posalji').disabled=true;
			}
			else
			{
				document.getElementById('errorpass').innerHTML="";
				document.getElementById('posalji').disabled=false;
			}
		}
                function passConf()
		{
			var pass = document.getElementById('password').value;
			var passConf = document.getElementById('passwordConf').value;
			if(passConf=="" && pass=="")
			{
                            document.getElementById('errorPassConf').innerHTML="";
                            document.getElementById('posalji').disabled=false;
			}
                        else if(pass!="" && passConf==""){
                            document.getElementById('errorPassConf').innerHTML="Lozinka i potvrda lozinke se ne poklapaju!</br>";
                            document.getElementById('posalji').disabled=true;
                        }
			else if(pass!=passConf)
			{
                            document.getElementById('errorPassConf').innerHTML="Lozinka i potvrda lozinke se ne poklapaju!</br>";
                            document.getElementById('posalji').disabled=true;
			}
			else
			{
                            document.getElementById('errorPassConf').innerHTML="";
                            document.getElementById('posalji').disabled=false;
			}
		}
                $("#password").on("change",function(){
                    pass();
                    passConf();
                });
	</script>
        <script>
            $(document).ready(function(){
                $("#role").change();
            });
            if($("body").height()<$(window).height())
            {
                $("body").height($(window).height());
            }
            $("#role").on("change",function(){
                var bioskopId=<%= k.getBioskopId() %>;
                $("#bioskopId").val(0);
                $("#bioskopId").change();
                if($(this).val()==="Menadzer"){
                    $("#bioskopi").show();
                    $("#bioskopId").val(bioskopId);
                    $("#bioskopId").change();
                }
                else{
                    $("#bioskopi").hide();
                }
            });
            $("#bioskopId").on("change",function(){
               if($(this).val()==="0" && $("#role").val()==="Menadzer") {
                   $("#posalji").prop("disabled",true);
               }
               else{
                   $("#posalji").prop("disabled",false);
               }
            });
        </script>
    </body>
</html>
