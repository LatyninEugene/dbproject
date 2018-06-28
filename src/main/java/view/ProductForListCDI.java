package view;


import control.ProductForListBean;
import domain.Product;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProductForListCDI implements Serializable {

    private int idList;
    private int idProduct;
    private int tonnage;

    public int getIdProduct() {
        return idProduct;
    }
    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getTonnage() {
        return tonnage;
    }
    public void setTonnage(int tonnage) {
        this.tonnage = tonnage;
    }

    public int getIdList() {
        return idList;
    }
    public void setIdList(int idList) {
        this.idList = idList;
    }

    private ProductForListBean bean = new ProductForListBean();

    public List<Product> getProductForList(){
        bean.updateProductsByIDList(idList);
        return bean.getProducts();
    }
    public void addProductForList(){
        bean.addProductForList(idList,idProduct,tonnage);
        bean.updateProductsByIDList(idList);
        UserCDI.redirect("/addforlist.jsf?id="+idList);
    }
    public int getSumTon(){
        return bean.getSumTon();
    }
    public void deleteProductForList(int idP){
        bean.deleteProductForList(idP,idList);
        bean.updateProductsByIDList(idList);
        UserCDI.redirect("/addforlist.jsf?id="+idList);
    }
}
