package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.SQLException;
import java.sql.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html;charset=UTF-8");
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
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html lang=\"en\">\n");
      out.write("<head>\n");
      out.write("  <meta charset=\"utf-8\">\n");
      out.write("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">  \n");
      out.write("  <title>Cineflix</title>\n");
      out.write("     <link rel=\"stylesheet\" href=\"assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css\">\n");
      out.write("    \n");
      out.write("    <link rel=\"stylesheet\" href=\"assets/css/style.css\">\n");
      out.write("    \n");
      out.write("    <link rel=\"stylesheet\" href=\"assets/css/kartice.css\" />\n");
      out.write("    \n");
      out.write("    <link rel=\"stylesheet\" href=\"assets/css/navbar.css\" />\n");
      out.write("\n");
      out.write("</head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("  <!-- Navigation -->\n");
      out.write("            ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/navbar.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("title", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("Main title", request.getCharacterEncoding()), out, false);
      out.write("\n");
      out.write("    \n");
      out.write("    <img style='width: 100%; height: 50%; opacity: 0.9' src=\"images/reg/pocetna.jpg\">\n");
      out.write("  \n");
      out.write("        <br>\n");
      out.write("    <h1>&emsp; Izdvajamo</h1>\n");
      out.write("        <br>\n");
      out.write("    <section class=\"sadrzaj\">\n");
      out.write("                    ");

                        try{
                            String dbUrl="jdbc:mysql://localhost:3306/onlinerezervacija";
                            String user="root";
                            String pass="";
                            Class.forName("com.mysql.jdbc.Driver");
        
                            Connection conn=DriverManager.getConnection(dbUrl,user,pass);
                            Statement statement=conn.createStatement();
                            String upit="SELECT * FROM film ORDER BY filmGodina DESC, filmNaziv ASC";
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
                                int i=0;
                                
      out.write("\n");
      out.write("                                <div class=\"kartice\">");

                                while(rezultat.next())
                                {
                                    if(i<3){                                        
                                    
      out.write("<div class=\"kartica\" id=\"");
      out.print( rezultat.getString("filmId") );
      out.write("\" >\n");
      out.write("                                        <h2>");
      out.print( rezultat.getString("filmNaziv") );
      out.write("</h2>\n");
      out.write("                                        <p>Zanrovi: ");
      out.print( rezultat.getString("filmZanr"));
      out.write("</p>\n");
      out.write("                                        <p>Godina: ");
      out.print( rezultat.getString("filmGodina"));
      out.write(" </p>\n");
      out.write("                                        <img src=\"");
      out.print( rezultat.getString("filmPoster") );
      out.write("\" alt=\"\"/>\n");
      out.write("                                    </div>");

                                    }
                                    i++;
                                }   
                                
      out.write("</div>");

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
                        
      out.write("                        \n");
      out.write("    </section>\n");
      out.write("\n");
      out.write("\n");
      out.write("  <!-- Bootstrap core JavaScript -->\n");
      out.write("  <script src=\"vendor/jquery/jquery.min.js\"></script>\n");
      out.write("  <script src=\"vendor/bootstrap/js/bootstrap.bundle.min.js\"></script>\n");
      out.write("  <script src=\"assets/js/jquery.min.js\"></script>\n");
      out.write("  <script src=\"assets/js/browser.min.js\"></script>\n");
      out.write("  <script src=\"assets/js/breakpoints.min.js\"></script>\n");
      out.write("  <script src=\"assets/js/util.js\"></script>\n");
      out.write("  <script src=\"assets/js/main.js\"></script>\n");
      out.write("  <script>\n");
      out.write("        $(\".kartica\").on(\"click\",function(event){\n");
      out.write("            location.href=\"/Cineflix/IzborBioskopa?id=\"+$(this).attr(\"id\");\n");
      out.write("        });\n");
      out.write("    </script>\n");
      out.write("    <script>\n");
      out.write("        if($(\"body\").height()<$(window).height())\n");
      out.write("            {\n");
      out.write("                $(\"body\").height($(window).height())\n");
      out.write("            }\n");
      out.write("    </script>\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("\n");
      out.write("</html>\n");
      out.write("\n");
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
