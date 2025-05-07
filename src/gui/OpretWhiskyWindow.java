package gui;

import application.controller.Controller;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretWhiskyWindow extends Stage {

    private TextField txfNavn;
    private Label lblError;



    public OpretWhiskyWindow(String title){
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        setScene(scene);

        setMinWidth(200);
        setMinHeight(200);
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblNavn = new Label("Navn på whisky");
        pane.add(lblNavn, 0, 0);

        txfNavn = new TextField();
        txfNavn.setMaxWidth(160);
        pane.add(txfNavn, 0, 1);

        HBox box = new HBox();
        pane.add(box, 0, 6);
        box.setSpacing(70);

        Button btnLuk = new Button("Luk");
        box.getChildren().add(btnLuk);
        GridPane.setHalignment(btnLuk, HPos.LEFT);
        btnLuk.setOnAction(event -> lukAction());

        Button btnOpret = new Button("Opret");
        box.getChildren().add(btnOpret);
        GridPane.setHalignment(btnOpret, HPos.RIGHT);
        btnOpret.setOnAction(event -> opretAction());

        lblError = new Label();
        pane.add(lblError, 0, 7);
        lblError.setStyle("-fx-text-fill: red");

    }

    private void opretAction() {
        String navn = txfNavn.getText();
        if (navn.isBlank()){
            lblError.setText("Skriv et navn på whisky'en");
        } else {
            Controller.createWhisky(navn);
            hide();
        }


    }

    private void lukAction() {
        hide();
    }

}
