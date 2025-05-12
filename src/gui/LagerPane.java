package gui;

import application.controller.Controller;
import application.model.Fadtype;
import application.model.Lager;
import application.model.Land;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class LagerPane extends GridPane {

    private ComboBox<Object> cbbLagre, cbbFadtype, cbbFills, cbbLand;
    private TextField txfMåneder, txfStørrelse;

    public LagerPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblLagre = new Label("Lagre");
        this.add(lblLagre,0,0);


        cbbLagre = new ComboBox<>();
        cbbLagre.getItems().add(null);
        cbbLagre.getItems().add("På lager");
        cbbLagre.getItems().addAll(Controller.getLagre());
        cbbLagre.getItems().add("Ikke på lager");
        this.add(cbbLagre, 0, 1);

        Label lblFadtype = new Label("Fadtype");
        this.add(lblFadtype,0,2);

        cbbFadtype = new ComboBox<>();
        cbbFadtype.getItems().addAll(Fadtype.NEW,Fadtype.EXSHERRY,Fadtype.EXOLOROSO,Fadtype.EXBOURBON);
        this.add(cbbFadtype, 0, 3);

        Label lblFils = new Label("Fils");
        this.add(lblFils,0,4);

        cbbFills = new ComboBox<>();
        cbbFills.getItems().addAll(0,1,2,3);
        this.add(cbbFills, 0, 5);

        Label lblLand = new Label("Land");
        this.add(lblLand,0,6);

        cbbLand = new ComboBox<>();
        cbbLand.getItems().addAll(Land.SPANIEN,Land.PORTUGAL,Land.USA);
        this.add(cbbLand, 0, 7);

        Label lblMåneder = new Label("Alder i måneder");
        this.add(lblMåneder,0,8);

        txfMåneder = new TextField();
        this.add(txfMåneder,0,9);

        Label lblStørrelse = new Label("Størrelse");
        this.add(lblStørrelse,0,10);

        txfStørrelse = new TextField();
        this.add(txfStørrelse,0,11);



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
