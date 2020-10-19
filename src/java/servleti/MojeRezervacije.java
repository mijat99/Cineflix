package servleti;

import beans.*;
import java.util.*;
import java.sql.*;
import viewModels.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MojeRezervacije extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        Korisnik korisnik= (Korisnik)session.getAttribute("korisnik");
        if(korisnik==null){
            request.setAttribute("errorMsg","Morate biti ulogovani!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        RezervacijeViewModel viewModel=new RezervacijeViewModel();
        ArrayList<beans.Rezervacija> nizRez=new ArrayList<>();
        try{
            String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
            String user="root";
            String pass="";
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn=DriverManager.getConnection(dbUrl,user,pass);
            Statement statement=conn.createStatement();
            String upit="SELECT * FROM rezervacija WHERE korisnikId="+korisnik.getKorisnikId();
            ResultSet rezultat=null;
            try{
                rezultat=statement.executeQuery(upit);
            }
            catch(SQLException sqle){        
                request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'rezervacija' neuspesna!</br>"+sqle);
                request.getRequestDispatcher("index.jsp").forward(request, response);}
            while(rezultat.next()){
                beans.Rezervacija rezervacija = new beans.Rezervacija();
                rezervacija.setRezervacijaId(Integer.parseInt(rezultat.getString("rezervacijaId")));
                rezervacija.setRezervacijaCena(Float.parseFloat(rezultat.getString("rezervacijaCena")));
                rezervacija.setKorisnikId(Integer.parseInt(rezultat.getString("korisnikId")));
                rezervacija.setProjekcijaId(Integer.parseInt(rezultat.getString("projekcijaId")));
                nizRez.add(rezervacija);
            }            
            for(beans.Rezervacija item : nizRez)
            {
                statement.close();
                statement=conn.createStatement();
                rezultat.close();
                upit="SELECT * FROM rezervisanaSedista WHERE rezervacijaId="+item.getRezervacijaId();
                rezultat=null;
                try{
                    rezultat=statement.executeQuery(upit);
                }catch(SQLException sqle){        
                request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'rezervacija' neuspesna!</br>"+sqle);
                request.getRequestDispatcher("index.jsp").forward(request, response);}
                ArrayList<Integer> red=new ArrayList<>();
                ArrayList<Integer> kolona=new ArrayList<>();
                while(rezultat.next()){
                    red.add(Integer.parseInt(rezultat.getString("sedisteRed")));
                    kolona.add(Integer.parseInt(rezultat.getString("sedisteKolona")));
                }
                item.setRedovi(red);
                item.setKolone(kolona);
                
                statement.close();
                statement=conn.createStatement();
                rezultat.close();
                upit="SELECT * FROM projekcija WHERE projekcijaId="+item.getProjekcijaId();
                rezultat=null;
                try{
                    rezultat=statement.executeQuery(upit);
                }catch(SQLException sqle){        
                request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'rezervacija' neuspesna!</br>"+sqle);
                request.getRequestDispatcher("index.jsp").forward(request, response);}
                if(rezultat.next()){
                    beans.Projekcija projekcija= new beans.Projekcija();
                    String filmId=rezultat.getString("filmId");
                    String salaId=rezultat.getString("salaId");
                    projekcija.setProjekcijaId(Integer.parseInt(rezultat.getString("projekcijaId")));
                    projekcija.setFilmId(Integer.parseInt(rezultat.getString("filmId")));
                    int br= Integer.parseInt(rezultat.getString("projekcijaJe3D"));
                    if(br==0){ projekcija.setProjekcijaJe3D(false); }
                    else { projekcija.setProjekcijaJe3D(true); }
                    java.sql.Date datum= java.sql.Date.valueOf(rezultat.getString("projekcijaDatum"));
                    projekcija.setProjekcijaDatum(datum);
                    java.sql.Time vreme = java.sql.Time.valueOf(rezultat.getString("projekcijaVreme"));
                    projekcija.setProjekcijaVreme(vreme);
                    
                    statement.close();
                    rezultat.close();
                    statement = conn.createStatement();
                    rezultat=null;
                    upit="SELECT * FROM film WHERE filmId="+filmId;
                    try{
                        rezultat=statement.executeQuery(upit);
                    }
                    catch(SQLException sqle){ 
                        request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'film' neuspesna!</br>"+sqle);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                    if(rezultat.next())
                    {
                        Film film=new Film();
                        film.setFilmId(Integer.parseInt(rezultat.getString("filmId")));
                        film.setFilmNaziv(rezultat.getString("filmNaziv"));
                        film.setFilmGodina(rezultat.getString("filmGodina"));
                        film.setFilmZanr(rezultat.getString("filmZanr"));
                        film.setFilmPoster(rezultat.getString("filmPoster"));
                        film.setFilmReziser(rezultat.getString("filmReziser"));
                        projekcija.setFilm(film);
                        projekcija.setFilmId(film.getFilmId());
                    }
                    statement.close();
                    rezultat.close();
                    statement = conn.createStatement();
                    rezultat=null;
                    upit="SELECT * FROM sala WHERE salaId="+salaId;
                    try{
                        rezultat=statement.executeQuery(upit);
                    }
                    catch(SQLException sqle){ 
                        request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'sala' neuspesna!</br>"+sqle);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                    if(rezultat.next())
                    {
                        Sala sala=new Sala();
                        sala.setSalaId(Integer.parseInt(rezultat.getString("salaId")));
                        sala.setSalaRedovi(Integer.parseInt(rezultat.getString("salaRedovi")));
                        sala.setSalaKolone(Integer.parseInt(rezultat.getString("salaKolone")));
                        sala.setBioskopId(Integer.parseInt(rezultat.getString("bioskopId")));
                        sala.setSalaNaziv(rezultat.getString("salaNaziv"));
                        
                        statement.close();
                        rezultat.close();
                        statement=conn.createStatement();
                        rezultat=null;
                        upit="SELECT * from bioskop WHERE bioskopId="+String.valueOf(sala.getBioskopId());
                        try{
                            rezultat=statement.executeQuery(upit);
                        }
                        catch(SQLException sqle){ 
                            request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'bioskop' neuspesna!</br>"+sqle);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                        if(rezultat.next()){
                            Bioskop bioskop=new Bioskop();
                            bioskop.setBioskopId(Integer.parseInt(rezultat.getString("bioskopId")));
                            bioskop.setBioskopNaziv(rezultat.getString("bioskopNaziv"));
                            bioskop.setBioskopAdresa(rezultat.getString("bioskopAdresa"));
                            bioskop.setBioskopTelefon(rezultat.getString("bioskopTelefon"));
                            bioskop.setBioskopBanner(rezultat.getString("bioskopBanner"));
                            bioskop.setBioskopCena2D(Float.parseFloat(rezultat.getString("bioskopCena2D")));
                            bioskop.setBioskopCena3D(Float.parseFloat(rezultat.getString("bioskopCena3D")));
                            sala.setBioskop(bioskop);                        
                            projekcija.setSala(sala);
                            item.setProjekcija(projekcija);
                        }
                        
                    }
                }
                
            }
            viewModel.setRezervacije(nizRez);
            request.setAttribute("viewModel", viewModel);
            request.getRequestDispatcher("mojeRezervacije.jsp").forward(request,response);
        }
        catch(ClassNotFoundException cnfe){
        request.setAttribute("errorMsg","Greska! Klasa baze nije pronadjena!</br>"+cnfe);
        request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        catch(SQLException e){ 
            request.setAttribute("errorMsg","Greska prilikom pretrage baze!</br>"+e);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }        
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
