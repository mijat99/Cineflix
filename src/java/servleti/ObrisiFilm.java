package servleti;

import beans.Korisnik;
import java.io.File;
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
public class ObrisiFilm extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ObrisiFilm</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ObrisiFilm at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session= request.getSession();
        Korisnik korisnik=(Korisnik)session.getAttribute("korisnik");
        if(korisnik==null || !korisnik.getKorisnikRole().equals("Administrator")){
            request.setAttribute("errorMsg","Morate biti Administrator kako bi ste obrisali salu!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }        
        String filmId;
        String pathSlika;
        try{
            filmId =request.getParameter("filmId");
            pathSlika=request.getParameter("pathSlika");
            try{
                String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                String user="root";
                String pass="";
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn=DriverManager.getConnection(dbUrl,user,pass);

                Statement statement=conn.createStatement();
                String upit="DELETE FROM film WHERE filmId="+filmId;
                int rezultat=0;
                try{
                    rezultat=statement.executeUpdate(upit);
                }
                catch(SQLException sqle){ 
                    request.setAttribute("errorMsg","Greska prilikom brisanja filma! Pokusajte ponovo.</br>"+sqle);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                if(rezultat==1){
                    try
                {
                    String path = getServletContext().getRealPath("/");
                    String nov = pathSlika.replace("/", "\\");
                    path+=nov;
                    File f = new File(path);
                    f.delete();
                }
                catch(Exception e)
                {
                    response.sendRedirect("index.jsp");
                }
                response.sendRedirect("SpisakFilmova");
            }
            else{
                request.setAttribute("errorMsg", "Greska prilikom upisa!");
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
