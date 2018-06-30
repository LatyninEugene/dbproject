package view;


import control.ProductForListBean;
import domain.Product;

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

    public List<Product> getProductForList(){
        ProductForListBean.updateProductsByIDList(idList);
        return ProductForListBean.getProducts();
    }
    public void addProductForList(){
        ProductForListBean.addProductForList(idList,idProduct,tonnage);
        ProductForListBean.updateProductsByIDList(idList);
        UserCDI.redirect("/addforlist.jsf?id="+idList);
    }
    public int getSumTon(){
        return ProductForListBean.getSumTon();
    }
    public void deleteProductForList(int idP){
        ProductForListBean.deleteProductForList(idP,idList);
        ProductForListBean.updateProductsByIDList(idList);
        UserCDI.redirect("/addforlist.jsf?id="+idList);
    }
}
