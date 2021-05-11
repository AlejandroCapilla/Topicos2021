package sample.components;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import sample.models.CancionesDAO;
import sample.views.frmCancion;

import java.util.Optional;

public class CellCustom extends TableCell<CancionesDAO, String> {
    private Button btnCelda;
    private CancionesDAO objCDao;

    public CellCustom(int opc) {
        if(opc == 1) {
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event -> {
                objCDao = CellCustom.this.getTableView().getItems().get(CellCustom.this.getIndex());
                new frmCancion(CellCustom.this.getTableView(),objCDao);
            });

        }else {
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setHeaderText("Confirmacion de la accion");
                alerta.setContentText("Realmente deseas borrar el registro");
                Optional<ButtonType> result = alerta.showAndWait();
                if(result.get() == ButtonType.OK) {
                    objCDao.DELETE();
                    objCDao = CellCustom.this.getTableView().getItems().get(CellCustom.this.getIndex());
                    CellCustom.this.getTableView().setItems(objCDao.SELECT());
                    CellCustom.this.getTableView().refresh();
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
