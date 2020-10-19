package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import beans.*;
import java.sql.*;

public final class navbar_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("    <div class=\"heder\">\n");
      out.write("        <img class=\"logo\" src=\"images/reg/logoCineflex.png\">\n");
      out.write("\t\t<nav>\n");
      out.write("\t\t\t<ul class=\"nav_links\">\n");
      out.write("\t\t\t\t<li class=\"dropbtn\"><a>Bioskopi</a>\n");
      out.write("\t\t\t\t\t");

                                                        try{
                                                            String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                                                            String user="root";
                                                            String pass="";
                                                            Class.forName("com.mysql.jdbc.Driver");
        
                                                            Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                                                            Statement statement=conn.createStatement();
                                                            String upit="SELECT * FROM bioskop ORDER BY bioskopNaziv ASC";
                                                            ResultSet rezultat=null;
                                                            try{
                                                                rezultat=statement.executeQuery(upit);
                                                            }
                                                            catch(SQLException sqle){
                                                                
      out.write("<h1>Greska u izvrsavanju upita!<br/> ");
      out.print( sqle );
      out.write("</h1>");

                                                            }
                                                            if(rezultat!=null){
                                                            
      out.write("<ul class=\"dropdown-content\">");

                                                            while(rezultat.next()){                          
                                                                
      out.write("<li><a href=\"IzborFilma?id=");
      out.print( rezultat.getString("bioskopId") );
      out.write("\"> ");
      out.print( rezultat.getString("bioskopNaziv"));
      out.write("</li>");

                                                            }   
                                                            
      out.write("</ul>");

                                                            }
                                                        }
                                                        catch(ClassNotFoundException cnfe){ 
      out.write("<h1>Greska u povezivanju klasa nije pronadjena!<br/> ");
      out.print( cnfe );
      out.write("</h1>");
 }
                                                        catch(SQLException e){ 
      out.write("<h1>Greska u povezivanju, baza nije pronadjena ili su proslednjeni pogresni parametri!<br/> ");
      out.print( e );
      out.write(" </h1>");
 }
                                                        
      out.write(" \n");
      out.write("\t\t\t\t</li>\n");
      out.write("\t\t\t\t<li class=\"dropbtn\"><a>Repertoar</a>\n");
      out.write("\t\t\t\t\t\t");

                                                        try{
                                                            String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                                                            String user="root";
                                                            String pass="";
                                                            Class.forName("com.mysql.jdbc.Driver");
        
                                                            Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                                                            Statement statement=conn.createStatement();
                                                            String upit="SELECT * FROM film";
                                                            ResultSet rezultat=null;
                                                            try{
                                                                rezultat=statement.executeQuery(upit);
                                                            }
                                                            catch(SQLException sqle){
                                                                
      out.write("<h1>Greska u izvrsavanju upita!<br/> ");
      out.print( sqle );
      out.write("</h1>");

                                                            }
                                                            if(rezultat!=null){
                                                            
      out.write("<ul class=\"dropdown-content\">");

                                                            while(rezultat.next()){                                    
                                                                
      out.write("<li><a href=\"IzborBioskopa?id=");
      out.print( rezultat.getString("filmId") );
      out.write("\"> ");
      out.print( rezultat.getString("filmNaziv"));
      out.write("</li>");

                                                            }   
                                                            
      out.write("</ul>");

                                                            }
                                                        }
                                                        catch(ClassNotFoundException cnfe){ 
      out.write("<h1>Greska u povezivanju klasa nije pronadjena!<br/> ");
      out.print( cnfe );
      out.write("</h1>");
 }
                                                        catch(SQLException e){ 
      out.write("<h1>Greska u povezivanju, baza nije pronadjena ili su proslednjeni pogresni parametri!<br/> ");
      out.print( e );
      out.write(" </h1>");
 }
                                                        
      out.write("\n");
      out.write("\t\t\t\t</li>\n");
      out.write("\t\t\t\t<li><a href=\"login.jsp\">Login</li>\n");
      out.write("\t\t\t\t<li><a href=\"register.jsp\">Register</li>\n");
      out.write("\t\t\t</ul>\n");
      out.write("\t\t</nav>\n");
      out.write("    </div>");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
