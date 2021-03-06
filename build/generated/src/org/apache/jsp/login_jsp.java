package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("\t<head>\n");
      out.write("\t\t<meta charset=\"utf-8\">\n");
      out.write("\t\t<title>Login</title>\n");
      out.write("\t\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("\t\t<link rel=\"stylesheet\" href=\"assets/fonts/material-design-iconic-font/css/material-design-iconic-font.min.css\">\n");
      out.write("                <link href=\"assets/vendor/bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">\n");
      out.write("                <link href=\"assets/css/modern-business.css\" rel=\"stylesheet\">\n");
      out.write("                <link rel=\"stylesheet\" href=\"assets/css/navbar.css\" />\n");
      out.write("                <link rel=\"stylesheet\" href=\"assets/css/style.css\">\n");
      out.write("\t</head>\n");
      out.write("\n");
      out.write("\t<body>\n");
      out.write("            <!-- Navigation -->\n");
      out.write("            ");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "/navbar.jsp" + "?" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("title", request.getCharacterEncoding())+ "=" + org.apache.jasper.runtime.JspRuntimeLibrary.URLEncode("Main title", request.getCharacterEncoding()), out, false);
      out.write("\n");
      out.write("            \n");
      out.write("\t\t<div class=\"wrapper\">\n");
      out.write("\t\t\t<div class=\"inner\">\n");
      out.write("\t\t\t\t<div class=\"image-holder\">\n");
      out.write("\t\t\t\t\t<img src=\"images/reg/registracija.jpg\" alt=\"\">\n");
      out.write("\t\t\t\t</div>\n");
      out.write("\t\t\t\t<form method=\"post\" action=\"Login\">\n");
      out.write("\t\t\t\t\t<h3>Login</h3>\n");
      out.write("\t\t\t\t\t<div class=\"form-wrapper\">\n");
      out.write("\t\t\t\t\t\t<input type=\"text\" placeholder=\"Username\" class=\"form-control\" name=\"username\" id=\"username\" maxlength=\"50\" required=\"required\" onchange=\"return user()\">\n");
      out.write("\t\t\t\t\t\t<i class=\"zmdi zmdi-account\"></i>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<div class=\"form-wrapper\">\n");
      out.write("\t\t\t\t\t\t<input type=\"password\" placeholder=\"Password\" class=\"form-control\" name=\"password\" id=\"password\" required=\"required\" minlength=\"10\" maxlength=\"14\" onchange=\"return pass()\">\n");
      out.write("\t\t\t\t\t\t<i class=\"zmdi zmdi-lock\"></i>\n");
      out.write("\t\t\t\t\t</div>\n");
      out.write("\t\t\t\t\t<button type=\"submit\">Login\n");
      out.write("\t\t\t\t\t\t<i class=\"zmdi zmdi-arrow-right\"></i>\n");
      out.write("\t\t\t\t\t</button>\n");
      out.write("\t\t\t\t</form>\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<script type=\"text/javascript\">\n");
      out.write("\t\tfunction user()\n");
      out.write("\t\t{\n");
      out.write("\t\t\tvar user = /^[a-z\\d]+\\.?[a-z\\d]+\\@[a-z]{2,6}\\.[a-z]{2,6}$/;\n");
      out.write("\t\t\tvar tekst = document.getElementById('username').value;\n");
      out.write("\t\t\tvar rezultat = tekst.match(user);\n");
      out.write("\t\t\tif(document.getElementById('username').value==\"\")\n");
      out.write("\t\t\t{\n");
      out.write("\t\t\t\tdocument.getElementById('erroruser').innerHTML=\"\";\n");
      out.write("\t\t\t\tdocument.getElementById('registrujSe').disabled=false;\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\telse if(rezultat==null)\n");
      out.write("\t\t\t{\n");
      out.write("\t\t\t\tdocument.getElementById('erroruser').innerHTML=\"Korisnicko ime mora biti u formatu e-mail adrese! Sadrzi se od vaseg imena koje sme sadrzati '.', nakon toga '@' zatim sufiks email providera.<br/>\";\n");
      out.write("\t\t\t\tdocument.getElementById('registrujSe').disabled=true;\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\telse\n");
      out.write("\t\t\t{\n");
      out.write("\t\t\t\tdocument.getElementById('erroruser').innerHTML=\"\";\n");
      out.write("\t\t\t\tdocument.getElementById('registrujSe').disabled=false;\n");
      out.write("\t\t\t}\n");
      out.write("\t\t}\n");
      out.write("\n");
      out.write("\t\tfunction pass()\n");
      out.write("\t\t{\n");
      out.write("\t\t\tvar pass = /^[a-zA-Z]{6,10}[\\d]{3,5}[!@#$%^&*?.]{1}$/;\n");
      out.write("\t\t\tvar tekst = document.getElementById('password').value;\n");
      out.write("\t\t\tvar rezultat = tekst.match(pass);\n");
      out.write("\t\t\tif(document.getElementById('password').value==\"\")\n");
      out.write("\t\t\t{\n");
      out.write("\t\t\t\tdocument.getElementById('errorpass').innerHTML=\"\";\n");
      out.write("\t\t\t\tdocument.getElementById('registrujSe').disabled=false;\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\telse if(rezultat==null)\n");
      out.write("\t\t\t{\n");
      out.write("\t\t\t\tdocument.getElementById('errorpass').innerHTML=\"Lozinka mora da ima izmedju 6-10 karaktera, 3 cifre i jedan znak!<br/>\";\n");
      out.write("\t\t\t\tdocument.getElementById('registrujSe').disabled=true;\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\telse\n");
      out.write("\t\t\t{\n");
      out.write("\t\t\t\tdocument.getElementById('errorpass').innerHTML=\"\";\n");
      out.write("\t\t\t\tdocument.getElementById('registrujSe').disabled=false;\n");
      out.write("\t\t\t}\n");
      out.write("\t\t}\n");
      out.write("\t</script>\n");
      out.write("        <script>\n");
      out.write("            if($(\"body\").height()<$(window).height())\n");
      out.write("            {\n");
      out.write("                $(\"body\").height($(window).height())\n");
      out.write("            }\n");
      out.write("        </script>\n");
      out.write("\t</body>\n");
      out.write("</html>\n");
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
