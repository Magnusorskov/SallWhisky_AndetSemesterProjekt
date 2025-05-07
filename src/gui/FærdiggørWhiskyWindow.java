package gui;

import application.controller.Controller;
import application.model.Whisky;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FærdiggørWhiskyWindow extends Stage {
    private Whisky whisky;

    public FærdiggørWhiskyWindow(String title, Whisky whisky) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        this.whisky = whisky;

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane, 300, 300);
        setScene(scene);
        setMinWidth(450);
        setMinHeight(400);
    }

    //-------------------------------------------------------
    private TextField txfNavn, txfVand, txfAlkohol;
    private TextArea txaHistorie, txaLabel;
    private Label lblLiter, lblAntalFlasker;

    public void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblNavn = new Label("Whisky navn");
        pane.add(lblNavn, 0, 0);

        txfNavn = new TextField(whisky.getNavn());
        pane.add(txfNavn, 0, 1);

        lblLiter = new Label("Total liter: " + whisky.beregnAntalLiter());
        pane.add(lblLiter, 0, 2);

        lblAntalFlasker = new Label("Antal flasker: " + whisky.beregnAntalFlasker());
        pane.add(lblAntalFlasker, 0, 3);

        Label lblVand = new Label("Påfyld vand");
        pane.add(lblVand, 0, 4);

        txfVand = new TextField("0");
        pane.add(txfVand, 0, 5);

        Button btnPåfyldVand = new Button("Påfyld Vand");
        btnPåfyldVand.setOnAction(event -> påfyldVandAction());
        pane.add(btnPåfyldVand, 0, 6);
        GridPane.setHalignment(btnPåfyldVand, HPos.RIGHT);

        Button btnLuk = new Button("Luk");
        btnLuk.setOnAction(event -> lukAction());
        pane.add(btnLuk, 0, 7);
        GridPane.setHalignment(btnLuk, HPos.LEFT);
        GridPane.setValignment(btnLuk, VPos.BOTTOM);

        //kolonne 1
        Label lblAlkoholProcent = new Label("AlkoholProcent");
        pane.add(lblAlkoholProcent, 1, 0);

        txfAlkohol = new TextField("0");
        pane.add(txfAlkohol, 1, 1);

        Label lblHistorie = new Label("Historie");
        pane.add(lblHistorie, 1, 2);

        txaHistorie = new TextArea(whisky.hentHistorik());
        pane.add(txaHistorie, 1, 3, 1, 3);

        //kolonne 2
        Label lblLabel = new Label("Label");
        pane.add(lblLabel, 2, 0);

        txaLabel = new TextArea();
        pane.add(txaLabel, 2, 1, 1, 3);

        Button btnGenereLabel = new Button("Påfyld Vand");
        btnGenereLabel.setOnAction(event -> genereLabelAction());
        pane.add(btnGenereLabel, 2, 2);
        GridPane.setHalignment(btnGenereLabel, HPos.RIGHT);

    }

    // -------------------------------------------------------------------------

    private void lukAction() {
        hide();
    }

    private void påfyldVandAction() {

    }

    private void genereLabelAction() {

        txaLabel.setText(Controller.genereLabel(whisky));
    }

    private void updateControls(){
        txfNavn.setText(whisky.getNavn());
        lblLiter.setText("Total liter: " + whisky.beregnAntalLiter());
        lblAntalFlasker.setText("Antal flasker: " + whisky.beregnAntalFlasker());
        txfVand.setText("" + whisky.getLiterVand());
        txaHistorie.setText(whisky.hentHistorik());
    }

}
