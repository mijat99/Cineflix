package servleti;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;
import java.util.ArrayList;
import java.sql.*;
import viewModels.poslovanjeViewModel;
public class Poslovanje extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Poslovanje</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Poslovanje at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        Korisnik korisnik=(Korisnik)session.getAttribute("korisnik");
        if(korisnik==null){
            request.setAttribute("errorMsg","Morate biti ulogovani!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        else if(!(korisnik.getKorisnikRole().equals("Menadzer"))){
            request.setAttribute("errorMsg","Samo menadzeri bioskopa imaju pristup ovoj strani!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        try
        {
            String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
            String user="root";
            String pass="";
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn=DriverManager.getConnection(dbUrl,user,pass);
            Statement statement=conn.createStatement();
            String upit="SELECT b.*,COUNT(*) as brRez ,SUM(r.rezervacijaCena) as suma FROM bioskop b "
                    + "JOIN sala s ON s.bioskopId=b.bioskopId "
                    + "JOIN projekcija p ON p.salaId=s.salaId "
                    + "JOIN film f ON p.filmId=f.filmId "
                    + "JOIN rezervacija r ON r.projekcijaId=p.projekcijaId WHERE b.bioskopId="+korisnik.getBioskopId();
            ResultSet rezultat=null;
            try{
                rezultat=statement.executeQuery(upit);
            }
            catch(SQLException sqle){        
                request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'bioskop' neuspesna!</br>"+sqle);
                request.getRequestDispatcher("index.jsp").forward(request, response);}
            if(rezultat.next()){
                Bioskop bioskop=new Bioskop();
                bioskop.setBioskopId(Integer.parseInt(rezultat.getString("bioskopId")));
                bioskop.setBioskopNaziv(rezultat.getString("bioskopNaziv"));
                bioskop.setBioskopAdresa(rezultat.getString("bioskopAdresa"));
                bioskop.setBioskopTelefon(rezultat.getString("bioskopTelefon"));
                bioskop.setBioskopBanner(rezultat.getString("bioskopBanner"));
                bioskop.setBioskopCena2D(Float.parseFloat(rezultat.getString("bioskopCena2D")));
                bioskop.setBioskopCena3D(Float.parseFloat(rezultat.getString("bioskopCena3D")));
                int brRezervacija=Integer.parseInt(rezultat.getString("brRez"));
                String s=rezultat.getString("suma");
                float suma=0;
                if(s!=null){
                    suma=Float.parseFloat(rezultat.getString("suma"));
                }
                poslovanjeViewModel vm=new poslovanjeViewModel();
                vm.setBioskop(bioskop);
                vm.setBrojRezervacija(brRezervacija);
                vm.setUkupnoRezervacije(suma);
                ArrayList<Film> filmovi=new ArrayList<>();
                ArrayList<Integer> brojProjekcijaPoFilmu=new ArrayList<>();
                ArrayList<Float> sumaPoFilmu=new ArrayList<>();

                statement.close();
                rezultat.close();
                statement=conn.createStatement();
                rezultat=null;
                upit="SELECT f.*,COUNT(p.projekcijaId) as brojProjekcija,SUM(r.rezervacijaCena) as ukFilm "
                        + "FROM film f "
                        + "JOIN projekcija p ON p.filmId=f.filmId "
                        + "JOIN sala s ON s.salaId=p.salaId "
                        + "JOIN bioskop b ON s.bioskopId=b.bioskopId "
                        + "JOIN rezervacija r ON r.projekcijaId=p.projekcijaId "
                        + "WHERE b.bioskopId="+korisnik.getBioskopId()+" GROUP BY f.filmId";
                try{
                    rezultat=statement.executeQuery(upit);
                }
                catch(SQLException sqle){        
                    request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'bioskop' neuspesna!</br>"+sqle);
                    request.getRequestDispatcher("index.jsp").forward(request, response);}
                while(rezultat.next()){
                    Film f= new Film();
                    f.setFilmId(Integer.parseInt(rezultat.getString("filmId")));
                    f.setFilmNaziv(rezultat.getString("filmNaziv"));
                    f.setFilmGodina(rezultat.getString("filmGodina"));
                    f.setFilmZanr(rezultat.getString("filmZanr"));
                    f.setFilmPoster(rezultat.getString("filmPoster"));
                    f.setFilmReziser(rezultat.getString("filmReziser"));
                    brojProjekcijaPoFilmu.add(Integer.parseInt(rezultat.getString("brojProjekcija")));
                    sumaPoFilmu.add(Float.parseFloat(rezultat.getString("ukFilm")));
                    filmovi.add(f);
                }
                vm.setFilmovi(filmovi);
                vm.setBrojProjekcijaPoFilmu(brojProjekcijaPoFilmu);
                vm.setSumaPoFilmu(sumaPoFilmu);
                request.setAttribute("viewModel", vm);
                request.getRequestDispatcher("poslovanje.jsp").forward(request, response);
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
        request.setAttribute("errorMsg","Greska u prikazivanju poslovanja! Molimo pokusajte ponovo!");
        request.getRequestDispatcher("index.jsp").forward(request, response);
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
