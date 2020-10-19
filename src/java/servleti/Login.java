package servleti;

import beans.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Login</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Login at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        Korisnik korisnik =(Korisnik)session.getAttribute("korisnik");
        if(korisnik==null || korisnik.getKorisnikUsername().trim().equals("")){
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
        else
        {
            request.setAttribute("errorMsg","Vec ste ulogovani!");
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        if(username==null || username.trim().equals("") || password==null || password.trim().equals(""))
        {
            request.setAttribute("errorMsg","Niste popunili sva polja!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        try{
            String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
            String user="root";
            String pass="";
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn=DriverManager.getConnection(dbUrl,user,pass);
            Statement statement=conn.createStatement();
            String upit="SELECT * FROM korisnik WHERE korisnikUsername='"+username+"'";
            ResultSet rezultat=null;
            try{
                rezultat=statement.executeQuery(upit);
            }
            catch(SQLException sqle){        
                request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'korisnik' neuspesna!</br>"+sqle);
                request.getRequestDispatcher("login.jsp").forward(request, response);}
            if(rezultat.next()){
                String shaPass=SHA1.StringToSha1(password);
                
                Korisnik korisnik=new Korisnik();
                korisnik.setKorisnikId(Integer.parseInt(rezultat.getString("korisnikId")));
                korisnik.setKorisnikUsername(rezultat.getString("korisnikUsername"));
                korisnik.setKorisnikPassword(rezultat.getString("korisnikPassword"));
                korisnik.setKorisnikRole(rezultat.getString("korisnikRole"));
                korisnik.setBioskopId(Integer.parseInt(rezultat.getString("bioskopId")));
                
                if(shaPass.equals(korisnik.getKorisnikPassword()))
                {
                    HttpSession session=request.getSession();
                    session.setAttribute("korisnik",korisnik);
                    response.sendRedirect("index.jsp");
                    
                }
                else
                {
                    request.setAttribute("errorMsg","Lozinka se ne poklapa! Pokusajte ponovo.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }
            else{
                request.setAttribute("errorMsg","Ne postoji korisnik sa datim korisnickim imenom!");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
        catch(ClassNotFoundException cnfe){
        request.setAttribute("errorMsg","Greska! Klasa baze nije pronadjena!</br>"+cnfe);
        request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        catch(SQLException e){ 
            request.setAttribute("errorMsg","Greska prilikom pretrage baze!</br>"+e);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        catch(Exception e){
            request.setAttribute("errorMsg","Greska! Pogresni parametri su prosledjeni!</br>"+e);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
