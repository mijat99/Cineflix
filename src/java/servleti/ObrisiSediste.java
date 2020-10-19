package servleti;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class ObrisiSediste extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ObrisiSediste</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ObrisiSediste at " + request.getContextPath() + "</h1>");
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
        String sediste;
        String rezervacijaId;
        float cenaKarte;
        try{
            sediste=request.getParameter("sediste");
            rezervacijaId=request.getParameter("rezervacijaId");
            cenaKarte=Float.parseFloat(request.getParameter("cenaKarte"));
            String[] splice=sediste.split("-");
            try{
                String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                String user="root";
                String pass="";
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                Statement statement=conn.createStatement();
                String upit="DELETE FROM rezervisanasedista WHERE rezervacijaId="+rezervacijaId+" and sedisteRed="+splice[0]+" and sedisteKolona="+splice[1];
                String upit2= "UPDATE rezervacija SET rezervacijaCena=rezervacijaCena-"+String.valueOf(cenaKarte)+" WHERE rezervacijaId="+rezervacijaId+";";
                try{
                    statement.executeUpdate(upit);
                    statement.executeUpdate(upit2);
                }
                catch(SQLException sqle){ 
                    request.setAttribute("errorMsg","Greska prilikom brisanja sedista! Pokusajte ponovo."+sqle);
                    request.getRequestDispatcher("MojeRezervacije").forward(request, response);
                }
                statement.close();
                statement=conn.createStatement();
                upit="SELECT * FROM rezervisanasedista WHERE rezervacijaId="+rezervacijaId;
                ResultSet rezultat=null;
                try{
                    rezultat=statement.executeQuery(upit);
                }catch(SQLException e){ 
                    request.setAttribute("errorMsg","Greska prilikom pretrage baze!"+e);
                    request.getRequestDispatcher("MojeRezervacije").forward(request, response);
                }
                if(rezultat.next())
                {
                    request.setAttribute("errorMsg","Uspesno obrisano sediste !");
                    request.getRequestDispatcher("MojeRezervacije").forward(request, response);
                }
                else{
                    statement.close();
                    statement=conn.createStatement();
                    upit="DELETE FROM rezervacija WHERE rezervacijaId="+rezervacijaId;
                    try{
                        statement.executeUpdate(upit);
                    }catch(SQLException sqle){ 
                        request.setAttribute("errorMsg","Greska prilikom brisanja rezervacije nakon uklanjanja poslednjeg sedista!Pokusajte ponovo"+sqle);
                        request.getRequestDispatcher("MojeRezervacije").forward(request, response);
                    }
                    request.setAttribute("errorMsg","Uspesno obrisano sediste i rezervacija otkazana!");
                    request.getRequestDispatcher("MojeRezervacije").forward(request, response);
                }
                
                request.setAttribute("errorMsg","Uspesno obrisana rezervacija!");
                request.getRequestDispatcher("MojeRezervacije").forward(request, response);
            }
            catch(ClassNotFoundException cnfe){
                request.setAttribute("errorMsg","Greska! Klasa baze nije pronadjena!</br>"+cnfe);
                request.getRequestDispatcher("MojeRezervacije").forward(request, response);
            }
            catch(SQLException e){ 
                request.setAttribute("errorMsg","Greska prilikom pretrage baze!</br>"+e);
                request.getRequestDispatcher("MojeRezervacije").forward(request, response);
            }
        }
        catch(NullPointerException npe){
            request.setAttribute("errorMsg","Nije prosledjen parametar za brisanje rezervacije! Pokusajte ponovo.");
            request.getRequestDispatcher("MojeRezervacije").forward(request, response);
        }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
