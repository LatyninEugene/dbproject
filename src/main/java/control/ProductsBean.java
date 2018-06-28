package control;


import domain.Product;
import domain.ProductType;
import domain.User;
import domain.UserType;

import javax.ejb.Stateful;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

@Stateful
public class ProductsBean {

    private static ArrayList<Product> products;

    public static ArrayList<Product> getProducts() {
        return products;
    }
    public static void setProducts(ArrayList<Product> products) {
        ProductsBean.products = products;
    }

    static {
        updateProductsList();
    }

    public static boolean updateProductsList(){
        products = new ArrayList<>();
        try(Connection con = JDBCUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM products");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                Product product = new Product();
                product.setId(resultSet.getInt(1));
                product.setTitle(resultSet.getString(2));
                product.setCount(resultSet.getInt(3));
                product.setType(ProductType.valueOf(resultSet.getString(4)));
                products.add(product);
            }
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
        Collections.sort(products,(o1, o2)->o1.getId() < o2.getId()? -1:1);
        return true;
    }
    public static boolean addProduct(String title, String type){
        try (Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT into products(title, count, type) values(?,?,?::type_of_product)");
            ps.setString(1,title);
            ps.setInt(2, 0);
            ps.setString(3,type);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}
