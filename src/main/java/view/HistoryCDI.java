package view;

import control.HistoryBean;
import domain.HistoryEntity;
import domain.TypeOfTransaction;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class HistoryCDI implements Serializable {

    private int idList;
    private int idTransport;

    public int getIdTransport() {
        return idTransport;
    }
    public void setIdTransport(int idTransport) {
        this.idTransport = idTransport;
    }

    public int getIdList() {
        return idList;
    }
    public void setIdList(int idList) {
        this.idList = idList;
    }

    public void addHistory(){
        HistoryBean.addHistory(idList,UserCDI.getUser().getId(), idTransport, String.valueOf(TypeOfTransaction.add));
        HistoryBean.updateHistoryList();
        UserCDI.redirect("/history.jsf");
    }
    public void removeHistory(){
        HistoryBean.addHistory(idList,UserCDI.getUser().getId(), idTransport, String.valueOf(TypeOfTransaction.remove));
        HistoryBean.updateHistoryList();
        UserCDI.redirect("/history.jsf");
    }

    public List<HistoryEntity> getHistory(){
        return HistoryBean.getHistory();
    }
}
