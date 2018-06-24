package control;

import javax.ejb.Stateless;
import java.sql.*;

@Stateless
public class EntryBean {
    public boolean checkUser(String login, String password) {
        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE login = ? AND password = ?;");
            ps.setString(1,login);
            ps.setString(2,password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
        } catch (ClassNotFoundException e) {
        }
        return false;
    }

    public boolean addUser(String login, String password){
        try(Connection con = JDBCUtil.getConnection()){
            PreparedStatement ps = con.prepareStatement("INSERT INTO users(login, password, type) VALUES(?,?,?::type_of_access);");
            ps.setString(1,login);
            ps.setString(2,password);
            ps.setString(3,UserType.user.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}
