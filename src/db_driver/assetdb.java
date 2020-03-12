package db_driver;

import java.util.Scanner;
import java.sql.*;

public class assetdb {
    private Connection connection = connect("");
    private Statement stmt = null;

    public assetdb(String username, String password) throws SQLException, ClassNotFoundException {
        System.out.println("Opening database connection...");
        /* Load JDBC driver */
        Class.forName("com.mysql.cj.jdbc.Driver");

        /* Create database connection with given username password */
        this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", username, password);

        /* Create SQL statement */
        this.stmt = this.connection.createStatement();

        

    }

    public void write_entry() throws SQLException {
        String tagid, purchasedate, assetype, status;
        int price;
        Scanner in = new Scanner(System.in);

        /* Read data */
        System.out.print("Enter Asset Tag ID> ");
        tagid = in.nextLine();
        System.out.print("Enter Asset Purchase Date (YYYY/MM/DD)> ");
        purchasedate = in.nextLine();
        System.out.print("Enter Asset Type> ");
        assetype = in.nextLine();
        System.out.print("Enter Asset Price> ");
        price = in.nextInt();
        System.out.print("Enter Asset Status> ");
        status = in.nextLine();
        /*************/

        /* Insert data into table */
        this.stmt.executeUpdate("INSERT INTO asset_table (tagid, purchasedate, assetype, price, status) VALUES ("
                + tagid + "," + purchasedate + "," + assetype + "," + price + "," + status + ",");
        this.connection.commit();
        /**************************/
    }

    public void display_db() throws SQLException {
        ResultSet RS = this.stmt.executeQuery("SELECT * FROM asset_table");
        try {
            while (RS.next() != false) {
                System.out.println(RS.getString(1));
            }
        } finally {
            RS.close();
        }
    }

    public void close_db() throws SQLException {
        System.out.println("Closing database connection...");
        this.stmt.close();
        this.connection.close();
    }

    
    public static final Connection connect(String database) {
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(JDBC_URL + database, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}


/*
try {

            /* SQL queries to create database */
            this.stmt.execute("CREATE DATABASE Assets");
            /**********************/
            this.stmt.execute(
                    "CREATE TABLE asset_table ( tagid varchar (255), purchasedate date, assetype varchar(255), price int not null, status varchar(255) )");

            this.connection.commit();
            System.out.println("Created database...");

        } catch (SQLException exception) {
            this.stmt.execute("OPEN DATABASE Assets");
        }


*/