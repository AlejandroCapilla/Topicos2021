package sample.models;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion2 {
    private static String server = "127.0.0.1";
    private static String user = "topicos2021";
    private static String pwd = "1234";
    private static String db = "productosdb";

    public static Connection conexion;

    public static void getConexion() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mariadb://"+server+":3306/"+db, user,pwd);

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}