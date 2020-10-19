package servleti;

import beans.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class SpisakFilmova extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SpisakFilmova</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SpisakFilmova at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        Korisnik korisnik=(Korisnik)session.getAttribute("korisnik");
        if(korisnik==null || !korisnik.getKorisnikRole().trim().equals("Administrator"))
        {
            request.setAttribute("errorMsg","Morate biti administrator kako bi pristupili spisku filmova!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
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
                request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'film' neuspesna!</br>"+sqle);
                request.getRequestDispatcher("index.jsp").forward(request, response);}
            ArrayList<Film> f= new ArrayList<>();
            while(rezultat.next()){
                Film film=new Film();
                film.setFilmId(Integer.parseInt(rezultat.getString("filmId")));
                film.setFilmNaziv(rezultat.getString("filmNaziv"));
                film.setFilmGodina(rezultat.getString("filmGodina"));
                film.setFilmZanr(rezultat.getString("filmZanr"));
                film.setFilmPoster(rezultat.getString("filmPoster"));
                film.setFilmReziser(rezultat.getString("filmReziser"));
                f.add(film);
            }
            request.setAttribute("filmovi",f);
            request.getRequestDispatcher("spisakFilmova.jsp").forward(request, response);
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
        processRequest(request, response);
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
