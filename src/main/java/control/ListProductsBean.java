package control;

import domain.ListProducts;
import domain.Product;
import domain.ProductType;

import javax.ejb.Stateful;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Stateful
public class ListProductsBean {
    private static ArrayList<ListProducts> listProductsArrayList;

    static {
        updateProductsList();
    }

    public static ArrayList<ListProducts> getListProducts() {
        return listProductsArrayList;
    }

    public static void setListProducts(ArrayList<ListProducts> listProducts) {
        ListProductsBean.listProductsArrayList = listProducts;
    }

    public static boolean updateProductsList() {
        listProductsArrayList = new ArrayList<>();
        try (Connection con = JDBCUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM list_products");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                ListProducts listProducts = new ListProducts();
                listProducts.setId(resultSet.getInt(1));
                listProducts.setTitle(resultSet.getString(2));
                listProductsArrayList.add(listProducts);
            }
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public static boolean addList(String title){
        try(Connection con = JDBCUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT into list_products(title) VALUES(?)");
            ps.setString(1,title);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public static ListProducts getListById(int id){
        for (ListProducts l : listProductsArrayList) {
            if (l.getId()==id)return l;
        }
        return null;
    }
}