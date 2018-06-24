package view;

import control.EntryBean;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

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

    public void createUser(){
        if(!checkUser()){
            if(eb.addUser(login,password)) {
                String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect(path+"/index.jsf");
                } catch (IOException e) {
                }
            }
        }
    }
    public boolean checkUser(){
       return eb.checkUser(login,password);
    }
    public void loginUser(){
        if(checkUser()){
            String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(
                        path+"/index.jsf");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void exit(){
        login = null;
        password = null;
        String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(path+"/login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
