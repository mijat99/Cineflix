package servleti;

import beans.Bioskop;
import beans.Korisnik;
import beans.Sala;
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
public class IzmeniSalu extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet IzmeniSalu</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet IzmeniSalu at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        Korisnik korisnik=(Korisnik)session.getAttribute("korisnik");
        if(korisnik==null || !korisnik.getKorisnikRole().equals("Administrator")){
            request.setAttribute("errorMsg","Morate biti Administrator kako bi ste dodali salu!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        String salaId;
        try{
            salaId=(String)request.getParameter("salaId");
            try{
                String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                String user="root";
                String pass="";
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                Statement statement=conn.createStatement();
                
                ResultSet rezultat=null;
                String upit="SELECT * FROM sala WHERE salaId="+salaId;
                try{
                    rezultat=statement.executeQuery(upit);
                }
                catch(SQLException sqle){ 
                    request.setAttribute("errorMsg","Greska prilikom pretrage projekcije! Pokusajte ponovo.</br>"+sqle);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                Sala sala= new Sala();
                if(rezultat.next()){
                    sala.setSalaId(Integer.parseInt(rezultat.getString("salaId")));
                    sala.setSalaKolone(Integer.parseInt(rezultat.getString("salaKolone")));
                    sala.setSalaRedovi(Integer.parseInt(rezultat.getString("salaRedovi")));
                    sala.setBioskopId(Integer.parseInt(rezultat.getString("bioskopId")));
                    sala.setSalaNaziv(rezultat.getString("salaNaziv"));
                }
                else{
                    request.setAttribute("errorMsg","Nije pronadjena sala sa prosledjenim id-em");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                request.setAttribute("sala",sala);
                request.getRequestDispatcher("izmeniSalu.jsp").forward(request, response);
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
        catch(IllegalArgumentException iae){
            request.setAttribute("errorMsg","Nisu prosledjeni svi parametri!<br/>"+iae);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } 
        catch(Exception e){
            request.setAttribute("errorMsg","Greska!<br/>"+e);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String salaId;
        String bioskopId;
        String salaNaziv;
        String salaRedovi;
        String salaKolone;
        try{
            salaId=request.getParameter("salaId");
            salaNaziv=request.getParameter("salaNaziv");
            salaRedovi=request.getParameter("salaRedovi");
            salaKolone=request.getParameter("salaKolone");
            try{
                String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                String user="root";
                String pass="";
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                Statement statement=conn.createStatement();
                
                int rezultat=0;
                String upit="UPDATE sala SET salaNaziv='"+salaNaziv+"', salaRedovi="+salaRedovi+", salaKolone="+salaKolone+" WHERE salaId="+salaId;
                try{
                    rezultat=statement.executeUpdate(upit);
                }
                catch(SQLException sqle){ 
                    request.setAttribute("errorMsg","Greska prilikom pretrage projekcije! Pokusajte ponovo.</br>"+sqle);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                if(rezultat==1){
                    response.sendRedirect("SpisakSala");
                }
                else{
                    request.setAttribute("errorMsg","Neocekivan broj izmena u bazi! Moguca greska.");
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
            
            
        }catch(IllegalArgumentException iae){
            request.setAttribute("errorMsg","Nisu prosledjeni svi parametri!<br/>"+iae);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } 
        catch(Exception e){
            request.setAttribute("errorMsg","Greska!<br/>"+e);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
