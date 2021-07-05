package sample.views;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.components.CellCustom2;
import sample.models.CrudDAO;

public class FrmCrud extends Stage {
    private TableView<CrudDAO> tbvProductos;
    private Button btnAgregar;
    private VBox vBox;
    private Scene escena;
    private CrudDAO objCDAO;

    public FrmCrud() {
        objCDAO = new CrudDAO();

        CrearUI();
        this.setTitle("Gestion de productos");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        vBox = new VBox();
        tbvProductos = new TableView<>();
        btnAgregar = new Button("Agregar");
        btnAgregar.setOnAction(event -> {
            new frmProducto(tbvProductos, objCDAO);
        });
        vBox.getChildren().addAll(tbvProductos, btnAgregar);
        CrearTabla();

        escena = new Scene(vBox, 500, 250);
    }

    private void CrearTabla() {
        TableColumn<CrudDAO, Integer> tbcIdProducto = new TableColumn<>("ID");
        tbcIdProducto.setCellValueFactory(new PropertyValueFactory<>("id_producto"));

        TableColumn<CrudDAO, String> tbcNomProducto = new TableColumn<>("Nombre");
        tbcNomProducto.setCellValueFactory(new PropertyValueFactory<>("nombre_producto"));

        TableColumn<CrudDAO, String> tbcDescripcion = new TableColumn<>("Descripcion");
        tbcDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<CrudDAO, String> tbcExistencia = new TableColumn<>("Existencia");
        tbcExistencia.setCellValueFactory(new PropertyValueFactory<>("existencia"));

        TableColumn<CrudDAO, ImageView> tbcImagen = new TableColumn<>("Imagen");
        tbcImagen.setCellValueFactory(new PropertyValueFactory<>("imgV"));

        TableColumn<CrudDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(new Callback<TableColumn<CrudDAO, String>, TableCell<CrudDAO, String>>() {

            @Override
            public TableCell<CrudDAO, String> call(TableColumn<CrudDAO, String> crudDAOStringTableColumn) {
                return new CellCustom2(1);
            }
        });

        TableColumn<CrudDAO, String> tbcBorrar = new TableColumn<>("Borrar");
        tbcBorrar.setCellFactory(new Callback<TableColumn<CrudDAO, String>, TableCell<CrudDAO, String>>() {

            @Override
            public TableCell<CrudDAO, String> call(TableColumn<CrudDAO, String> crudDAOStringTableColumn) {
                return new CellCustom2(2);
            }
        });
        tbvProductos.getColumns().addAll(tbcIdProducto,tbcNomProducto, tbcDescripcion, tbcExistencia, tbcImagen, tbcEditar,tbcBorrar);
        tbvProductos.setItems(objCDAO.SELECT());
    }
}
