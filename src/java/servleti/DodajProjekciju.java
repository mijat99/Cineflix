package servleti;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import beans.*;
import java.util.ArrayList;
import viewModels.dodajProjekcijuViewModel;
public class DodajProjekciju extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DodajProjekciju</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DodajProjekciju at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session= request.getSession();
        Korisnik korisnik=(Korisnik)session.getAttribute("korisnik");
        if(korisnik==null || !korisnik.getKorisnikRole().equals("Menadzer")){
            request.setAttribute("errorMsg","Morate biti menadzer kako bi ste dodali projekciju!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        try{
            String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
            String user="root";
            String pass="";
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection(dbUrl,user,pass);
            Statement statement=conn.createStatement();
            ResultSet rezultat=null;
            ResultSet rez=null;

            String query="SELECT * FROM sala WHERE bioskopId="+korisnik.getBioskopId();
            try{
                rezultat= statement.executeQuery(query);
            }catch(SQLException sql){
                request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'sala' neuspesna!</br>"+sql);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            ArrayList<Sala> sale=new ArrayList<>();
            while(rezultat.next()){
                Sala sala=new Sala();
                sala.setSalaId(Integer.parseInt(rezultat.getString("salaId")));
                sala.setSalaKolone(Integer.parseInt(rezultat.getString("salaKolone")));
                sala.setSalaRedovi(Integer.parseInt(rezultat.getString("salaRedovi")));
                sala.setBioskopId(Integer.parseInt(rezultat.getString("bioskopId")));
                sala.setSalaNaziv(rezultat.getString("salaNaziv")); 
                sale.add(sala);
            }
            statement.close();
            statement=conn.createStatement();
            rezultat=null;
            query="SELECT * FROM film";
            try{
                rezultat= statement.executeQuery(query);
            }catch(SQLException sql){
                request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'sala' neuspesna!</br>"+sql);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            ArrayList<Film> filmovi=new ArrayList<>();
            while(rezultat.next()){
                Film f = new Film();
                f.setFilmId(Integer.parseInt(rezultat.getString("filmId")));
                f.setFilmNaziv(rezultat.getString("filmNaziv"));
                f.setFilmGodina(rezultat.getString("filmGodina"));
                f.setFilmZanr(rezultat.getString("filmZanr"));
                f.setFilmPoster(rezultat.getString("filmPoster"));
                f.setFilmReziser(rezultat.getString("filmReziser"));
                filmovi.add(f);
            }
            dodajProjekcijuViewModel vm= new dodajProjekcijuViewModel();
            Projekcija projekcija=new Projekcija();
            vm.setProjekcija(projekcija);
            vm.setFilmovi(filmovi);
            vm.setSale(sale);

            request.setAttribute("viewModel", vm);
            request.getRequestDispatcher("dodajProjekciju.jsp").forward(request, response);

        }
        catch(ClassNotFoundException cnfe){
            request.setAttribute("errorMsg","Greska! Klasa baze nije pronadjena!</br>"+cnfe);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        catch(SQLException e){ 
            request.setAttribute("errorMsg","Greska prilikom pretrage baze!</br>"+e);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String projekcijaId;
        int salaId;
        int filmId;
        int projekcijaJe3D;
        String datum;
        String vreme;
        try{
            projekcijaId =request.getParameter("projekcijaId");
            salaId =Integer.parseInt(request.getParameter("salaId"));
            filmId =Integer.parseInt(request.getParameter("filmId"));
            projekcijaJe3D =Integer.parseInt(request.getParameter("projekcijaJe3D"));
            datum=request.getParameter("datum");
            vreme=request.getParameter("vreme");
            try{
                String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                String user="root";
                String pass="";
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                Statement statement=conn.createStatement();
                String upit="INSERT INTO projekcija(salaId,filmId,projekcijaJe3D,projekcijaDatum,projekcijaVreme) VALUES( "+salaId+", "+filmId+", "+projekcijaJe3D+", '"+datum+"', '"+vreme+"')";
                int rezultat=0;
                try{
                    rezultat=statement.executeUpdate(upit);
                }
                catch(SQLException sqle){ 
                    request.setAttribute("errorMsg","Greska prilikom dodavanja projekcije! Pokusajte ponovo.</br>"+sqle);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                if(rezultat==1){
                    request.setAttribute("errorMsg","Uspesno dodata projekcija!");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }                 
                request.setAttribute("errorMsg","Nije moguce dodati zeljenu projekciju! Pokusajte ponovo");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            catch(ClassNotFoundException cnfe){
                request.setAttribute("errorMsg","Greska! Klasa baze nije pronadjena!</br>"+cnfe);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            catch(SQLException e){ 
                request.setAttribute("errorMsg","Greska prilikom pretrage baze!</br>"+e);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        }
        catch(NullPointerException npe){
            request.setAttribute("errorMsg","Nije prosledjen parametar za brisanje projekcije! Pokusajte ponovo.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
