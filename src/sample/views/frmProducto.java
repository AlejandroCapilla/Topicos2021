package sample.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.models.CrudDAO;
import java.io.File;

public class frmProducto extends Stage {
    private Scene escena;
    private VBox vBox;
    private TextField txtNombre, txtDescripcion, txtExistencia;
    private Button btnGuardar;
    private CrudDAO objCDAO;
    private TableView<CrudDAO> tbvProductos;
    private Button btnImagen;
    private FileChooser fileChooser;
    private File archivoSeleccionado;
    private byte[] img;

    public frmProducto(TableView<CrudDAO> tbvProductos, CrudDAO objCDAO) {
        this.tbvProductos = tbvProductos;
        if (objCDAO != null) {
            this.objCDAO = objCDAO;
        }else {
            this.objCDAO = new CrudDAO();
        }

        CrearUI();
        this.setTitle("Gestion de cancion");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));

        txtNombre = new TextField();
        txtNombre.setText(objCDAO.getNombre_producto());
        txtNombre.setPromptText("Nombre del producto");

        txtDescripcion = new TextField();
        txtDescripcion.setText(objCDAO.getDescripcion());
        txtDescripcion.setPromptText("Descripcion del prodcuto");

        txtExistencia = new TextField();
        txtExistencia.setText(objCDAO.getExistencia());
        txtExistencia.setPromptText("Existencia");

        img = objCDAO.getImagen();

        btnImagen = new Button("Seleccionar imagen");
        btnImagen.setOnAction(event -> abrirImagen());

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> {
            objCDAO.setNombre_producto(txtNombre.getText());
            objCDAO.setDescripcion(txtDescripcion.getText());
            objCDAO.setExistencia(txtExistencia.getText());

            if(objCDAO.getId_producto() > 0) {
                if (archivoSeleccionado == null) {
                    objCDAO.UPDATE();
                }else{
                    objCDAO.UPDATE(archivoSeleccionado);
                }

            }else {
                objCDAO.INSERT(archivoSeleccionado);
            }

            tbvProductos.setItems(objCDAO.SELECT());
            tbvProductos.refresh();

            this.close();
        });

        vBox.getChildren().addAll(txtNombre, txtDescripcion, txtExistencia, btnImagen, btnGuardar);
        escena = new Scene(vBox, 250,300);
    }

    private void abrirImagen() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar imagen");
        archivoSeleccionado = fileChooser.showOpenDialog(this);
    }
}
