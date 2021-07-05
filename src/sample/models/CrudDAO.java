package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CrudDAO {
    private int id_producto;
    private String nombre_producto;
    private String descripcion;
    private String existencia;
    private byte[] imagen;
    private Imagen img;
    private ImageView imgV;

    public ImageView getImgV() {
        return imgV;
    }

    public void setImgV(ImageView imgV) {
        this.imgV = imgV;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    private String s = "hola";

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getExistencia() {
        return existencia;
    }

    public void setExistencia(String existencia) {
        this.existencia = existencia;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    /*public void INSERT() {
        try {
            String query = "INSERT tbl_prodcutos (nombre_producto, descripcion, existencia, imagen)" +
                    "VALUES('"+nombre_producto+"','"+descripcion+"','"+existencia+"',"+imagen+")";

            Statement stmt = Conexion2.conexion.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }*/

    public void INSERT(File file) {
        String insert = "insert into tbl_prodcutos(nombre_producto, descripcion, existencia, imagen) values(?,?,?,?)";
        FileInputStream fis = null;
        PreparedStatement ps = null;
        try {
            Conexion2.conexion.setAutoCommit(false);
            fis = new FileInputStream(file);
            ps = Conexion2.conexion.prepareStatement(insert);
            ps.setString(1, nombre_producto);
            ps.setString(2, descripcion);
            ps.setString(3, existencia);
            ps.setBinaryStream(4,fis,(int)file.length());
            ps.executeUpdate();
            Conexion2.conexion.commit();
            return;
        }catch(Exception e) {
            System.out.println(e);
        }finally{
            try {
                ps.close();
                fis.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    /*public void UPDATE() {
        try {
            String query = "UPDATE tbl_prodcutos SET nombre_producto = '"+nombre_producto+ "', descripcion = '"+descripcion+
                    "', existencia = '"+existencia+"', imagen = "+blob+" WHERE id_producto = "+id_producto;

            Statement stmt = Conexion2.conexion.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }*/

    public void UPDATE() {
        String update = "UPDATE tbl_prodcutos SET nombre_producto=?, descripcion =?, existencia =? WHERE id_producto ="+id_producto;
        PreparedStatement ps = null;
        try {
            Conexion2.conexion.setAutoCommit(false);
            ps = Conexion2.conexion.prepareStatement(update);
            ps.setString(1, nombre_producto);
            ps.setString(2, descripcion);
            ps.setString(3, existencia);

            ps.executeUpdate();
            Conexion2.conexion.commit();
            return;
        }catch(Exception e) {
            System.out.println(e);
        }finally{
            try {
                ps.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    public void UPDATE(File file) {
        String update = "UPDATE tbl_prodcutos SET nombre_producto=?, descripcion =?, existencia =?, imagen =? WHERE id_producto ="+id_producto;
        PreparedStatement ps = null;
        FileInputStream fis = null;
        try {
            Conexion2.conexion.setAutoCommit(false);
            fis = new FileInputStream(file);
            ps = Conexion2.conexion.prepareStatement(update);
            ps.setString(1, nombre_producto);
            ps.setString(2, descripcion);
            ps.setString(3, existencia);
            ps.setBinaryStream(4,fis,(int)file.length());

            ps.executeUpdate();
            Conexion2.conexion.commit();
            return;
        }catch(Exception e) {
            System.out.println(e);
        }finally{
            try {
                ps.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    public void DELETE() {
        try {
            String query = "DELETE FROM tbl_prodcutos WHERE id_producto = "+ id_producto;
            Statement stmt = Conexion2.conexion.createStatement();
            stmt.executeUpdate(query);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<CrudDAO> SELECT() {
        ObservableList<CrudDAO> listaC = FXCollections.observableArrayList();
        try {
            CrudDAO objC;

            String query = "SELECT id_producto, nombre_producto, descripcion, existencia, imagen FROM tbl_prodcutos";
            Statement stmt = Conexion2.conexion.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objC = new CrudDAO();
                objC.id_producto = res.getInt("id_producto");
                objC.nombre_producto = res.getString("nombre_producto");
                objC.descripcion = res.getString("descripcion");
                objC.existencia = res.getString("existencia");
                objC.imagen = res.getBytes("imagen");
                img = new Imagen();
                img.setData(objC.imagen);
                objC.imgV = img.getImgeView();

                listaC.add(objC);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        return  listaC;
    }
}
