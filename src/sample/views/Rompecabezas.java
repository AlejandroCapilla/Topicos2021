package sample.views;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Rompecabezas extends Stage implements EventHandler {
    private BorderPane borderPane;
    private GridPane tablero;
    private Button[][] btnTarjetas;
    private HBox hBox;
    private Label lblTarjetas;
    private TextField txtTarjetas;
    private Button btnMesclar;
    private Scene escena;

    private int noPiezas;

    public Rompecabezas(){
        CrearUI();
        this.setTitle("Rompecabezas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        borderPane = new BorderPane();
        lblTarjetas = new Label("Cantidad de Piezas");
        txtTarjetas = new TextField();
        btnMesclar = new Button("Mesclar Tarjetas");
        btnMesclar.addEventHandler(MouseEvent.MOUSE_CLICKED,this);
        hBox = new HBox();
        hBox.getChildren().addAll(lblTarjetas,txtTarjetas,btnMesclar);

        borderPane.setTop(hBox);

        tablero = new GridPane();
        borderPane.setCenter(tablero);

        escena = new Scene(borderPane, 800, 600);
    }

    @Override
    public void handle(Event event) {
        noPiezas = Integer.parseInt(txtTarjetas.getText());
        btnTarjetas =  new Button[noPiezas][noPiezas];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                btnTarjetas[i][j] = new Button();
                tablero.add(btnTarjetas[i][j],j,i);
            }
        }
    }
}
