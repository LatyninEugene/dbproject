package view;

import domain.UserType;
import control.UsersBean;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/secondpage","/secondpage.xhtml"})
public class SecondPageServlet extends HttpServlet {
    @Inject
    private UserCDI userCDI;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(UsersBean.findType(userCDI.getUser()).equals(UserType.user)){
            resp.sendRedirect("/secondpage.jsf");
        }else {
            resp.sendRedirect("/secondpageforadmin.jsf");
        }

    }
}
