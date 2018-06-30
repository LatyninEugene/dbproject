package domain;


import control.ListProductsBean;
import control.TransportBean;
import control.UsersBean;

import java.sql.Date;

public class HistoryEntity {
    private int id;
    private int idList;
    private int idUser;
    private TypeOfTransaction type;
    private Date date;
    private int idTransport;

    public int getIdTransport() {
        return idTransport;
    }
    public void setIdTransport(int idTransport) {
        this.idTransport = idTransport;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdList() {
        return idList;
    }
    public void setIdList(int idList) {
        this.idList = idList;
    }

    public int getIdUser() {
        return idUser;
    }
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public TypeOfTransaction getType() {
        return type;
    }
    public void setType(TypeOfTransaction type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public static ListProducts getList(int idList){
        return ListProductsBean.getListById(idList);
    }
    public static User getUser(int idUser){
        return UsersBean.getUserById(idUser);
    }
    public static Transport getTransport(int idTransport){return TransportBean.getTransportById(idTransport);
    }
}
