package control;

import javax.ejb.Stateless;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Stateless
public class ExampleBean {
    public void createTabel() {
        try(Connection con = JDBCUtil.getConnection(); Statement st = con.createStatement()){
            st.executeUpdate("create TABLE test (name varchar(80));");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
