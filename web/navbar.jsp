<%@page import="beans.*"%>
<%@page import="java.sql.*"%>
    <div class="heder">
        <a href="index.jsp"><img class="logo" style="width:150px;height:75px;" src="images/reg/logo.png"></a>
		<nav>
			<ul class="nav_links">
				<li class="dropbtn"><a>Bioskopi</a>
					<%
                                                        try{
                                                            String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                                                            String user="root";
                                                            String pass="";
                                                            Class.forName("com.mysql.jdbc.Driver");
        
                                                            Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                                                            Statement statement=conn.createStatement();
                                                            String upit="SELECT * FROM bioskop ORDER BY bioskopNaziv ASC";
                                                            ResultSet rezultat=null;
                                                            try{
                                                                rezultat=statement.executeQuery(upit);
                                                            }
                                                            catch(SQLException sqle){
                                                                %><h1>Greska u izvrsavanju upita!<br/> <%= sqle %></h1><%
                                                            }
                                                            if(rezultat!=null){
                                                            %><ul class="dropdown-content"><%
                                                            while(rezultat.next()){                          
                                                                %><a href="IzborFilma?id=<%= rezultat.getString("bioskopId") %>"> <%= rezultat.getString("bioskopNaziv")%></a><%
                                                            }   
                                                            %></ul><%
                                                            }
                                                        }
                                                        catch(ClassNotFoundException cnfe){ %><h1>Greska u povezivanju klasa nije pronadjena!<br/> <%= cnfe %></h1><% }
                                                        catch(SQLException e){ %><h1>Greska u povezivanju, baza nije pronadjena ili su proslednjeni pogresni parametri!<br/> <%= e %> </h1><% }
                                                        %> 
				</li>
				<li class="dropbtn"><a>Repertoar</a>
						<%
                                                        try{
                                                            String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                                                            String user="root";
                                                            String pass="";
                                                            Class.forName("com.mysql.jdbc.Driver");
        
                                                            Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                                                            Statement statement=conn.createStatement();
                                                            String upit="SELECT * FROM film";
                                                            ResultSet rezultat=null;
                                                            try{
                                                                rezultat=statement.executeQuery(upit);
                                                            }
                                                            catch(SQLException sqle){
                                                                %><h1>Greska u izvrsavanju upita!<br/> <%= sqle %></h1><%
                                                            }
                                                            if(rezultat!=null){
                                                            %><ul class="dropdown-content"><%
                                                            while(rezultat.next()){                                    
                                                                %><a href="IzborBioskopa?id=<%= rezultat.getString("filmId") %>"> <%= rezultat.getString("filmNaziv")%></a><%
                                                            }   
                                                            %></ul><%
                                                            }
                                                        }
                                                        catch(ClassNotFoundException cnfe){ %><h1>Greska u povezivanju klasa nije pronadjena!<br/> <%= cnfe %></h1><% }
                                                        catch(SQLException e){ %><h1>Greska u povezivanju, baza nije pronadjena ili su proslednjeni pogresni parametri!<br/> <%= e %> </h1><% }
                                                        %>
				<%
                                                        session=request.getSession();
                                                        Korisnik korisnik = (Korisnik)session.getAttribute("korisnik");
                                                        if(korisnik==null){%>
                                                           <li><a href="register.jsp">Register</a></li>
                                                           <li><a href="login.jsp">Login</a></li>
                                                        <%}
                                                        else{
                                                            %><li><a href="MojeRezervacije">Rezervacije</a></li><%
                                                            if(korisnik.getKorisnikRole().equals("Administrator")){
                                                                %>
                                                           <li class="dropbtn"><a>Administrator</a>
                                                                <ul class="dropdown-content">
                                                                    <a href="SpisakBioskopa">Bioskopi</a>
                                                                    <a href="SpisakSala">Sale</a>
                                                                    <a href="SpisakFilmova">Filmovi</a>
                                                                    <a href="SpisakKorisnika">Koriscnici</a>
                                                                </ul>
                                                            </li><%
                                                            }
                                                            else if (korisnik.getKorisnikRole().equals("Menadzer")){
                                                                %><li><a href="Poslovanje">Poslovanje bioskopa</a></li>
                                                                <li><a href="DodajProjekciju">Nova projekcija</a></li><%
                                                            }%>
                                                            <li><a href="Odjava">Logout</a></li>
                                                        <%}%>
			</ul>
		</nav>
    </div>