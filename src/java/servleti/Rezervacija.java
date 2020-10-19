package servleti;
import beans.*;
import viewModels.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Rezervacija extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Rezervacija</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Rezervacija at " + request.getContextPath() + "</h1>");
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
       if(korisnik == null){
           response.sendRedirect(request.getHeader("referer"));
       }
       else{
           String projekcijaId;
           String [] sedista;
           float ukCena;
           try{
               projekcijaId=(String)request.getParameter("projekcijaId");
               sedista=(String[])request.getParameterValues("checkbox");
               ukCena=Float.parseFloat(request.getParameter("ukCena"));
               String[] kolone=new String[sedista.length];
               String[] redovi=new String[sedista.length];
               for(int i=0;i<sedista.length;i++){
                    String[] split=sedista[i].split("-");
                    kolone[i]=split[1];
                    redovi[i]=split[0];
               }
               try{
                    String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                    String user="root";
                    String pass="";
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                    Statement statement;
                    String upit;
                    statement=conn.createStatement();
                    upit="SELECT rezervacijaId FROM rezervacija WHERE korisnikId="+korisnik.getKorisnikId()+" and projekcijaId="+projekcijaId+"";
                    ResultSet postojiRezervacija=null;
                    try{
                        postojiRezervacija=statement.executeQuery(upit);
                    }catch(SQLException sqle){        
                        request.setAttribute("errorMsg","Greska! Neuspesno citanje rezervacije iz tabele!</br>"+sqle);
                        request.getRequestDispatcher("index.jsp").forward(request, response);}
                    postojiRezervacija.last();
                    if(postojiRezervacija.getRow()==0){
                        statement.close();
                        statement=conn.createStatement();
                        upit="INSERT INTO rezervacija(rezervacijaCena,projekcijaId,korisnikId) VALUES("
                            +ukCena+","+projekcijaId+","+korisnik.getKorisnikId()+")";
                        int rez=0;
                        try{
                            rez=statement.executeUpdate(upit);
                        }
                        catch(SQLException sqle){        
                            request.setAttribute("errorMsg","Greska! Upis u tabelu 'rezervacija' neuspesan!</br>"+sqle);
                            request.getRequestDispatcher("index.jsp").forward(request, response);}
                        if(rez!=1){
                            request.setAttribute("errorMsg","Greska! Upis u tabelu 'rezervacija' neuspesan!</br>");
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    }
                    for(int i=0;i<sedista.length;i++)
                    {
                        statement.close();
                        statement=conn.createStatement();
                        upit="INSERT INTO rezervisanasedista(sedisteKolona,sedisteRed,rezervacijaId) VALUES("
                                +kolone[i]+","+redovi[i]+",(SELECT rezervacijaId FROM rezervacija WHERE korisnikId="+korisnik.getKorisnikId()+" and projekcijaId="+projekcijaId+"))";
                        ResultSet rezultat=null;
                        try{
                            statement.executeUpdate(upit);
                        }
                        catch(SQLException sqle){        
                            request.setAttribute("errorMsg","Greska! Upis u tabelu 'rezervisanasedista' neuspesan!</br>"+sqle);
                            request.getRequestDispatcher("index.jsp").forward(request, response);
                        }
                    }      
                    response.sendRedirect("MojeRezervacije");
                }
               catch(ClassNotFoundException cnfe){
                    request.setAttribute("errorMsg","Greska! Klasa baze nije pronadjena!</br>"+cnfe);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
               }
                
           }catch(NullPointerException npe){
               response.sendRedirect("IzborSedista?projekcijaId="+String.valueOf(17));
               return;
           }catch(Exception e){
               request.setAttribute("errorMsg", "Greska!<br/>"+e);
               request.getRequestDispatcher("index.jsp").forward(request, response);
           }
       }
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
