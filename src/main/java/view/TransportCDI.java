package view;

import control.TransportBean;
import domain.Transport;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class TransportCDI implements Serializable {

    private String number;
    private int tonnage;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getTonnage() {
        return tonnage;
    }

    public void setTonnage(int tonnage) {
        this.tonnage = tonnage;
    }

    public List<Transport> getTransports(){
        return TransportBean.getTransports();
    }
    public void updateTransportList(){
        TransportBean.updateTransportsList();
        UserCDI.redirect("/transport.jsf");
    }
    public void addTransport(){
        if(TransportBean.addTransport(number,tonnage)) {
            TransportBean.updateTransportsList();
            UserCDI.redirect("/transport.jsf");
        }else UserCDI.redirect("/addtransport.jsf");
    }

    public void sortByID(){
        TransportBean.sortByID();
        UserCDI.redirect("/transport.jsf");
    }
    public void sortByTonnage(){
        TransportBean.sortByTonnage();
        UserCDI.redirect("/transport.jsf");
    }
}
