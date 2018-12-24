package sample.DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class SubjectDB {

//    public void connect() {
//        Connection c = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:subjectdb.db");
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
//        System.out.println("Opened database successfully");
//    }

//    public void createDB() {
//        Connection c = null;
//        Statement stmt = null;
//        try {
//            Class.forName("org.sqlite.JDBC");
//            c = DriverManager.getConnection("jdbc:sqlite:subjectdb.db");
//            System.out.println("Opened database successfully");
//
//            stmt = c.createStatement();
//            String sql = "CREATE TABLE SUBJECT " +
//                    "(COURSEID TEXT PRIMARY KEY     NOT NULL," +
//                    " NAME           TEXT    NOT NULL, " +
//                    " PREREQ         TEXT, " +
//                    " PASS           INT     NOT NULL, " +
//                    " YEAR           INT     NOT NULL," +
//                    " SEM            INT     NOT NULL," +
//                    " COLOR          TEXT)";
//            stmt.executeUpdate(sql);
//            stmt.close();
//            c.close();
//        } catch (Exception e) {
//            System.err.println(e.getClass().getName() + ": " + e.getMessage());
//            System.exit(0);
//        }
//        System.out.println("Table created successfully");
//    }

    public ObservableList<Subject> showDB() {
        Connection c = null;
        Statement stmt = null;
        ObservableList<Subject> subjects = FXCollections.observableArrayList();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:subjectdb.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SUBJECT;");
            while (rs.next()) {
                String id = rs.getString("courseid");
                String name = rs.getString("name");
                String pre = rs.getString("prereq");
                String pass = rs.getString("pass");
                String color = rs.getString("color");
                int year = rs.getInt("year");
                int sem = rs.getInt("sem");
                subjects.add(new Subject(id, name, pre, pass, year, sem, color));
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return subjects;
    }

    public ObservableList<Subject> showDBUser(int y, int s) {
        Connection c = null;
        Statement stmt = null;
        ObservableList<Subject> subjects = FXCollections.observableArrayList();
        if (y > 4) {
            y = 4;
        }
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:subjectdb.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SUBJECT WHERE YEAR<=\'" + y + "\' AND SEM=\'" + s + "\'");
            while (rs.next()) {
                String id = rs.getString("courseid");
                String name = rs.getString("name");
                String pre = rs.getString("prereq");
                String pass = rs.getString("pass");
                String color = rs.getString("color");
                int year = rs.getInt("year");
                int sem = rs.getInt("sem");
                subjects.add(new Subject(id, name, pre, pass, year, sem, color));
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return subjects;
    }

    public void deleteData(String courseid) {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:subjectdb.db");
            if (c != null) {
                String query = "DELETE FROM SUBJECT WHERE COURSEID == \'" + courseid + "\' ";
                PreparedStatement ps = c.prepareStatement(query);
                ps.executeUpdate();
                c.close();
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public String getData(String courseid) {
        Connection c = null;
        Statement stmt = null;
        String ans = "";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:subjectdb.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SUBJECT WHERE COURSEID LIKE '%" + courseid + "%'");
            while (rs.next()) {
                String id = rs.getString("courseid");
                String name = rs.getString("name");
                String pre = rs.getString("prereq");
                String pass = rs.getString("pass");
                String color = rs.getString("color");
                int year = rs.getInt("year");
                int sem = rs.getInt("sem");
                ans = "Course ID: " + id + '\n' +
                        "Subject Name: " + name + '\n' +
                        "Pre Req: " + pre + '\n' +
                        "Year: " + year + '\n' +
                        "Semester: " + sem + '\n' +
                        "Pass: " + pass + '\n' +
                        "Color: " + color + '\n';
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

    public void addData(String id, String name, String pre, String pass, String color, int year, int sem) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:subjectdb.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "INSERT INTO SUBJECT (COURSEID,NAME,PREREQ,PASS,COLOR,YEAR,SEM) " +
                    "VALUES (\'" + id + "\', \'" + name + "\', \'" + pre + "\',\'" + pass + "\', \'" + color + "\',\'" + year + "\',\'" + sem + "\' );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void updateData(String id, String name, String pre, String pass, String color, int year, int sem) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:subjectdb.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "UPDATE SUBJECT set NAME= \'" + name + "\',PREREQ= \'" + pre + "\',PASS=\'" + pass + "\',COLOR= \'" + color + "\',YEAR=\'" + year + "\',SEM=\'" + sem + "\' where COURSEID=\'" + id + "\'";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public String getPass(String courseid) {
        Connection c = null;
        Statement stmt = null;
        String ans = "";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:subjectdb.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SUBJECT WHERE COURSEID = \'" + courseid + "\'");
            while (rs.next()) {
                String pass = rs.getString("pass");
                ans = pass;
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

    public void updatePass(String courseid, String pass) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:subjectdb.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "UPDATE SUBJECT set PASS=\'" + pass + "\' WHERE COURSEID=\'" + courseid + "\'";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public ArrayList checkPreReq(String prereq) {
        ArrayList<String> pre = new ArrayList<String>();
        if (prereq.contains("/")) {
            String[] hold = prereq.split("/");
            String[] hold1 = hold[0].split(",");
            for (int i = 0; i < hold1.length; i++) {
                pre.add(hold1[i]);
            }
            pre.add("/");
            String[] hold2 = hold[1].split(",");
            for (int j = 0; j < hold2.length; j++) {
                pre.add(hold2[j]);
            }
            pre.add("3");
        } else {
            if (prereq.contains(",")) {
                String[] hold = prereq.split(",");
                for (int i = 0; i < hold.length; i++) {
                    pre.add(hold[i]);
                }
                pre.add("1");
            } else if (prereq.contains("-")) {
                String[] hold = prereq.split("-");
                for (int i = 0; i < hold.length; i++) {
                    pre.add(hold[i]);
                }
                pre.add("2");
            } else {
                pre.add(prereq);
                pre.add("4");
            }
        }
        return pre;
    }

    public Map getAllPreReq() {
        Connection c = null;
        Statement stmt = null;
        Map<String, String> preReqDB = new LinkedHashMap<String, String>();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:subjectdb.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SUBJECT");
            while (rs.next()) {
                String courseid = rs.getString("courseid");
                String pre = rs.getString("prereq");
                preReqDB.put(courseid, pre);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return preReqDB;
    }

    public String getPreReq(String courseid) {
        Connection c = null;
        Statement stmt = null;
        String ans = "";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:subjectdb.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SUBJECT WHERE COURSEID = \'" + courseid + "\'");
            while (rs.next()) {
                String prereq = rs.getString("prereq");
                ans = prereq;
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

    public void updateAllPasstoZero() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:subjectdb.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            String sql = "UPDATE SUBJECT set PASS= 'NotPass'";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public boolean checkCourseId(String courseid) {
        Connection c = null;
        Statement stmt = null;
        boolean ans = false;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:subjectdb.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM SUBJECT WHERE COURSEID LIKE '%" + courseid + "%'");
            while (rs.next()) {
                ans = true;
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
}

