package view;


import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Inject
    private UserCDI userCDI;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        if((userCDI != null) && (userCDI.getLogin()!=null)){
            session.setAttribute("user",userCDI.getLogin());
        }else session.setAttribute("user",null);

        if(session.getAttribute("user")==null){
            resp.sendRedirect(req.getContextPath()+"/login.jsf");
            return;
        }
        resp.sendRedirect(req.getContextPath()+"/index");
    }
}
