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
public class SpisakSala extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SpisakSala</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SpisakSala at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        Korisnik korisnik=(Korisnik)session.getAttribute("korisnik");
        if(korisnik==null || !korisnik.getKorisnikRole().trim().equals("Administrator"))
        {
            request.setAttribute("errorMsg","Morate biti administrator kako bi pristupili spisku sala!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        try{
            String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
            String user="root";
            String pass="";
            Class.forName("com.mysql.jdbc.Driver");

            Connection conn=DriverManager.getConnection(dbUrl,user,pass);
            Statement statement=conn.createStatement();
            String upit="SELECT * FROM sala ORDER BY bioskopId ASC, salaId ASC";
            ResultSet rezultat=null;
            try{
                rezultat=statement.executeQuery(upit);
            }
            catch(SQLException sqle){        
                request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'sala' neuspesna!</br>"+sqle);
                request.getRequestDispatcher("index.jsp").forward(request, response);}
            ArrayList<Sala> s= new ArrayList<>();
            while(rezultat.next()){
                Sala sala=new Sala();
                sala.setSalaId(Integer.parseInt(rezultat.getString("salaId")));
                sala.setSalaKolone(Integer.parseInt(rezultat.getString("salaKolone")));
                sala.setSalaRedovi(Integer.parseInt(rezultat.getString("salaRedovi")));
                sala.setBioskopId(Integer.parseInt(rezultat.getString("bioskopId")));
                sala.setSalaNaziv(rezultat.getString("salaNaziv"));

                Statement stmt=conn.createStatement();
                ResultSet rez=null;
                String query="SELECT * FROM bioskop WHERE bioskopId="+rezultat.getString("bioskopId");
                try{
                    rez=stmt.executeQuery(query);
                }catch(SQLException sqle){        
                    request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'sala' neuspesna!</br>"+sqle);
                    request.getRequestDispatcher("index.jsp").forward(request, response);}
                if(rez.next()){
                    Bioskop bioskop = new Bioskop();
                    bioskop.setBioskopId(Integer.parseInt(rez.getString("bioskopId")));
                    bioskop.setBioskopNaziv(rez.getString("bioskopNaziv"));
                    bioskop.setBioskopAdresa(rez.getString("bioskopAdresa"));
                    bioskop.setBioskopTelefon(rez.getString("bioskopTelefon"));
                    bioskop.setBioskopBanner(rez.getString("bioskopBanner"));
                    bioskop.setBioskopCena2D(Float.parseFloat(rez.getString("bioskopCena2D")));
                    bioskop.setBioskopCena3D(Float.parseFloat(rez.getString("bioskopCena3D")));
                    sala.setBioskop(bioskop);
                }
                s.add(sala);
            }
            request.setAttribute("sale",s);
            request.getRequestDispatcher("spisakSala.jsp").forward(request, response);
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
