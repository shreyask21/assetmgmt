package db_driver;

//import java.util.Scanner;
import java.sql.*;

public class assetdb {

    private Connection sqlconnection;
    private Statement sqlstatement;
    //private ResultSet sqlresultset;
    public assetdb(String username, String password){
        try{

            this.sqlconnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", username, password);
            this.sqlstatement = sqlconnection.createStatement();
            this.sqlstatement.executeUpdate("CREATE DATABASE asset_db");
            this.sqlstatement.executeUpdate("USE asset_db");
            this.sqlstatement.executeUpdate("CREATE TABLE asset_table (tagid varchar (255), purchasedate date, assetype varchar (255), price int not null, status varchar (255) )");
            System.out.println("New database generated...");
            System.out.println("Connecting to new database...");


        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}