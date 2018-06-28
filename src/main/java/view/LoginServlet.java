package view;


import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login", "/login.xhtml"})
public class LoginServlet extends HttpServlet {

    @Inject
    private UserCDI userCDI;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        if(!userCDI.checkSessionUser(session)){
                resp.sendRedirect("/login.jsf");
                return;
        }
        resp.sendRedirect("/index");
    }
}
