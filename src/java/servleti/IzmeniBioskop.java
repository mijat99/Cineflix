package servleti;

import beans.Bioskop;
import beans.Korisnik;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class IzmeniBioskop extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        Korisnik korisnik=(Korisnik)session.getAttribute("korisnik");
        if(korisnik==null || !korisnik.getKorisnikRole().trim().equals("Administrator"))
        {
            request.setAttribute("errorMsg","Morate biti administrator kako bi izmenili bioskop!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        String bioskopId;
        try{
            bioskopId=request.getParameter("bioskopId");
            try{
                String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                String user="root";
                String pass="";
                Class.forName("com.mysql.jdbc.Driver");

                Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                Statement statement=conn.createStatement();
                String upit="SELECT * FROM bioskop WHERE bioskopId="+bioskopId;
                ResultSet rezultat=null;
                try{
                    rezultat=statement.executeQuery(upit);
                }
                catch(SQLException sqle){        
                    request.setAttribute("errorMsg","Greska! Pretraga u tabeli 'bioskop' neuspesna!</br>"+sqle);
                    request.getRequestDispatcher("index.jsp").forward(request, response);}
                if(rezultat.next()){
                    Bioskop bioskop=new Bioskop();
                    bioskop.setBioskopId(Integer.parseInt(rezultat.getString("bioskopId")));
                    bioskop.setBioskopNaziv(rezultat.getString("bioskopNaziv"));
                    bioskop.setBioskopAdresa(rezultat.getString("bioskopAdresa"));
                    bioskop.setBioskopTelefon(rezultat.getString("bioskopTelefon"));
                    bioskop.setBioskopBanner(rezultat.getString("bioskopBanner"));
                    bioskop.setBioskopCena2D(Float.parseFloat(rezultat.getString("bioskopCena2D")));
                    bioskop.setBioskopCena3D(Float.parseFloat(rezultat.getString("bioskopCena3D")));
                    
                    request.setAttribute("bioskop",bioskop);
                    request.getRequestDispatcher("izmenaBioskopa.jsp").forward(request, response);
                }
                else{
                    request.setAttribute("errorMsg","Nije pronadjen bioskop sa prosledjenim id-em!");
                    request.getRequestDispatcher("SpisakBioskopa").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        Korisnik korisnik=(Korisnik)session.getAttribute("korisnik");
        if(korisnik==null || !korisnik.getKorisnikRole().equals("Administrator")){
            request.setAttribute("errorMsg","Morate biti Administrator kako bi ste izmenili bioskop!");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        String pathZaUpis="";
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        String filePath;
        String nazivF;
        ArrayList<String> podaci = new ArrayList<String>();
        boolean izabranFile=true;

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
                        if(fileName.equals("")){
                            izabranFile=false;
                        }
                        else
                        {
                            String root = getServletContext().getRealPath("/");
                            File path = new File(root + "/images/bioskopi");
                            if (!path.exists())
                            {
                                boolean status = path.mkdirs();
                            }
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
                            LocalDateTime now = LocalDateTime.now();
                            pathZaUpis="images/bioskopi/"+dtf.format(now)+"-"+fileName;
                            File uploadedFile = new File(path + "/" + dtf.format(now) +"-"+ fileName);
                            filePath = uploadedFile.getCanonicalPath();
                            nazivF = uploadedFile.getName();

                            item.write(uploadedFile);
                        }
                        
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
        String bioskopId=podaci.get(0);
        String bioskopNaziv=podaci.get(1);
        String bioskopAdresa=podaci.get(2);
        String bioskopTelefon=podaci.get(3);
        String bioskopCena2D=podaci.get(4);
        String bioskopCena3D=podaci.get(5);
        String stariFilePath=podaci.get(6);
        try{
            String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
            String user="root";
            String pass="";
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn=DriverManager.getConnection(dbUrl,user,pass);
            Statement statement=conn.createStatement();
            int rezultat=0;
            String upit="UPDATE bioskop SET bioskopNaziv='"+bioskopNaziv+"', bioskopAdresa='"+bioskopAdresa
                    +"',bioskopTelefon='"+bioskopTelefon+"', bioskopCena2D="+bioskopCena2D+", bioskopCena3D="+bioskopCena3D+" ";
            if(izabranFile){
                upit+=", bioskopBanner='"+pathZaUpis+"' ";
            }
            upit+=" WHERE bioskopId="+bioskopId;
            try{
                rezultat= statement.executeUpdate(upit);
            }catch(SQLException sql){
                request.setAttribute("errorMsg","Greska! Upis u tabelu 'bioskop' neuspesan!</br>"+sql);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
            if(rezultat==1){
                if(izabranFile){
                    try
                    {
                        String path = getServletContext().getRealPath("/");
                        String nov = stariFilePath.replace("/", "\\");
                        path+=nov;
                        File f = new File(path);
                        f.delete();
                    }
                    catch(Exception e)
                    {
                        response.sendRedirect("index.jsp");
                    }
                }
                response.sendRedirect("SpisakBioskopa");
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
