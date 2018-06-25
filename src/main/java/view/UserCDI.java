package view;

import control.EntryBean;
import control.UserType;
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
    private User user;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

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

    public String createUser(){
        //String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        if(!checkUser()){
            if(eb.addUser(login,password)) {
                user = eb.getUser(login);
                return("/index.jsf");
            }
        }
        return ("/login");
    }
    public boolean checkUser(){
       return eb.checkUser(login,password);
    }
    public String loginUser(){
        if(checkUser()){
            user = eb.getUser(login);
            return ("/index.jsf");
        }
        return ("/login");
    }

    public String exit(){
        user = null;
        login = null;
        password = null;
        return ("/login");
    }

    public String goUsers(){
       return user.getType().equals(UserType.user)? "/users.jsf" : "/usersforadmin.jsf";
    }

    public List<User> getUsers(){
        return UsersBean.getUsers();
    }

    public String updateUsersList(){
        UsersBean.updateUsersList();
        return goUsers();
    }
    public String typeChange(User user){
        UsersBean.typeChange(user);
        this.user.setType(user.getType());
        return goUsers();
    }
}
