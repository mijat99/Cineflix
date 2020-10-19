package servleti;

import beans.*;
import viewModels.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class IzborProjekcije extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    String bioskopId;
    String filmId;
    try{
        bioskopId=request.getParameter("bioskopId");
        filmId=request.getParameter("filmId");
        projekcijeViewModel viewModel=new projekcijeViewModel();
        ArrayList<Projekcija> projekcije = new ArrayList<Projekcija>();
        try{
            String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
            String user="root";
            String pass="";
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection(dbUrl,user,pass);
            
            Statement statement=conn.createStatement();
            String upit="SELECT * FROM film WHERE filmId="+filmId;
            ResultSet rezultat=null;
            try{
                rezultat=statement.executeQuery(upit);
            }
            catch(SQLException sqle){ 
                request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'film' neuspesna!</br>"+sqle);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            if(rezultat.next()){
                Film film=new Film();
                film.setFilmId(Integer.parseInt(rezultat.getString("filmId")));
                film.setFilmNaziv(rezultat.getString("filmNaziv"));
                film.setFilmGodina(rezultat.getString("filmGodina"));
                film.setFilmZanr(rezultat.getString("filmZanr"));
                film.setFilmPoster(rezultat.getString("filmPoster"));
                film.setFilmReziser(rezultat.getString("filmReziser"));
                viewModel.setFilm(film);
                statement.close();
            }
            statement=conn.createStatement();
            upit="SELECT * FROM bioskop WHERE bioskopId="+bioskopId;
            rezultat=null;
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
                viewModel.setBioskop(bioskop);
                statement.close();
            }
            statement=conn.createStatement();
            upit="SELECT * FROM `projekcija` WHERE projekcijaId in "
                    
                    + "(SELECT p.projekcijaId FROM projekcija p "
                    + "JOIN sala s on p.salaId=s.salaId "
                    + "JOIN bioskop b ON b.bioskopId=s.bioskopId "
                    + "JOIN film f ON f.filmId=p.filmId "
                    + "WHERE b.bioskopId="+bioskopId +" AND f.filmId="+filmId+")"
                    
                    + "ORDER BY projekcijaDatum ASC, projekcijaVreme ASC";           
            rezultat=null;
            try{
                rezultat=statement.executeQuery(upit);
            }
            catch(SQLException sqle){
                request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'projekcija' neuspesna!</br>"+sqle);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            while(rezultat.next()){
                Projekcija projekcija=new Projekcija();
                projekcija.setProjekcijaId(Integer.parseInt(rezultat.getString("projekcijaId")));
                
                Statement stmt= conn.createStatement();
                String query= "Select * from sala where salaId="+rezultat.getString("salaId");
                ResultSet rez=null;
                try{
                    rez= stmt.executeQuery(query);
                }catch(SQLException sql){
                    request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'sala' neuspesna!</br>"+sql);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                if(rez.next())
                {
                    Sala sala=new Sala();
                    sala.setSalaId(Integer.parseInt(rez.getString("salaId")));
                    sala.setSalaKolone(Integer.parseInt(rez.getString("salaKolone")));
                    sala.setSalaRedovi(Integer.parseInt(rez.getString("salaRedovi")));
                    sala.setBioskopId(Integer.parseInt(rez.getString("bioskopId")));
                    sala.setSalaNaziv(rez.getString("salaNaziv"));
                    projekcija.setSala(sala);
                }
                projekcija.setProjekcijaId(Integer.parseInt(rezultat.getString("projekcijaId")));
                projekcija.setFilmId(Integer.parseInt(rezultat.getString("filmId")));
                int br= Integer.parseInt(rezultat.getString("projekcijaJe3D"));
                if(br==0){ projekcija.setProjekcijaJe3D(false); }
                else { projekcija.setProjekcijaJe3D(true); }
                java.sql.Date datum= java.sql.Date.valueOf(rezultat.getString("projekcijaDatum"));
                projekcija.setProjekcijaDatum(datum);
                java.sql.Time vreme = java.sql.Time.valueOf(rezultat.getString("projekcijaVreme"));
                projekcija.setProjekcijaVreme(vreme);
                projekcije.add(projekcija);
            }
            viewModel.setProjekcije(projekcije);
            request.setAttribute("viewModel", viewModel);
            request.getRequestDispatcher("izborProjekcije.jsp").forward(request, response);
            
        }
        catch(ClassNotFoundException cnfe){
            request.setAttribute("errorMsg","Greska! Klasa baze nije pronadjena!</br>"+cnfe);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        catch(SQLException e){ 
            request.setAttribute("errorMsg","Greska prilikom pretrage baze!</br>"+e);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }catch(Exception e){
        request.setAttribute("errorMsg","Greska! Pogresni parametri su prosledjeni!</br>"+e);
        request.getRequestDispatcher("index.jsp").forward(request, response);
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
    }// </editor-fold>

}
