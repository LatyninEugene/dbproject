package view;

import control.ListProductsBean;
import domain.ListProducts;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ListProductsCDI implements Serializable {

    private String title;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<ListProducts> getListProducts(){
        return ListProductsBean.getListProducts();
    }
    public void updateProductsList(){
        ListProductsBean.updateProductsList();
        UserCDI.redirect("/listproducts.jsf");
    }

    public void addList(){
        if(ListProductsBean.addList(title))ListProductsBean.updateProductsList();
        UserCDI.redirect("/listproducts.jsf");
    }

    public void openList(int id){
        UserCDI.redirect("/addforlist.jsf?id="+id);
    }
}
