package database;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.sybase.jdbc4.a.b.an.e;

public class connection {
    static final String DB_URL = "jdbc:sybase:Tds:10.56.128.121:5000/Contact";
    static final String USER = "dn301182nti";
    static final String PASS = "14789632g";
    static final String QUERY = "SELECT State FROM JEvent WHERE JE_REF='220515TN01533076'";
    //static final String QUERYM = "UPDATE JEvent " + "SET State = 'IN' WHERE JE_REF='220515TN01533076'";

    public static void main(String[] args) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
        )
        {
        String UpdState = "UPDATE JEvent " + "SET State = 'IN' WHERE JE_REF='220515TN01533076'";
            stmt.executeUpdate(UpdState);
            ResultSet rs = stmt.executeQuery(QUERY); // Extract data from result set
            while (rs.next()){   // Retrieve by column name
                System.out.print("State: " + rs.getString("State"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
