package servleti;

import beans.Korisnik;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

@MultipartConfig
public class DodajFilm extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet DodajFilm</title>");        
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DodajFilm at " + request.getContextPath() + "</h1>");
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
            request.setAttribute("errorMsg","Morate biti administrator kako bi dodali film!");
            request.getRequestDispatcher("SpisakFilmova").forward(request, response);
        }
        request.getRequestDispatcher("dodajFilm.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        Korisnik korisnik=(Korisnik)session.getAttribute("korisnik");
        if(korisnik==null || !korisnik.getKorisnikRole().equals("Administrator")){
            request.setAttribute("errorMsg","Morate biti Administrator kako bi ste dodali film!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        String pathZaUpis="";
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        String filePath;
        String nazivF;
        ArrayList<String> podaci = new ArrayList<String>();

        if (isMultipart)
        {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try
            {
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext())
                {
                    FileItem item = (FileItem) iterator.next();

                    if (!item.isFormField())
                    {
                        String fileName = item.getName();

                        String root = getServletContext().getRealPath("/");
                        File path = new File(root + "/images/filmovi");
                        if (!path.exists())
                        {
                            boolean status = path.mkdirs();
                        }
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
                        LocalDateTime now = LocalDateTime.now();
                        pathZaUpis="images/filmovi/"+dtf.format(now)+"-"+fileName;
                        File uploadedFile = new File(path + "/" + dtf.format(now) +"-"+ fileName);
                        filePath = uploadedFile.getCanonicalPath();
                        nazivF = uploadedFile.getName();

                        item.write(uploadedFile);
                    }
                    else
                    {
                        podaci.add(item.getString());
                    }
                }
            }
            catch (FileUploadException e)
            {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        String filmNaziv=podaci.get(0);
        String filmGodina=podaci.get(1);
        String filmZanr=podaci.get(2);
        String filmReziser=podaci.get(3);
        try{
            String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
            String user="root";
            String pass="";
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection(dbUrl,user,pass);
            Statement statement=conn.createStatement();
            int rezultat=0;
            String query="INSERT INTO film(filmNaziv,filmGodina,filmZanr,filmReziser,filmPoster) VALUES ('"+filmNaziv +"','"+ filmGodina+"','"+ filmZanr+"','"+ filmReziser+"','"+pathZaUpis+"')";
            try{
                rezultat= statement.executeUpdate(query);
            }catch(SQLException sql){
                request.setAttribute("errorMsg","Greska! Upis u tabelu 'film' neuspesan!</br>"+sql);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            if(rezultat==1){
                response.sendRedirect("SpisakFilmova");
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
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
