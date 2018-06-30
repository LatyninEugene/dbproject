package view;

import control.ProductsBean;
import domain.Product;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProductsCDI implements Serializable {

    private String title;
    private String type;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public void updateProductsList(){
        ProductsBean.updateProductsList();
        UserCDI.redirect("/storage.jsf");
    }

    public List<Product> getProducts(){
        return ProductsBean.getProducts();
    }

    public void addProduct(){
        if(ProductsBean.addProduct(title,type)){
            ProductsBean.updateProductsList();
            UserCDI.redirect("/storage.jsf");
        } else UserCDI.redirect("/addproduct.jsf");
    }

    public void sortById(){
        ProductsBean.sortById();
        UserCDI.redirect("/storage.jsf");
    }

    public void sortByTonnage(){
        ProductsBean.sortByTonnage();
        UserCDI.redirect("/storage.jsf");
    }
}
