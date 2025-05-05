package gui;

import application.controller.Controller;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretLagerWindow extends Stage {

    //-----------------------------------------------------
    private TextField txfNavn, txfAdresse, txfAntalHylder, txfAntalReoler;
    private Label lblError;

    public OpretLagerWindow(String title) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        setScene(scene);

        setMinWidth(400);
        setMinHeight(400);
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblLand = new Label("Navn på lager");
        pane.add(lblLand, 0, 0);

        txfNavn = new TextField();
        txfNavn.setMaxWidth(160);
        pane.add(txfNavn, 0, 1);

        Label lblAdresse = new Label("Adresse");
        pane.add(lblAdresse, 1, 0);

        txfAdresse = new TextField();
        txfAdresse.setMaxWidth(160);
        pane.add(txfAdresse, 1, 1);

        Label lblAntalReoler = new Label("Antal reoler");
        pane.add(lblAntalReoler, 0, 2);

        txfAntalReoler = new TextField();
        txfAntalReoler.setMaxWidth(160);
        pane.add(txfAntalReoler, 0, 3);
        txfAntalReoler.setText("0");

        Label lblAntalHylder = new Label("Antal hylder");
        pane.add(lblAntalHylder, 1, 2);

        txfAntalHylder = new TextField();
        txfAntalHylder.setMaxWidth(160);
        pane.add(txfAntalHylder, 1, 3);
        txfAntalHylder.setText("0");


        Button btnLuk = new Button("Luk");
        btnLuk.setOnAction(event -> lukAction());
        pane.add(btnLuk, 0, 7);
        GridPane.setHalignment(btnLuk, HPos.LEFT);
        GridPane.setValignment(btnLuk, VPos.BOTTOM);

        Button btnOpret = new Button("Opret");
        btnOpret.setOnAction(event -> opretAction());
        pane.add(btnOpret, 2, 7);
        GridPane.setHalignment(btnOpret, HPos.RIGHT);
        GridPane.setValignment(btnOpret, VPos.BOTTOM);


        lblError = new Label();
        pane.add(lblError, 0, 5, 2, 1);
        lblError.setStyle("-fx-text-fill: red");

    }

    private void lukAction() {
        hide();
    }

    private void opretAction() {
        String navn = txfNavn.getText().trim();
        String adresse = txfAdresse.getText().trim();
        int antalReoler = Integer.parseInt(txfAntalReoler.getText().trim());
        int antalHylder = Integer.parseInt(txfAntalHylder.getText().trim());

        if (navn.isBlank()) {
            lblError.setText("Skriv et navn på lageret");
        } else if (adresse.isBlank()) {
            lblError.setText("Skriv adressen på lageret");
        } else if (antalReoler <= 0) {
            lblError.setText("Giv lageret et positivt antal rækker");
        } else if (antalHylder <= 0) {
            lblError.setText("Giv lageret et positivt antal hylder");
        } else {
            Controller.createLager(navn, antalReoler, antalHylder, adresse);
            hide();
        }

    }
}
