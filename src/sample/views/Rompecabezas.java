package sample.views;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.w3c.dom.css.RGBColor;


public class Rompecabezas extends Stage implements EventHandler {

    private String[] arImagenes;// = {"fila-1-col-1.jpg","fila-1-col-2.jpg","fila-1-col-3.jpg","fila-1-col-4.jpg","fila-2-col-1.jpg","fila-2-col-2.jpg","fila-2-col-3.jpg","fila-2-col-4.jpg","fila-3-col-1.jpg","fila-3-col-2.jpg","fila-3-col-3.jpg","fila-3-col-4.jpg","fila-4-col-1.jpg","fila-4-col-2.jpg","fila-4-col-3.jpg","fila-4-col-4.jpg",};
    private String[][] arAsignacion;
    private String dir;

    private BorderPane borderPane;
    private GridPane tablero;
    private Button[][] btnTarjetas;
    private HBox hBox;
    private Label lblTarjetas;
    private ChoiceBox chBTamano;
    private Button btnMesclar;
    private Scene escena;

    private int noPiezas;
    private boolean bandera = false; // indica si ya se selecciono una carta para el intercambio
    private int xTemp, yTemp = 0;

    public Rompecabezas(){
        CrearUI();
        this.setTitle("Rompecabezas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        borderPane = new BorderPane();
        lblTarjetas = new Label("Tamaño");
        chBTamano = new ChoiceBox();

        //Crear ChoiceBox
        chBTamano.getItems().addAll("3x3", "4x4", "5x5");
        chBTamano.getSelectionModel().select(0);

        btnMesclar = new Button("Mesclar Tarjetas");
        btnMesclar.addEventHandler(MouseEvent.MOUSE_CLICKED,this);
        hBox = new HBox();
        hBox.getChildren().addAll(lblTarjetas,chBTamano,btnMesclar);
        hBox.setPadding(new Insets(10));
        hBox.setSpacing(10);

        borderPane.setTop(hBox);

        tablero = new GridPane();
        borderPane.setCenter(tablero);

        escena = new Scene(borderPane, 900, 600);
        escena.getStylesheets().add(getClass().getResource("../css/StylesRompecabezas.css").toExternalForm());
    }

    @Override
    public void handle(Event event) {
        limpiarGridPane();


        noPiezas = chBTamano.getSelectionModel().getSelectedIndex() + 3;
        btnTarjetas =  new Button[noPiezas][noPiezas];
        arAsignacion = new String[noPiezas][noPiezas];

        dir = noPiezas+"x"+noPiezas+"/";

        crearArImagenes();
        revolver();
        for (int i = 0; i < noPiezas; i++) {
            for (int j = 0; j < noPiezas; j++) {
                int finalI = i;
                int finalJ = j;

                btnTarjetas[i][j] = new Button();
                btnTarjetas[i][j].setPrefSize(600,500);

                agregarImagenBoton(i, j, i, j);

                btnTarjetas[i][j].setOnAction(event1 -> intercambio(finalI, finalJ));

                tablero.add(btnTarjetas[i][j],j,i);
            }
        }
    }

    /*private void agregarImagenBoton(int x, int y, int xAux, int yAux) {
        Image img = new Image("sample/assets/rompecabezas/"+dir+arAsignacion[x][y]);
        ImageView imv = new ImageView(img);
        //imv.setFitHeight(100);
        imv.setPreserveRatio(true);
        btnTarjetas[xAux][yAux].setGraphic(imv);
    }*/

    //Agregar un Background a un boton
    private void agregarImagenBoton(int x, int y, int xAux, int yAux) {
        BackgroundImage myBI= new BackgroundImage(new Image("sample/assets/rompecabezas/"+dir+arAsignacion[x][y],320,320,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, false, false));

        btnTarjetas[xAux][yAux].setBackground(new Background(myBI));
    }

    private void limpiarGridPane() {
        for (int i = 0; i < noPiezas; i++) {
            for (int j = 0; j < noPiezas; j++) {
                tablero.getChildren().remove(btnTarjetas[i][j]);
            }
        }
    }

    private void crearArImagenes() {
        arImagenes = new String[noPiezas*noPiezas];

        for (int i = 0; i < noPiezas; i++) {
            for (int j = 0; j < noPiezas; j++) {
                arImagenes[(i * noPiezas) + j] = "fila-"+(i+1)+"-col-"+(j+1)+".jpg";
            }
        }
    }

    private void revolver(){
        for (int i = 0; i < noPiezas; i++) {
            for (int j = 0; j < noPiezas; j++) {
                arAsignacion[i][j] = new String();
            }
        }

        int posx, posy = 0;
        for (int i = 0; i < arImagenes.length;) {
            posx = (int)(Math.random()*noPiezas);
            posy = (int)(Math.random()*noPiezas);
            if(arAsignacion[posx][posy].equals("")){
                arAsignacion[posx][posy] = arImagenes[i];
                i++;
            }
        }
    }

    private void intercambio(int i, int j) {
        if (!bandera){
            bandera = !bandera;
            xTemp = i;
            yTemp = j;
        }else{
            agregarImagenBoton(i, j, xTemp, yTemp);

            agregarImagenBoton(xTemp, yTemp, i, j);

            //esto hice para que funcionara
            String aux = arAsignacion[i][j];
            arAsignacion[i][j] = arAsignacion[xTemp][yTemp];
            arAsignacion[xTemp][yTemp] = aux;

            bandera = !bandera;

            if (comprobarArmado()) {
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Rompecabezas");
                alerta.setHeaderText("Has resuelto el Rompecabezas :D!!!");
                alerta.setContentText("Intetar nuevamente");
                alerta.showAndWait();
            }

        }
    }

    private boolean comprobarArmado() {
        boolean estaArmado = true;
        int i = 0, j;

        do {
            j = 0;
            do {
                String nombre = "fila-"+(i + 1)+"-col-"+(j + 1)+".jpg";
                if (!arAsignacion[i][j].equals(nombre)) {
                    estaArmado = false;
                }
                j++;
            }while(j < noPiezas && estaArmado);
            i++;
        }while(i < noPiezas && estaArmado);

        return estaArmado;
    }
}
