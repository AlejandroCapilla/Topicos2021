package sample.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import sample.models.CrudDAO;
import sample.views.frmProducto;

import java.util.Optional;

public class CellCustom2 extends TableCell<CrudDAO, String> {
    private Button btnCelda;
    private CrudDAO objCDao;

    public CellCustom2(int opc) {
        if(opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objCDao = CellCustom2.this.getTableView().getItems().get(CellCustom2.this.getIndex());
                new frmProducto(CellCustom2.this.getTableView(),objCDao);
            });

        }else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setHeaderText("Confirmacion de la accion");
                alerta.setContentText("Realmente deseas borrar el registro");
                Optional<ButtonType> result = alerta.showAndWait();
                if(result.get() == ButtonType.OK) {
                    objCDao = CellCustom2.this.getTableView().getItems().get(CellCustom2.this.getIndex());
                    objCDao.DELETE();
                    CellCustom2.this.getTableView().setItems(objCDao.SELECT());
                    CellCustom2.this.getTableView().refresh();
                }
            });
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if(!empty) {
            setGraphic(btnCelda);
        }
    }
}
