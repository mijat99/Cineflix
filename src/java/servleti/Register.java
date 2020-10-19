package servleti;

import beans.*;
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Register extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Register</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Register at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        Korisnik korisnik =(Korisnik)session.getAttribute("korisnik");
        if(korisnik==null || korisnik.getKorisnikRole().equals("Administrator"))
        {   
            if(korisnik != null && korisnik.getKorisnikRole().equals("Administrator")){
                try{
                    String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                    String user="root";
                    String pass="";
                    Class.forName("com.mysql.jdbc.Driver");

                    Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                    Statement statement=conn.createStatement();
                    String upit="SELECT * FROM bioskop";
                    ResultSet rezultat=null;
                    try{
                        rezultat=statement.executeQuery(upit);
                    }
                    catch(SQLException sqle){        
                        request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'bioskop' neuspesna!</br>"+sqle);
                        request.getRequestDispatcher("index.jsp").forward(request, response);}
                    ArrayList<Bioskop> bioskopi=new ArrayList<>();
                    while(rezultat.next()){
                        Bioskop b = new Bioskop();
                        b.setBioskopId(Integer.parseInt(rezultat.getString("bioskopId")));
                        b.setBioskopNaziv(rezultat.getString("bioskopNaziv"));
                        bioskopi.add(b);
                    }
                    request.setAttribute("bioskopi", bioskopi);
                }
                catch(ClassNotFoundException cnfe){
                request.setAttribute("errorMsg","Greska! Klasa baze nije pronadjena!</br>"+cnfe);
                request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                catch(SQLException sqle){ 
                    request.setAttribute("errorMsg","Greska prilikom pretrage baze!</br>"+sqle);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                catch(Exception e){
                    request.setAttribute("errorMsg","Nisu prosledjeni dobiri parametri!</br>"+e);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            }
            request.getRequestDispatcher("register.jsp").forward(request,response);
        }
        else{
            request.setAttribute("errorMsg", "Vec ste ulogovani!");
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String passwordConf=request.getParameter("passwordConf");
        String role=request.getParameter("role");
        String bioskopId=request.getParameter("bioskopId");
        if(username==null || username.trim().equals("") || password==null || password.trim().equals("") 
                || passwordConf==null || passwordConf.trim().equals("") || role==null || role.trim().equals("") 
                || bioskopId==null || bioskopId.trim().equals(""))
        {
            request.setAttribute("errorMsg","Niste popunili sva polja!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
        if(password.trim().equals(passwordConf.trim()))
        {        
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
                    request.getRequestDispatcher("index.jsp").forward(request, response);}
                if(rezultat.next()){
                    request.setAttribute("errorMsg","Korisnicko ime je vec zauzeto!");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
                else{
                    statement.close();
                    rezultat.close();
                    statement=conn.createStatement();
                    String shaPass=SHA1.StringToSha1(password);
                    upit="INSERT INTO korisnik(korisnikUsername,korisnikPassword,korisnikRole,bioskopId) VALUES('"+username+"','"+shaPass+"','"+role+"',+"+bioskopId+")";
                    try{
                        statement.executeUpdate(upit);
                    }catch(SQLException sqle){
                        request.setAttribute("errorMsg","Greska! Upis u tabelu 'korisnik' neuspesan!</br>"+sqle);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                    HttpSession session=request.getSession();
                    Korisnik korisnik = (Korisnik)session.getAttribute("korisnik");
                    if(korisnik!=null && korisnik.getKorisnikRole().equals("Administrator")){
                        request.setAttribute("errorMsg", "Uspesno dodat novi korisnik!");
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    }
                    response.sendRedirect("Login");          
                }
            }
            catch(ClassNotFoundException cnfe){
            request.setAttribute("errorMsg","Greska! Klasa baze nije pronadjena!</br>"+cnfe);
            request.getRequestDispatcher("register.jsp").forward(request, response);
            }
            catch(SQLException e){ 
                request.setAttribute("errorMsg","Greska prilikom pretrage baze!</br>"+e);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
            catch(Exception e){
                request.setAttribute("errorMsg","Greska! Pogresni parametri su prosledjeni!</br>"+e);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
