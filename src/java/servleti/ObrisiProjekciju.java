package servleti;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;
import java.sql.*;
public class ObrisiProjekciju extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ObrisiProjekciju</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ObrisiProjekciju at " + request.getContextPath() + "</h1>");
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
        if(korisnik==null || !korisnik.getKorisnikRole().equals("Menadzer")){
            request.setAttribute("errorMsg","Morate biti menadzer kako bi ste obrisali projekciju!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        String projekcijaId;
        try{
            projekcijaId =request.getParameter("projekcijaId");
            try{
                String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                String user="root";
                String pass="";
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn=DriverManager.getConnection(dbUrl,user,pass);

                Statement statement=conn.createStatement();
                String upit="DELETE FROM projekcija WHERE projekcijaId="+projekcijaId;
                try{
                    statement.executeUpdate(upit);
                }
                catch(SQLException sqle){ 
                    request.setAttribute("errorMsg","Greska prilikom brisanja projekcije! Pokusajte ponovo.</br>"+sqle);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                
                request.setAttribute("errorMsg","Uspesno obrisana projekcija!");
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
