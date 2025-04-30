package gui;

import application.model.Batch;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

public class BatchPane extends GridPane {
    private TextField txfBatchNr, txfMark, txfBygsort, txfRygemateriale, txfMaltBatch, txfStartDato, txfKommentar, txfInitialer;
    private ListView<Batch> lvwIgangværendeBatches;

    public BatchPane(){
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblBatch = new Label("Igangværende Batches");
        this.add(lblBatch,0,0);

        lvwIgangværendeBatches = new ListView<>();
        this.add(lvwIgangværendeBatches,0,1,1,6);
        lvwIgangværendeBatches.setPrefWidth(200);
        lvwIgangværendeBatches.setPrefHeight(200);
//        lvwIgangværendeBatches.getItems().setAll(Controller.lvwIgangværendeBatches());
//        ChangeListener<Batch> listener = (ov, oldBatch, newBatch) -> this.selectedBatchChanged();
//        lvwIgangværendeBatches.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblName = new Label("BatchNr:");
        this.add(lblName, 1, 1);

        txfBatchNr = new TextField();
        this.add(txfBatchNr, 2, 1);
        txfBatchNr.setPrefWidth(120);
        txfBatchNr.setEditable(false);

        Label lblBygsort = new Label("Bygsort:");
        this.add(lblBygsort, 1, 2);
        txfBygsort = new TextField();
        this.add(txfBygsort, 2, 2);
        txfBygsort.setEditable(false);

        Label lblRygemateriale = new Label("Rygemateriale:");
        this.add(lblRygemateriale, 1, 3);

        txfRygemateriale = new TextField();
        this.add(txfRygemateriale, 2, 3);
        txfRygemateriale.setEditable(false);

        Label lblMaltBatch = new Label("MaltBatch:");
        this.add(lblMaltBatch, 3, 1);

        txfMaltBatch = new TextField();
        this.add(txfMaltBatch, 4, 1);
        txfMaltBatch.setEditable(false);

        Label lblMark = new Label("Mark:");
        this.add(lblMark, 3, 2);

        txfMark = new TextField();
        this.add(txfMark, 4, 2);
        txfMark.setEditable(false);

        Label lblStartDato = new Label("StartDato:");
        this.add(lblStartDato, 3, 3);

        txfStartDato = new TextField();
        this.add(txfStartDato, 4, 3);
        txfStartDato.setEditable(false);

        Label lblInitial = new Label("Initaler:");
        this.add(lblInitial,1,4);

        txfInitialer = new TextField();
        this.add(txfInitialer, 2, 4);
        txfInitialer.setEditable(false);

        Label lblKommentar = new Label("Kommentar:");
        this.add(lblKommentar, 1, 5);

        txfKommentar = new TextField();
        this.add(txfKommentar, 2, 5,3,2);
        txfKommentar.setEditable(false);

        Button btnFærdig = new Button("Færdiggør Batch");
//        btnFærdig.setOnAction(event -> this.færdiggørAction());
        this.add(btnFærdig,0,7);

        Button btnOpret = new Button("Opret ny batch");
        btnOpret.setOnAction(event -> this.opretAction());
        this.add(btnOpret,4,7);
        GridPane.setHalignment(btnOpret, HPos.RIGHT);


    }

    // -------------------------------------------------------------------------
    private void opretAction() {
        BatchWindow dia = new BatchWindow("Opret Batch");
        dia.showAndWait();

//        lvwIgangværendeBatches.getItems().setAll(Controller.lvwIgangværendeBatches());
//        this.updateControls();
    }


}
