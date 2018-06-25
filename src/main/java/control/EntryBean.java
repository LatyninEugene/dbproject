package control;

import domain.User;

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
    public User getUser(String login){
        User user = new User();
        try(Connection con = JDBCUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE login = ?;");
            ps.setString(1, login);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setType(UserType.valueOf(resultSet.getString(4)));
            }
        } catch (SQLException | ClassNotFoundException e) {
           return null;
        }
        return user;
    }
}
