package control;

import domain.HistoryEntity;
import domain.ListProducts;
import domain.Product;
import domain.TypeOfTransaction;

import javax.ejb.Stateful;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

@Stateful
public class HistoryBean {
    private static ArrayList<HistoryEntity> history;

    public static ArrayList<HistoryEntity> getHistory() {
        return history;
    }
    public static void setHistory(ArrayList<HistoryEntity> history) {
        HistoryBean.history = history;
    }

    static {
        updateHistoryList();
    }

    public static boolean updateHistoryList(){
        history = new ArrayList<>();
        try(Connection con = JDBCUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM history");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                HistoryEntity historyEntity = new HistoryEntity();
                historyEntity.setId(resultSet.getInt(1));
                historyEntity.setIdList(resultSet.getInt(2));
                historyEntity.setIdUser(resultSet.getInt(3));
                historyEntity.setType(TypeOfTransaction.valueOf(resultSet.getString(4)));
                historyEntity.setDate(resultSet.getDate(5));
                historyEntity.setIdTransport(resultSet.getInt(6));
                history.add(historyEntity);
            }
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
        Collections.sort(history,(o1, o2)->o1.getId() < o2.getId()? -1:1);
        return true;
    }

    public static boolean addHistory(int idList, int idUser,int idTransport, String type){
        try(Connection con = JDBCUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("INSERT into history(id_list, id_user, type, date, id_transport) values (?,?,?::type_of_transaction,?::timestamp,?);");
            ps.setInt(1,idList);
            ps.setInt(2,idUser);
            ps.setString(3,type);
            LocalDateTime localDate = LocalDateTime.now();
            ps.setTimestamp(4,Timestamp.valueOf(localDate));
            ps.setInt(5,idTransport);
            ps.executeUpdate();
            ProductForListBean.updateProductsByIDList(idList);
            ArrayList<Product> products = ProductForListBean.getProducts();
            for (Product p : products) {
                int t;
                if(type.equals("add"))t = ProductsBean.getProductByID(p.getId()).getCount()+p.getCount();
                else t = ProductsBean.getProductByID(p.getId()).getCount()-p.getCount();
                ps = con.prepareStatement("UPDATE products set count = ? where id = ?");
                ps.setInt(1,t);
                ps.setInt(2,p.getId());
                ps.executeUpdate();
            }
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
        return true;
    }


}
