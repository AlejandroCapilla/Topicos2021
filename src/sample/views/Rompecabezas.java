package sample.views;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


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
        chBTamano.getSelectionModel().select(1);

        btnMesclar = new Button("Mesclar Tarjetas");
        btnMesclar.addEventHandler(MouseEvent.MOUSE_CLICKED,this);
        hBox = new HBox();
        hBox.getChildren().addAll(lblTarjetas,chBTamano,btnMesclar);

        borderPane.setTop(hBox);

        tablero = new GridPane();
        borderPane.setCenter(tablero);

        escena = new Scene(borderPane, 900, 600);
    }

    @Override
    public void handle(Event event) {
        vaciarArBotones();

        noPiezas = chBTamano.getSelectionModel().getSelectedIndex() + 3;
        btnTarjetas =  new Button[noPiezas][noPiezas];
        arAsignacion = new String[noPiezas][noPiezas];

        dir = noPiezas+"x"+noPiezas+"/";

        crearArImagenes();
        revolver();
        for (int i = 0; i < noPiezas; i++) {
            for (int j = 0; j < noPiezas; j++) {

                Image img = new Image("/sample/assets/rompecabezas/"+dir+arAsignacion[i][j]);
                ImageView imv = new ImageView(img);
                imv.setFitHeight(120);
                imv.setPreserveRatio(true);

                btnTarjetas[i][j] = new Button();
                int finalI = i;
                int finalJ = j;
                btnTarjetas[i][j].setOnAction(event1 -> intercambio(finalI, finalJ));
                btnTarjetas[i][j].setGraphic(imv);
                btnTarjetas[i][j].setPrefSize(50,50);
                tablero.add(btnTarjetas[i][j],j,i);
            }
        }
    }

    private void vaciarArBotones() {
        for (int i = 0; i < noPiezas; i++) {
            for (int j = 0; j < noPiezas; j++) {
                btnTarjetas[i][j].setVisible(false);
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
            Image img = new Image("sample/assets/rompecabezas/"+dir+arAsignacion[i][j]);
            ImageView imv = new ImageView(img);
            imv.setFitHeight(120);
            imv.setPreserveRatio(true);


            Image img2 = new Image("sample/assets/rompecabezas/"+dir+arAsignacion[xTemp][yTemp]);
            ImageView imv2 = new ImageView(img2);
            imv2.setFitHeight(120);
            imv2.setPreserveRatio(true);

            //esto hice para que funcionara
            String aux = arAsignacion[i][j];
            arAsignacion[i][j] = arAsignacion[xTemp][yTemp];
            arAsignacion[xTemp][yTemp] = aux;


            btnTarjetas[xTemp][yTemp].setGraphic(imv);
            btnTarjetas[i][j].setGraphic(imv2);
            bandera = !bandera;

        }
    }
}
