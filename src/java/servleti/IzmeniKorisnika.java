package servleti;

import beans.Bioskop;
import beans.Korisnik;
import beans.SHA1;
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
public class IzmeniKorisnika extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet IzmeniKorisnika</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet IzmeniKorisnika at " + request.getContextPath() + "</h1>");
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
            request.setAttribute("errorMsg","Morate biti Administrator kako bi ste izmenili korisnika!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        String korisnikId;
        try{
            korisnikId=(String)request.getParameter("korisnikId");
            try{
                String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                String user="root";
                String pass="";
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                Statement statement=conn.createStatement();
                
                ResultSet rezultat=null;
                String upit="SELECT * FROM korisnik WHERE korisnikId="+korisnikId;
                try{
                    rezultat=statement.executeQuery(upit);
                }
                catch(SQLException sqle){ 
                    request.setAttribute("errorMsg","Greska prilikom pretrage projekcije! Pokusajte ponovo.</br>"+sqle);
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                Korisnik izmena=new Korisnik();
                if(rezultat.next()){
                    izmena.setKorisnikId(Integer.parseInt(rezultat.getString("korisnikId")));
                    izmena.setKorisnikUsername(rezultat.getString("korisnikUsername"));
                    izmena.setKorisnikPassword(rezultat.getString("korisnikPassword"));
                    izmena.setKorisnikRole(rezultat.getString("korisnikRole"));
                    izmena.setBioskopId(Integer.parseInt(rezultat.getString("bioskopId")));
                }
                else{
                    request.setAttribute("errorMsg","Nije pronadjen korisnik sa prosledjenim id-em");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
                statement.close();
                rezultat.close();
                statement=conn.createStatement();
                upit="SELECT * FROM bioskop";
                rezultat=null;
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
                request.setAttribute("korisnik",izmena);
                request.getRequestDispatcher("izmeniKorisnika.jsp").forward(request, response);
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
        String korisnikId=request.getParameter("korisnikId");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String passwordConf=request.getParameter("passwordConf");
        String role=request.getParameter("role");
        String bioskopId=request.getParameter("bioskopId");
        if(username==null || username.trim().equals("") || role==null || role.trim().equals("") || bioskopId==null || bioskopId.trim().equals(""))
        {
            request.setAttribute("errorMsg","Niste popunili sva polja!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
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
                request.getRequestDispatcher("index.jsp").forward(request, response);}
            if(rezultat.next()){
                if(!rezultat.getString("korisnikUsername").trim().equals(username.trim()))
                {
                    request.setAttribute("errorMsg","Korisnicko ime je vec zauzeto!");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                }
            }
            statement.close();
            rezultat.close();
            statement=conn.createStatement();
            upit="UPDATE korisnik set korisnikUsername='"+username+"', korisnikRole='"+role+"', bioskopId="+bioskopId;
            if(password!= null && passwordConf != null && !password.trim().equals("") && !passwordConf.trim().equals("") && password.trim().equals(passwordConf.trim()))
            {   
                String shaPass=SHA1.StringToSha1(password);
                upit+=",korisnikPassword='"+shaPass+"'";
            }
            upit+= " WHERE korisnikId="+korisnikId;
            try{
                statement.executeUpdate(upit);
            }catch(SQLException sqle){
                request.setAttribute("errorMsg","Greska! Izmena u tabeli 'korisnik' neuspesna!</br>"+sqle);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            response.sendRedirect("SpisakKorisnika");          
            
        }
        catch(ClassNotFoundException cnfe){
        request.setAttribute("errorMsg","Greska! Klasa baze nije pronadjena!</br>"+cnfe);
        request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        catch(SQLException e){ 
            request.setAttribute("errorMsg","Greska prilikom pretrage baze!</br>"+e);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        catch(Exception e){
            request.setAttribute("errorMsg","Greska! Pogresni parametri su prosledjeni!</br>"+e);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
