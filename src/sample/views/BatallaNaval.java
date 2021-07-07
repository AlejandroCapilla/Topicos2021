package sample.views;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private Label lblMensaje;

    //Conexion
    private Socket socket;
    private InetAddress host;
    ClienteBatallaNaval cliente;

    private int cantBarcos;
    private int cantBarcosUndidos = 0;
    private boolean turno;

    private String entrada;
    private String aux;

    private boolean juegoEmpezado;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // Esto se ejecuta en segundo plano una única vez
            while (true) {
                // Pero usamos un truco y hacemos un ciclo infinito
                try {
                    // En él, hacemos que el hilo duerma
                    Thread.sleep(1000);
                    // Y después realizamos las operaciones

                    entrada = cliente.obtenerDatos();

                    javafx.application.Platform.runLater(
                            () -> {
                                switch (entrada.charAt(0)) {
                                    case 'N':
                                        txtCantBarcos.setText(entrada.substring(1));
                                        txtCantBarcos.setDisable(true);
                                        btnListo.setDisable(true);
                                        cantBarcos = Integer.parseInt(txtCantBarcos.getText());
                                        lblMensaje.setText("Posiciona tus piezas en el tanlero");
                                        aux = "Es turno de tu oponente";
                                        juegoEmpezado = true;
                                        break;
                                    case 'D':
                                        verificarDisparo(entrada.substring(1));
                                        turno = true;
                                        lblMensaje.setText("Es tu turno!!!");
                                        break;
                                    case 'G':
                                        mostrarGanador(entrada.substring(1));
                                }
                            });

                    // Así, se da la impresión de que se ejecuta cada cierto tiempo
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                    System.out.println("Catch Runable");
                }
            }
        }
    };

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

        Thread hilo = new Thread(runnable);
        hilo.start();
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

        lblMensaje = new Label("Ingrese la cantidad de piezas");

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

        vBox.getChildren().addAll(hBoxOpc, lblMensaje, tableroE,tablero);

        escena = new Scene(vBox,300,600);
    }

    private void PosicionE(int finalI, int finalJ) {
        if(turno) {
            cliente.mandarDatos("D"+finalI+" "+finalJ);

            lblMensaje.setText("Es turno de tu oponente");

            turno = false;
        }
    }

    private void Posicion(int finalI, int finalJ) {
        if (cantBarcos > 0) {
            agregarImagenBoton(finalI, finalJ);
            cantBarcos = cantBarcos-1;
        }

        if (cantBarcos == 0 && !juegoEmpezado) {
            lblMensaje.setText(aux);
            juegoEmpezado = true;
        }
    }

    @Override
    public void handle(Event event) {
        int cant;
        try {
            cant = Integer.parseInt(txtCantBarcos.getText());
            cliente.mandarDatos("N"+cant);
            turno = true;
            lblMensaje.setText("Posiciona tus piezas en el tanlero");
            aux = "Es tu turno!!!";

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
        btnPosiciones[x][y].setDisable(true);
    }

    private void agregarImagenBoton2(int x, int y) {
        BackgroundImage myBI= new BackgroundImage(new Image("sample/assets/equis.JPG",320,320,false,true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1.0, 1.0, true, true, false, false));
        //btnPosiciones[x][y].setBackground(null);
        btnPosiciones[x][y].setBackground(new Background(myBI));
        btnPosiciones[x][y].setDisable(false);
        cantBarcosUndidos = cantBarcosUndidos+1;
        if(cantBarcosUndidos == Integer.parseInt(txtCantBarcos.getText())){
            cliente.mandarDatos("GHas ganado!!!");
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Batalla Naval");
            alerta.setHeaderText("Haz perdido");
            alerta.setContentText("ok");
            alerta.showAndWait();
        }
    }

    private void verificarDisparo(String coordenadas) {
        String[] parts = coordenadas.split(" ");
        String part1 = parts[0];
        String part2 = parts[1];

        int x, y;
        x = Integer.parseInt(part1);
        y = Integer.parseInt(part2);
        System.out.println("Coordenadas: "+x+", "+y);

        if(btnPosiciones[x][y].isDisable()) {
            agregarImagenBoton2(x,y);
        }
    }

    private void mostrarGanador(String ganador) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Batalla Naval");
        alerta.setHeaderText(ganador);
        alerta.setContentText("ok");
        alerta.showAndWait();
    }
}