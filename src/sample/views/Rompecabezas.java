package sample.views;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class Rompecabezas extends Stage implements EventHandler {

    private String[] arImagenes = {"fila-1-col-1.jpg","fila-1-col-2.jpg","fila-1-col-3.jpg","fila-1-col-4.jpg","fila-2-col-1.jpg","fila-2-col-2.jpg","fila-2-col-3.jpg","fila-2-col-4.jpg","fila-3-col-1.jpg","fila-3-col-2.jpg","fila-3-col-3.jpg","fila-3-col-4.jpg","fila-4-col-1.jpg","fila-4-col-2.jpg","fila-4-col-3.jpg","fila-4-col-4.jpg",};
    private String[][] arAsignacion;
    private BorderPane borderPane;
    private GridPane tablero;
    private Button[][] btnTarjetas;
    private HBox hBox;
    private Label lblTarjetas;
    private TextField txtTarjetas;
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
        arAsignacion = new String[4][4];
        revolver();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                Image img = new Image("sample/assets/"+arAsignacion[i][j]);
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

    private void intercambio(int i, int j) {
        if (!bandera){
            bandera = !bandera;
            xTemp = i;
            yTemp = j;
        }else{
            Image img = new Image("sample/assets/"+arAsignacion[i][j]);
            ImageView imv = new ImageView(img);
            imv.setFitHeight(120);
            imv.setPreserveRatio(true);


            Image img2 = new Image("sample/assets/"+arAsignacion[xTemp][yTemp]);
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

    private void revolver(){
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arAsignacion[i][j] = new String();
            }
        }

        int posx, posy = 0;
        for (int i = 0; i < arImagenes.length;) {
            posx = (int)(Math.random()*4);
            posy = (int)(Math.random()*4);
            if(arAsignacion[posx][posy].equals("")){
                arAsignacion[posx][posy] = arImagenes[i];
                i++;
            }
        }
    }
}
