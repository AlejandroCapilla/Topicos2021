package sample.views;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class BatallaNaval extends Stage implements EventHandler {
    private Scene escena;
    private VBox vBox;
    private Label lblCant;
    private TextField txtCantBarcos;
    private Button btnListo;
    private HBox hBoxOpc;
    private GridPane tablero;
    private Button[][] btnPosiciones;
    private GridPane tableroE;
    private Button[][] btnPosicionesE;

    //Conexion
    private Socket socket;
    private InetAddress host;
    ClienteBatallaNaval cliente;

    private int cantBarcos;


    public BatallaNaval() {
        crearUI();
        this.setTitle("Batalla naval");
        this.setScene(escena);
        this.show();

        try {
            host = InetAddress.getLocalHost();
            socket = new Socket(host, 4000);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cliente = new ClienteBatallaNaval(socket,host);
        cliente.connectToServer();
    }

    private void crearUI() {
        vBox = new VBox();
        vBox.setPadding(new Insets(10)); // Espacio entre el contenedor y el contenido
        vBox.setSpacing(5);// Espacio entre cada elemento contenido

        lblCant = new Label("Cantidad de barcos:");

        txtCantBarcos = new TextField();
        txtCantBarcos.setPrefWidth(50);
        txtCantBarcos.setText("");

        btnListo = new Button("Listo!!!");
        btnListo.addEventHandler(MouseEvent.MOUSE_CLICKED, this);

        hBoxOpc = new HBox();
        hBoxOpc.setPadding(new Insets(10));
        hBoxOpc.setSpacing(10);
        hBoxOpc.getChildren().addAll(lblCant, txtCantBarcos, btnListo);

        tableroE = new GridPane();
        tableroE.setPadding(new Insets(5));
        btnPosicionesE =  new Button[10][9];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                int finalI = i;
                int finalJ = j;

                btnPosicionesE[i][j] = new Button();
                btnPosicionesE[i][j].setPrefSize(300,300);

                btnPosicionesE[i][j].setOnAction(event1 -> PosicionE(finalI, finalJ));

                tableroE.add(btnPosicionesE[i][j],j,i);
            }
        }

        tablero = new GridPane();
        tablero.setPadding(new Insets(5));
        btnPosiciones =  new Button[10][9];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                int finalI = i;
                int finalJ = j;

                btnPosiciones[i][j] = new Button();
                btnPosiciones[i][j].setPrefSize(300,300);

                btnPosiciones[i][j].setOnAction(event1 -> Posicion(finalI, finalJ));

                tablero.add(btnPosiciones[i][j],j,i);
            }
        }


        vBox.getChildren().addAll(hBoxOpc,tableroE,tablero);

        escena = new Scene(vBox,300,600);
    }

    private void PosicionE(int finalI, int finalJ) {

    }

    private void Posicion(int finalI, int finalJ) {
        if (cantBarcos > 0) {
            agregarImagenBoton(finalI, finalJ);
            cantBarcos = cantBarcos-1;
        }
    }

    @Override
    public void handle(Event event) {
        int cant;
        try {
            cant = Integer.parseInt(txtCantBarcos.getText());
            cliente.mandarDatos("N"+cant);

            cantBarcos = cant;
            txtCantBarcos.setDisable(true);
            btnListo.setDisable(true);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private void agregarImagenBoton(int x, int y) {
        BackgroundImage myBI= new BackgroundImage(new Image("sample/assets/barco.JPG",320,320,false,true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false));

        btnPosiciones[x][y].setBackground(new Background(myBI));
    }
}