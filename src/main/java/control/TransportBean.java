package control;

import domain.Product;
import domain.ProductType;
import domain.Transport;

import javax.ejb.Stateful;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

@Stateful
public class TransportBean {
    private static ArrayList<Transport> transports;

    public static ArrayList<Transport> getTransports() {
        return transports;
    }
    public static void setTransports(ArrayList<Transport> transports) {
        TransportBean.transports = transports;
    }

    static {
        updateTransportsList();
    }

    public static boolean updateTransportsList(){
        transports = new ArrayList<>();
        try(Connection con = JDBCUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM transport");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                Transport transport = new Transport();
                transport.setId(resultSet.getInt(1));
                transport.setNummber(resultSet.getString(2));
                transport.setTonnage(resultSet.getInt(3));
                transports.add(transport);
            }
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
        Collections.sort(transports,(o1, o2)->o1.getId() < o2.getId()? -1:1);
        return true;
    }

    public static boolean addTransport(String num, int ton){
        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("insert into transport(number, tonnage) VALUES (?,?);");
            ps.setString(1,num);
            ps.setInt(2,ton);
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public static void sortByID(){
        Collections.sort(transports, (o1, o2)->o1.getId() < o2.getId()? -1:1);
    }
    public static void sortByTonnage(){
        Collections.sort(transports, (o1, o2)->o1.getTonnage() < o2.getTonnage()? 1:-1);
    }
}
