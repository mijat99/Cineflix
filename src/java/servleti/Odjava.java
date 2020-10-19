package servleti;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import beans.*;

public class Odjava extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Odjava</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Odjava at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        Korisnik korisnik = (Korisnik)session.getAttribute("korisnik");
        if(korisnik==null)
        {
            response.sendRedirect(request.getHeader("referer"));
        }
        else
        {
            session.invalidate();
            response.sendRedirect(request.getHeader("referer"));
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
