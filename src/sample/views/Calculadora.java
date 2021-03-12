package sample.views;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage implements EventHandler {
    private Scene escena;
    private VBox vBox;
    private TextField txtOperacion;
    private HBox[] hBoxes;
    private Button[] arBotones;
    private char[] arNumeros = {'7','8','9','/','4','5','6','*','1','2','3','+','0','.','=','-'};

    public Calculadora(){
        crearUI();
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.show();
    }

    private void crearUI() {
        vBox = new VBox();
        vBox.setPadding(new Insets(10)); // Espacio entre el contenedor y el contenido
        vBox.setSpacing(5);// Espacio entre cada elemento contenido

        txtOperacion = new TextField();
        txtOperacion.setEditable(false);
        txtOperacion.setPrefHeight(100);
        txtOperacion.setText("0");
        txtOperacion.setAlignment(Pos.BASELINE_RIGHT);

        hBoxes = new HBox[4];
        arBotones = new Button[16];

        int pos = 0;
        for (int i = 0; i < hBoxes.length; i++) {
            hBoxes[i] = new HBox();
            hBoxes[i].setSpacing(5);

            for (int j = 0; j < 4; j++) {
                arBotones[pos] = new Button(arNumeros[pos]+"");
                arBotones[pos].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventoCalcu(arNumeros[pos]));
                arBotones[pos].setPrefSize(50,100);
                hBoxes[i].getChildren().add(arBotones[pos]);
                pos++;
            }
        }
        vBox.getChildren().addAll(txtOperacion, hBoxes[0], hBoxes[1], hBoxes[2], hBoxes[3]);
        escena = new Scene(vBox,200,250);
    }

    @Override
    public void handle(Event event) {
        System.out.println("Mi primer Evento");
    }
}
class EventoCalcu implements EventHandler{

    char tecla;
    public EventoCalcu(char tecla){
        this.tecla = tecla;
    }
    @Override
    public void handle(Event event) {
        System.out.println(tecla);
    }
}