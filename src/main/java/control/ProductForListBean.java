package control;

import domain.Product;
import domain.ProductType;

import javax.ejb.Stateless;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProductForListBean {
    private static ArrayList<Product> products;

    public static ArrayList<Product> getProducts() {
        return products;
    }
    public static void setProducts(ArrayList<Product> products) {
        ProductForListBean.products = products;
    }

    public static boolean updateProductsByIDList(int id){
        products = new ArrayList<>();
        try(Connection con = JDBCUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * from product_for_list where id_list = ?;");
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Product product = new Product();
                ps = con.prepareStatement("select * from products where id = ?;");
                ps.setInt(1,rs.getInt(3));
                ResultSet rs1 = ps.executeQuery();
                if(rs1.next()) {
                    product.setId(rs1.getInt(1));
                    product.setTitle(rs1.getString(2));
                    product.setCount(rs.getInt(4));
                    product.setType(ProductType.valueOf(rs1.getString(4)));
                    products.add(product);
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public static boolean addProductForList(int idList, int idProduct, int tonnage){
        try(Connection con = JDBCUtil.getConnection()) {
            int idInList = checkList(idProduct);
            PreparedStatement ps;
            if(idInList==-1) {
                ps = con.prepareStatement("INSERT  into product_for_list(id_list, id_product, tonnage) VALUES(?,?,?); ");
                ps.setInt(1, idList);
                ps.setInt(2, idProduct);
                ps.setInt(3, tonnage);
                ps.executeUpdate();
            }else {
                tonnage+=products.get(idInList).getCount();
                ps = con.prepareStatement("UPDATE product_for_list set tonnage = ? where id_list=? and id_product=?;");
                ps.setInt(1,tonnage);
                ps.setInt(2,idList);
                ps.setInt(3,idProduct);
                ps.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
        return true;
    }
    private static int checkList(int id){
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getId()==id){
                return i;
            }
        }
        return -1;
    }
    public static boolean deleteProductForList(int idProduct, int idList){
        try (Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("DELETE from product_for_list where id_product = ? and id_list = ?;");
            ps.setInt(1,idProduct);
            ps.setInt(2,idList);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
        return true;
    }
    public static int getSumTon(){
        int sumTon = 0;
        for (Product p : products) {
            sumTon+=p.getCount();
        }
        return sumTon;
    }

}
