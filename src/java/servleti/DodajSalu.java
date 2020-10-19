package servleti;

import beans.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class DodajSalu extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DodajSalu</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DodajSalu at " + request.getContextPath() + "</h1>");
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
        String bioskopId;
        try{
            bioskopId=(String)request.getAttribute("bioskopId");
            request.setAttribute("bioskopId", bioskopId);
            request.getRequestDispatcher("dodajSalu.jsp").forward(request, response);

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
        HttpSession session=request.getSession();
        Korisnik korisnik=(Korisnik)session.getAttribute("korisnik");
        if(korisnik==null || !korisnik.getKorisnikRole().equals("Administrator")){
            request.setAttribute("errorMsg","Morate biti Administrator kako bi ste dodali salu!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        String bioskopId;
        String salaNaziv;
        int salaRedovi;
        int salaKolone;
        try{
            bioskopId=(String)request.getParameter("bioskopId");
            salaNaziv=(String)request.getParameter("salaNaziv");
            salaRedovi=Integer.parseInt(request.getParameter("salaRedovi"));
            salaKolone=Integer.parseInt(request.getParameter("salaKolone"));
            try{
                String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                String user="root";
                String pass="";
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                Statement statement=conn.createStatement();
                int rezultat=0;
                String query="INSERT INTO sala(salaNaziv,salaRedovi,salaKolone,bioskopId) VALUES ('"+salaNaziv +"',"+ salaRedovi+","+ salaKolone+","+ bioskopId+")";
                try{
                    rezultat= statement.executeUpdate(query);
                }catch(SQLException sql){
                    request.setAttribute("errorMsg","Greska! Upis u tabelu 'sala' neuspesan!</br>"+sql);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                if(rezultat==1){
                    response.sendRedirect("SpisakSala");
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
    public String getServletInfo() {
        return "Short description";
    }

}
