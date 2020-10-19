package servleti;

import beans.Korisnik;
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
public class SpisakKorisnika extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SpisakKorisnika</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SpisakKorisnika at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        Korisnik ulogovan=(Korisnik)session.getAttribute("korisnik");
        if(ulogovan==null || !ulogovan.getKorisnikRole().trim().equals("Administrator"))
        {
            request.setAttribute("errorMsg","Morate biti administrator kako bi pristupili spisku korisnika!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        try{
                String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                String user="root";
                String pass="";
                Class.forName("com.mysql.jdbc.Driver");

                Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                Statement statement=conn.createStatement();
                String upit="SELECT * FROM korisnik ORDER BY korisnikRole ASC,korisnikUsername ASC";
                ResultSet rezultat=null;
                try{
                    rezultat=statement.executeQuery(upit);
                }
                catch(SQLException sqle){        
                    request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'film' neuspesna!</br>"+sqle);
                    request.getRequestDispatcher("index.jsp").forward(request, response);}
                ArrayList<Korisnik> k= new ArrayList<>();
                while(rezultat.next()){
                    Korisnik korisnik=new Korisnik();
                    korisnik.setKorisnikId(Integer.parseInt(rezultat.getString("korisnikId")));
                    korisnik.setKorisnikUsername(rezultat.getString("korisnikUsername"));
                    korisnik.setKorisnikRole(rezultat.getString("korisnikRole"));
                    korisnik.setBioskopId(Integer.parseInt(rezultat.getString("bioskopId")));
                    k.add(korisnik);
                }
                request.setAttribute("korisnici",k);
                request.getRequestDispatcher("spisakKorisnika.jsp").forward(request, response);
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
