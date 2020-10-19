<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>
<%@page import="beans.*"%>
<%@page isELIgnored="false" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Register</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
                <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
                <link href="assets/css/modern-business.css" rel="stylesheet">
                <link rel="stylesheet" href="assets/css/navbar.css" />
                <link rel="stylesheet" href="assets/css/style.css">
	</head>
        <jsp:useBean id="b" class="viewModels.registerViewModel" scope="page" >
            <jsp:setProperty name="b" property="bioskopi" value="${ bioskopi }" />
        </jsp:useBean>
	<body>
            <!-- Navigation -->
            <jsp:include page="/navbar.jsp">
                <jsp:param name="title" value="Main title" />
            </jsp:include>
            <%
                session=request.getSession();
                Korisnik korisnik=(Korisnik)session.getAttribute("korisnik");
            %>
		<div class="wrapper">
			<div class="inner">
				<div class="image-holder">
					<img src="images/reg/registracija.jpg" alt="">
				</div>
				<form method="post" action="Register">
					<h3>Register</h3>
					<div class="form-wrapper">
						<input type="text" placeholder="Username" class="form-control" name="username" id="username" maxlength="50" required="required" onchange="return user()">
						<i class="zmdi zmdi-account"></i>
					</div>
					<div class="form-wrapper">
						<input type="password" placeholder="Password" class="form-control" name="password" id="password" required="required" minlength="10" maxlength="14" onchange="return pass()">
						<i class="zmdi zmdi-lock"></i>
					</div>
                                        <div class="form-wrapper">
						<input type="password" placeholder="Confirm Password" class="form-control" name="passwordConf" id="passwordConf" minlength="10" maxlength="14" required="required" onchange="return passConf()">
						<i class="zmdi zmdi-lock"></i>
					</div>
                                        <%
                                            if(korisnik!=null){
                                                if(!(korisnik.getKorisnikRole().equals("")) && korisnik.getKorisnikRole().equals("Administrator"))
                                                {%>
                                                <label for="role">Uloga</label>
                                                <select name="role" id="role">
                                                    <option value="Administrator">Administrator</option>
                                                    <option value="Menadzer">Menadzer</option>
                                                    <option value="Korisnik">Korisnik</option>
                                                </select>
                                                <div id="bioskopi">
                                                    <label for="bioskopId">Bioskop</label>
                                                    <select name="bioskopId" id="bioskopId">
                                                        <option value="0">Izaberite bioskop</option><%
                                                        if(b.getBioskopi().size()>0)
                                                        {
                                                        for(int i=0;i<b.getBioskopi().size();i++)
                                                        {%> <option value="<%= b.getBioskopi().get(i).getBioskopId() %>"><%= b.getBioskopi().get(i).getBioskopNaziv() %></option> <%}
                                                        }
                                                        else{%>
                                                        <option value="0">Nema bioskopa u bazi podataka!</option>
                                                        <%}
                                                    %></select>
                                                </div>
                                                <%}
                                            }
                                            else{%>
                                                <input type="hidden"  value="Korisnik" name="role"/>
                                                <input type="hidden" name="bioskopId" value="0" />
                                    <%}
                                    %>
					<button type="submit" id="posalji">Register
						<i class="zmdi zmdi-arrow-right"></i>
					</button>
				</form>
			</div>
		</div>
		<script type="text/javascript">
		function user()
		{
			var user = /^[a-z\d]+\.?[a-z\d]+\@[a-z]{2,6}\.[a-z]{2,6}$/;
			var tekst = document.getElementById('username').value;
			var rezultat = tekst.match(user);
			if(document.getElementById('username').value=="")
			{
				document.getElementById('erroruser').innerHTML="";
				document.getElementById('registrujSe').disabled=false;
			}
			else if(rezultat==null)
			{
				document.getElementById('erroruser').innerHTML="Korisnicko ime mora biti u formatu e-mail adrese! Sadrzi se od vaseg imena koje sme sadrzati '.', nakon toga '@' zatim sufiks email providera.<br/>";
				document.getElementById('registrujSe').disabled=true;
			}
			else
			{
				document.getElementById('erroruser').innerHTML="";
				document.getElementById('registrujSe').disabled=false;
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
				document.getElementById('registrujSe').disabled=false;
			}
			else if(rezultat==null)
			{
				document.getElementById('errorpass').innerHTML="Lozinka mora da ima izmedju 6-10 karaktera, 3 cifre i jedan znak!<br/>";
				document.getElementById('registrujSe').disabled=true;
			}
			else
			{
				document.getElementById('errorpass').innerHTML="";
				document.getElementById('registrujSe').disabled=false;
			}
		}
                function passConf()
		{
			var pass = document.getElementById('password').value;
			var passConf = document.getElementById('passwordConf').value;
			if(document.getElementById('passwordConf').value=="")
			{
				document.getElementById('errorPassConf').innerHTML="";
				document.getElementById('posalji').disabled=false;
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
                $("#bioskopId").val(0);
                $("#bioskopId").change();
                if($(this).val()==="Menadzer"){
                    $("#bioskopi").show();
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
