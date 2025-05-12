package gui;

import application.controller.Controller;
import application.model.Fadtype;
import application.model.Lager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class LagerPane extends GridPane {

    private ComboBox<Object> cbbLagre;
    private ComboBox<Fadtype> cbbFadtype;
    private ComboBox<Integer> cbbFills;
    private ComboBox<> cbbFills;

    public LagerPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblLagre = new Label("Lagre");
        this.add(lblLagre,0,0);

        cbbLagre = new ComboBox<>();
        cbbLagre.getItems().addAll(Controller.getLagre());
        cbbLagre.getItems().add("Ikke på lager");
        cbbLagre.getItems().add("Alle lagre");
        this.add(cbbLagre, 0, 1);

        Label lblFadtype = new Label("Fadtype");
        this.add(lblFadtype,0,2);

        cbbFadtype = new ComboBox<>();
        cbbFadtype.getItems().addAll(Fadtype.NEW,Fadtype.EXSHERRY,Fadtype.EXOLOROSO,Fadtype.EXBOURBON);
        this.add(cbbFadtype, 0, 3);

        Label lblFils = new Label("Fils");
        this.add(lblFadtype,0,4);

        cbbFills = new ComboBox<>();
        cbbFills.getItems().addAll(0,1,2,3);
        this.add(cbbFills, 0, 5);





        //kolonne 3
        Button btnOpretFad = new Button("Opret Fad");
        btnOpretFad.setMinWidth(100);
        btnOpretFad.setOnAction(event -> this.opretFadAction());
        this.add(btnOpretFad, 3, 0);

        Button btnOpretLager = new Button("Opret Lager");
        btnOpretLager.setMinWidth(100);
        btnOpretLager.setOnAction(event -> this.opretLagerAction());
        this.add(btnOpretLager, 3, 1);
    }



    // -------------------------------------------------------------------------

    private void opretFadAction() {

        OpretFadWindow dia = new OpretFadWindow("Opret fad");
        dia.showAndWait();

        // Wait for the modal dialog to close
        updateControls();
    }

    private void opretLagerAction() {

        OpretLagerWindow dia = new OpretLagerWindow("Opret Lager");
        dia.showAndWait();

        updateControls();

    }

    public void updateControls() {

    }



}
