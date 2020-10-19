package servleti;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import beans.*;
import java.util.ArrayList;
import viewModels.*;
public class SpisakBioskopa extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SpisakBioskopa</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SpisakBioskopa at " + request.getContextPath() + "</h1>");
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
            request.setAttribute("errorMsg","Morate biti administrator kako bi pristupili spisku bioskopa!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
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
                ArrayList<Bioskop> b= new ArrayList<>();
                while(rezultat.next()){
                    Bioskop bioskop = new Bioskop();
                    bioskop.setBioskopId(Integer.parseInt(rezultat.getString("bioskopId")));
                    bioskop.setBioskopNaziv(rezultat.getString("bioskopNaziv"));
                    bioskop.setBioskopAdresa(rezultat.getString("bioskopAdresa"));
                    bioskop.setBioskopTelefon(rezultat.getString("bioskopTelefon"));
                    bioskop.setBioskopBanner(rezultat.getString("bioskopBanner"));
                    bioskop.setBioskopCena2D(Float.parseFloat(rezultat.getString("bioskopCena2D")));
                    bioskop.setBioskopCena3D(Float.parseFloat(rezultat.getString("bioskopCena3D")));
                    b.add(bioskop);
                }
                request.setAttribute("bioskopi",b);
                request.getRequestDispatcher("spisakBioskopa.jsp").forward(request, response);
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
