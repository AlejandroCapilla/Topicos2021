package sample.views;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Scanner;

public class Encriptador extends Stage implements EventHandler<KeyEvent> {
    private Scene escena;
    private HBox hBox;
    private VBox vBox;
    private ToolBar tolMenu;
    private TextArea txtEntrada;
    private TextArea txtSalida;
    private Button btnEncriptar;
    private Button btnTolAbrir;
    private FileChooser fileChooser;
    private File archivoSeleccionado;

    public Encriptador(){
        crearUI();
        this.setTitle("Funcion de encriptacion con HASH");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        vBox = new VBox();
        tolMenu = new ToolBar();
        btnTolAbrir = new Button();
        ImageView imv = new  ImageView("sample/assets/icon-open.png");
        imv.setFitWidth(35);
        imv.setPreserveRatio(true);
        btnTolAbrir.setGraphic(imv);
        btnTolAbrir.setOnAction(event -> abrirArchivo());
        tolMenu.getItems().add(btnTolAbrir);

        hBox = new HBox();
        txtEntrada = new TextArea();
        txtEntrada.setOnKeyReleased(this);

        txtSalida = new TextArea();
        txtSalida.setEditable(false);

        hBox.getChildren().addAll(txtEntrada, txtSalida);
        btnEncriptar = new Button("Encriptar entrada");
        btnEncriptar.setOnAction(event1 -> encriptarArchivo());

        vBox.getChildren().addAll(tolMenu, hBox, btnEncriptar);

        escena = new Scene(vBox, 600, 300);
    }

    private void abrirArchivo(){
        fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar archivo a encriptar :)");
        archivoSeleccionado = fileChooser.showOpenDialog(this);

        try {
            Scanner input = new Scanner(new File(String.valueOf(archivoSeleccionado)));
            while (input.hasNextLine()) {
                String linea = input.nextLine();
                txtEntrada.appendText(linea + "\n");
            }
            input.close();
        } catch (Exception ex) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Encriptador");
            alerta.setHeaderText("Error:");
            alerta.setContentText(ex.getMessage());
            alerta.showAndWait();
        }
    }

    @Override
    public void handle(KeyEvent event) {
        String texto = txtEntrada.getText();

        txtSalida.setText(encriptarTexto(texto));
    }

    private String encriptarTexto(String entrada) {
        String entradaEncriptada = "";
        for (int i = 0; i < entrada.length(); i++) {
            if(FuncionHASH(entrada.charAt(i)) == -30) {
                entradaEncriptada = entradaEncriptada + "\n" ;
            }else {
                entradaEncriptada = entradaEncriptada + FuncionHASH(entrada.charAt(i))+"";
            }
        }
        return entradaEncriptada;
    }

    private void encriptarArchivo() {
        txtSalida.setText("");
        try {
            Scanner input = new Scanner(new File(String.valueOf(archivoSeleccionado)));
            while (input.hasNextLine()) {
                String linea = input.nextLine();
                txtSalida.appendText(encriptarLinea(linea) + "\n");
            }
            input.close();
        } catch (Exception ex) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Encriptador");
            alerta.setHeaderText("Error:");
            alerta.setContentText(ex.getMessage());
            alerta.showAndWait();
        }
    }

    private String encriptarLinea(String l) {
        int tam = l.length();
        String lineaEncriptada = "";

        for (int i = 0; i < tam; i++) {
            lineaEncriptada = lineaEncriptada + FuncionHASH(l.charAt(i));
        }
        return lineaEncriptada;
    }

    private int FuncionHASH(char c) {
        int n = c;
        n = (n-60) + (2*n);

        return n;
    }
}
