package servleti;

import beans.*;
import java.io.*;
import viewModels.*;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

public class IzborSedista extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet izborSedista</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet izborSedista at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String projekcijaId;
        Projekcija projekcija=new Projekcija();
        try{
            projekcijaId=request.getParameter("projekcijaId");
            try{
                String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                String user="root";
                String pass="";
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn=DriverManager.getConnection(dbUrl,user,pass);

                Statement statement=conn.createStatement();
                String upit="SELECT * FROM projekcija WHERE projekcijaId="+projekcijaId;
                ResultSet rezultat=null;
                try{
                    rezultat=statement.executeQuery(upit);
                }
                catch(SQLException sqle){ 
                    request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'projekcija' neuspesna!</br>"+sqle);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                if(rezultat.next()){
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
                            
                            IzborSedistaViewModel vm= new IzborSedistaViewModel();
                            vm.setProjekcija(projekcija);
                            statement.close();
                            rezultat.close();
                            statement=conn.createStatement();
                            rezultat=null;
                            upit="SELECT * FROM rezervisanasedista WHERE rezervacijaId in(SELECT rezervacijaId FROM rezervacija WHERE projekcijaId="+projekcija.getProjekcijaId()+")";
                            try{
                                rezultat=statement.executeQuery(upit);
                            }
                            catch(SQLException sqle){ 
                                request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'bioskop' neuspesna!</br>"+sqle);
                                request.getRequestDispatcher("index.jsp").forward(request, response);
                            }
                            ArrayList<Integer> kolona=new ArrayList<>();
                            ArrayList<Integer> red=new ArrayList<>();
                            while(rezultat.next()){
                                kolona.add(Integer.parseInt(rezultat.getString("sedisteKolona")));
                                red.add(Integer.parseInt(rezultat.getString("sedisteRed")));
                            }
                            vm.setKolone(kolona);
                            vm.setRedovi(red);
                            request.setAttribute("viewModel", vm);
                            request.getRequestDispatcher("izborSedista.jsp").forward(request,response);
                        }
                        
                    }
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
