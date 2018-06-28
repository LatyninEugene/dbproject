package view;

import control.EntryBean;
import control.UsersBean;
import domain.User;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class UserCDI implements Serializable {

    private String login;
    private String password;
    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getLogin() {
        if (user==null){
            return login;
        }
        return login+" ("+UsersBean.findType(user).toString()+")";
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkSessionUser(HttpSession session){
        if(session.getAttribute("user")!=null){
            return true;
        }
        return false;
    }

    private EntryBean eb = new EntryBean();

    public String createUser(){
        //String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        if(!checkUser()){
            if(eb.addUser(login,password)) {
                setSession();
            }
        }
        return "failure";
    }
    private void setSession(){
        user = UsersBean.getUser(login);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.getSession().setAttribute("user",login);
        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
        try {
            response.sendRedirect("/index");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean checkUser(){
       return eb.checkUser(login,password);
    }
    public String loginUser(){
        if(checkUser()){
            setSession();
        }
        return "failure";
    }

    public void exit(){
        login = null;
        password = null;
        user = null;
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        request.getSession().setAttribute("user",null);
        redirect("/login");
    }

    public List<User> getUsers(){
        return UsersBean.getUsers();
    }
    public void updateUsersList(){
        UsersBean.updateUsersList();
        redirect("/users");
    }
    public void typeChange(User user){
        UsersBean.typeChange(user);
        redirect("/users");
    }
    public static void redirect(String s){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
