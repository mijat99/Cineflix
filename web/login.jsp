<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Login</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="stylesheet" href="assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css">
                <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
                <link href="assets/css/modern-business.css" rel="stylesheet">
                <link rel="stylesheet" href="assets/css/navbar.css" />
                <link rel="stylesheet" href="assets/css/style.css">
	</head>

	<body>
            <!-- Navigation -->
            <jsp:include page="/navbar.jsp">
                <jsp:param name="title" value="Main title" />
            </jsp:include>
            
		<div class="wrapper">
			<div class="inner">
				<div class="image-holder">
					<img src="images/reg/registracija.jpg" alt="">
				</div>
				<form method="post" action="Login">
					<h3>Login</h3>
					<div class="form-wrapper">
						<input type="text" placeholder="Username" class="form-control" name="username" id="username" maxlength="50" required="required" onchange="return user()">
						<i class="zmdi zmdi-account"></i>
					</div>
					<div class="form-wrapper">
						<input type="password" placeholder="Password" class="form-control" name="password" id="password" required="required" minlength="10" maxlength="14" onchange="return pass()">
						<i class="zmdi zmdi-lock"></i>
					</div>
					<button type="submit">Login
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
	</script>
        <script>
            if($("body").height()<$(window).height())
            {
                $("body").height($(window).height())
            }
        </script>
	</body>
</html>
