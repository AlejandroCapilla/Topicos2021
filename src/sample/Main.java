package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    private HBox hBox;
    private VBox vBox;
    private BorderPane pane;
    private FlowPane pane2;
    private GridPane grid;
    private AnchorPane anchor;
    private Button btn1, btn2, btn3;
    private Label lbl;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1, menCompetencia2, menCerrar;
    private MenuItem mitCalcu, mitSalir;
    private Scene escena;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        CrearUI();

        primaryStage.setTitle("Proyecto de clase Topicos avanzados de programacion 2021");
        primaryStage.setScene(escena);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    private void CrearUI() {
        vBox = new VBox();
        CrearMenu();
        CrearEscena();
    }
    private void CrearEscena(){
        vBox.getChildren().add(mnbPrincipal);
        escena = new Scene(vBox, 300, 70);
    }

    private void CrearMenu(){
        mnbPrincipal = new MenuBar();
        menCompetencia1 = new Menu("Competencia 1");
        menCompetencia2 = new Menu("Competencia 2");
        menCerrar = new Menu("Cerrar");
        mnbPrincipal.getMenus().addAll(menCompetencia1, menCompetencia2, menCerrar);

        mitCalcu = new MenuItem("Calculadora");
        menCompetencia1.getItems().add(mitCalcu);

        mitSalir = new MenuItem("Salir");
        mitSalir.setOnAction(event -> {System.exit(0);});
        menCerrar.getItems().add(mitSalir);
    }

    private void CrearButton(){
        btn1 = new Button("Boton 1");
        btn2 = new Button("Boton 2");
        btn3 = new Button("Boton 3");
    }

    private void CambiarColorButton(){
        btn1.setStyle("-fx-background-color:green;");
        btn2.setStyle("-fx-background-color:blue;");
        btn3.setStyle("-fx-background-color:red;");
    }

    private void CambiarTamanoButton(){
        btn1.setPrefSize(100,80);
        btn2.setPrefSize(100,50);
        btn3.setPrefSize(60,90);
    }

    private void CrearHBox(){
        hBox = new HBox();
        hBox.getChildren().addAll(btn1,btn2,btn3);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(20));
    }

    private void CrearVBox(){
        vBox = new VBox();
        vBox.getChildren().addAll(btn1, btn2, btn3);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20));
    }

    private void CrearBorderPane(){
        pane = new BorderPane();
        lbl = new Label("Esta es una etiqueta");
        pane.setTop(lbl);
    }

    private void CrearFlowPane(){
        pane2 = new FlowPane(Orientation.VERTICAL);
        pane2.getChildren().addAll(btn1, btn2, btn3);
    }

    private void CrearGridPane() {
        grid = new GridPane();
        grid.add(new Button("Posicion 0, 0"), 0, 0);
        grid.add(new Button("Posicion 1, 0"), 1, 0 );
        grid.add(new Button("Posicion 0, 1"), 0, 1);
        grid.add(new Button("Posicion 1, 1"), 1, 1);
        grid.add(new Button("Posicion 0, 2"), 0, 2);
        grid.add(new Button("Posicion 1, 2"), 1, 2);
        grid.add(new Button("Posicion Col 2 Fila 2"), 2, 2);
    }

    private void CrearAnchorPane(){
        anchor = new AnchorPane();
        AnchorPane.setTopAnchor(btn1,15.0);
        AnchorPane.setRightAnchor(btn1,15.0);
        AnchorPane.setBottomAnchor(btn2,30.0);
        AnchorPane.setLeftAnchor(btn2,100.0);
        anchor.getChildren().addAll(btn1,btn2,btn3);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
