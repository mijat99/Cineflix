package servleti;

import beans.Korisnik;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class ObrisiKorisnika extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ObrisiKorisnika</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ObrisiKorisnika at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        HttpSession session=request.getSession();
        Korisnik korisnik=(Korisnik)session.getAttribute("korisnik");
        if(korisnik==null || !korisnik.getKorisnikRole().equals("Administrator")){
            request.setAttribute("errorMsg","Morate biti Administrator kako bi ste obrisali salu!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }        
        String korisnikId;
        try{
            korisnikId =request.getParameter("korisnikId");
            try{
                String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                String user="root";
                String pass="";
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn=DriverManager.getConnection(dbUrl,user,pass);

                Statement statement=conn.createStatement();
                String upit="DELETE FROM korisnik WHERE korisnikId="+korisnikId;
                try{
                    statement.executeUpdate(upit);
                }
                catch(SQLException sqle){ 
                    request.setAttribute("errorMsg","Greska prilikom brisanja korisnika! Pokusajte ponovo.</br>"+sqle);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                response.sendRedirect("SpisakKorisnika");
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
