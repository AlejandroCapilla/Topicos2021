package sample.views;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.components.Corredor;

public class Pista extends Stage {
    Scene escena;
    private VBox vbox;
    private ProgressBar[] carriles;
    private Corredor[] corredores;
    private String[] nomCorredores = {"Homero", "Quick Silber", "Flash", "Bob Esponja", "Shrek"};

    public Pista() {
        CrearUI();
        this.setTitle("Pista de atletismo :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        vbox = new VBox();
        vbox.setSpacing(10.0);
        vbox.setPadding(new Insets(10.0));

        carriles = new ProgressBar[5];
        corredores = new Corredor[5];
        for (int i = 0; i < carriles.length; i++) {
            carriles[i] = new ProgressBar(0);
            carriles[i].setPrefWidth(180);
            corredores[i] = new Corredor(nomCorredores[i], carriles[i]);

            vbox.getChildren().add(carriles[i]);
        }
        escena = new Scene(vbox, 200, 150);
    }
}
