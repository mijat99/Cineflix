package servleti;

import java.io.*;
import java.util.*;
import beans.*;
import viewModels.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class IzborBioskopa extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet IzborBioskopa</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet IzborBioskopa at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String filmId=request.getParameter("id");
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
                    request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'bioskop' neuspesna!</br>"+sqle);
                    request.getRequestDispatcher("index.jsp").forward(request, response);}
                IzborBioskopaViewModel vm=new IzborBioskopaViewModel();
                ArrayList<Bioskop> b= new ArrayList<Bioskop>();
                if(rezultat.next()){
                    Film film=new Film();
                    film.setFilmId(Integer.parseInt(rezultat.getString("filmId")));
                    film.setFilmNaziv(rezultat.getString("filmNaziv"));
                    film.setFilmGodina(rezultat.getString("filmGodina"));
                    film.setFilmZanr(rezultat.getString("filmZanr"));
                    film.setFilmPoster(rezultat.getString("filmPoster"));
                    film.setFilmReziser(rezultat.getString("filmReziser"));
                    vm.setFilm(film);
                    statement.close();
                    rezultat.close();
                    statement=conn.createStatement();
                    rezultat=null;
                    upit="SELECT * FROM bioskop WHERE bioskopId in(SELECT bioskopId from sala where salaid in(SELECT salaId FROM projekcija WHERE filmId="+filmId+"))";
                    try{
                    rezultat=statement.executeQuery(upit);
                    }
                    catch(SQLException sqle){        
                        request.setAttribute("errorMsg","Greska! Pretraga bioskopa neuspesna!</br>"+sqle);
                        request.getRequestDispatcher("index.jsp").forward(request, response);}
                    while(rezultat.next())
                    {
                        Bioskop bioskop = new Bioskop();
                        bioskop.setBioskopId(Integer.parseInt(rezultat.getString("bioskopId")));
                        bioskop.setBioskopNaziv(rezultat.getString("bioskopNaziv"));
                        bioskop.setBioskopAdresa(rezultat.getString("bioskopAdresa"));
                        bioskop.setBioskopTelefon(rezultat.getString("bioskopTelefon"));
                        bioskop.setBioskopBanner(rezultat.getString("bioskopBanner"));
                        bioskop.setBioskopCena2D(Float.parseFloat(rezultat.getString("bioskopCena2D")));
                        bioskop.setBioskopCena3D(Float.parseFloat(rezultat.getString("bioskopCena3D")));
                        b.add(bioskop);
                    }
                    vm.setBioskopi(b);
                    request.setAttribute("viewModel",vm);
                    request.getRequestDispatcher("izborBioskopa.jsp").forward(request, response);
                }
                else{
                    request.setAttribute("errorMsg","Nije pronadjen film sa id-em '"+filmId+"'");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
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
        catch(Exception e){
            request.setAttribute("errorMsg","Greska! Pogresni parametri su prosledjeni!</br>"+e);
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
