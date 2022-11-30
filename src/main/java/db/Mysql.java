package db;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {
    private Connection conn;
    private String MYSQL_USER;
    private String MYSQL_PASSWORD;
    private String MYSQL_ROOT_PASSWORD;
    private String MYSQL_DATABASE;
    private int MYSQL_PORT;
    private String MYSQL_HOST;

    public Mysql(){
        try {
            Dotenv dotenv = Dotenv.load();
            this.MYSQL_USER = dotenv.get("MYSQL_USER");
            this.MYSQL_PASSWORD = dotenv.get("MYSQL_PASSWORD");
            this.MYSQL_HOST = dotenv.get("MYSQL_HOST", "0.0.0.0");
            this.MYSQL_PORT = Integer.parseInt(dotenv.get("MYSQL_PORT", "9001"));
            this.MYSQL_DATABASE = dotenv.get("MYSQL_DATABASE");
        } catch (Exception e){
            this.MYSQL_USER = System.getenv("MYSQL_USER");
            this.MYSQL_PASSWORD = System.getenv("MYSQL_PASSWORD");
            this.MYSQL_HOST = System.getenv("MYSQL_HOST");
            this.MYSQL_PORT = Integer.parseInt(System.getenv("MYSQL_PORT"));
            this.MYSQL_DATABASE = System.getenv("MYSQL_DATABASE");
        }
        this.Connect();
    }

    public void Connect(){
        try{
            String url = String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s&useSSL=false&allowPublicKeyRetrieval=true", this.MYSQL_HOST, this.MYSQL_PORT, this.MYSQL_DATABASE, this.MYSQL_USER, this.MYSQL_PASSWORD);
            System.out.println("Connection successfully established...");
            this.conn = DriverManager.getConnection(url);
            this.conn.setAutoCommit(false);
        } catch (Exception e) {
            System.out.println("Please provide correct env!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public Connection getConnection(){
        return this.conn;
    }

    public static void main(String[] args){
        Mysql a = new Mysql();
        a.Connect();
        Connection b = a.getConnection();
    }
}