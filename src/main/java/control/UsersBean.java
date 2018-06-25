package control;

import domain.User;

import javax.ejb.Stateful;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Stateful
public class UsersBean {
    private static ArrayList<User> users;

    static {
        updateUsersList();
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public static boolean updateUsersList(){
        users = new ArrayList<>();
        try(Connection con = JDBCUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setLogin(resultSet.getString(2));
                user.setType(UserType.valueOf(resultSet.getString(4)));
                users.add(user);
            }
        } catch (SQLException e) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
        Collections.sort(users,(o1,o2)->o1.getId() < o2.getId()? -1:1);
        return true;
    }

    public static boolean typeChange(User user){
        int idInTable = find(user);
        try(Connection con = JDBCUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE users SET type = ?::type_of_access where login = ?");
            ps.setString(2,user.getLogin());
            if (user.getType().equals(UserType.user)) {
                ps.setString(1, UserType.admin.toString());
                user.setType(UserType.admin);
            }
            else{
                ps.setString(1,UserType.user.toString());
                user.setType(UserType.user);
            }
            ps.executeUpdate();
            users.set(idInTable, user);

        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
        return true;
    }
    private static int find(User user){
        for(int x = 0; x<users.size(); x++){
            if(users.get(x).getId() == user.getId())
                return x;
        }
        return -1;
    }
}
