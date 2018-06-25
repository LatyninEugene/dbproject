package view;

import control.EntryBean;
import control.UsersBean;
import domain.User;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class UserCDI implements Serializable {

    private String login;
    private String password;

    public String getLogin() {
        return login;
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


    private EntryBean eb = new EntryBean();
    private UsersBean ub = new UsersBean();

    public String createUser(){
        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        if(!checkUser()){
            if(eb.addUser(login,password)) {
                return(path+"/index.jsf");
            }
        }
        return (path+"/login");
    }
    public boolean checkUser(){
       return eb.checkUser(login,password);
    }
    public String loginUser(){
        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        if(checkUser()){
            return (path+"/index.jsf");
        }
        return (path+"/login");
    }

    public String exit(){
        login = null;
        password = null;
        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        return (path+"/login");
    }

    public List<User> getUsers(){
        return ub.getUsers();
    }

    public String updateUsersList(){
        UsersBean.updateUsersList();
        return "/users.jsf";
    }
}
