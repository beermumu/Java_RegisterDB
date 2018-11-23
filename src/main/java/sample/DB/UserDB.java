package sample.DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.UserData;

import java.sql.*;

public class UserDB {

    public ObservableList<UserData> showDB() {
        Connection c = null;
        Statement stmt = null;
        ObservableList<UserData> user = FXCollections.observableArrayList();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:userdb.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User;");
            while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");
                int sem = rs.getInt("sem");
                user.add(new UserData(id, name, sem));
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return user;
    }

    public void addData(String id, String name,int sem) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:userdb.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "INSERT INTO User (id,name,sem) " +
                    "VALUES (\'" + id + "\', \'" + name + "\',\'" + sem + "\' );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public Integer getSem() {
        Connection c = null;
        Statement stmt = null;
        int ans = 0;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:userdb.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User");
            while (rs.next()) {
                int sem = rs.getInt("sem");
                ans = sem;
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ans;
    }

    public String getId() {
        Connection c = null;
        Statement stmt = null;
        String ans = "";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:userdb.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User");
            while (rs.next()) {
                String id = rs.getString("id");
                ans = id;
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ans;
    }

    public String getName() {
        Connection c = null;
        Statement stmt = null;
        String ans = "";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:userdb.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM User");
            while (rs.next()) {
                String name = rs.getString("name");
                ans = name;
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return ans;
    }

    public void deleteData() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:userdb.db");
            if (c != null) {
                String query = "DELETE FROM User ";
                PreparedStatement ps = c.prepareStatement(query);
                ps.executeUpdate();
                c.close();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
