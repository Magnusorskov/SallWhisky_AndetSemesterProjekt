package gui;

import application.controller.Controller;
import application.model.Batch;
import application.model.Fad;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class DestillatPane extends GridPane {
    private TextArea txaBatchBeskrivelse, txaFadBeskrivelse;
    private TextField txfNavn, txfAntalLiter;
    private Label lblFadTilgængeligLiter, lblBatchVæskemængde;
    private ComboBox<Batch> cmbBatches;
    private ComboBox<Fad> cmbFade;

    public DestillatPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblBatches = new Label("Batches");
        this.add(lblBatches, 0, 0);

        cmbBatches = new ComboBox<>();
        cmbBatches.getItems().addAll(Controller.getFærdigeBatches());
        this.add(cmbBatches, 0, 1);

        Label lblBeskrivelse = new Label("Beskrivelse");
        this.add(lblBeskrivelse, 0, 2);

        Batch batch = cmbBatches.getSelectionModel().getSelectedItem();
        txaBatchBeskrivelse = new TextArea(Controller.getBatchBeskrivelse(batch));
        this.add(txaBatchBeskrivelse, 0, 3);

        ChangeListener<Batch> listener = (ov, oldBatch, newBatch) -> this.updateControls();
        cmbBatches.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblPåfyldFad = new Label("Påfyld fad");
        this.add(lblPåfyldFad,1,0);

        cmbFade = new ComboBox<>();
        cmbFade.getItems().setAll(Controller.getFade());
        ChangeListener<Fad> fadListener = (ov, oldFad, newFad) -> this.updateControls();
        cmbFade.getSelectionModel().selectedItemProperty().addListener(fadListener);
        this.add(cmbFade,2,0);

        lblBatchVæskemængde = new Label("Resterende væske i valgt batch: ");
        this.add(lblBatchVæskemængde,1,1);

        lblFadTilgængeligLiter = new Label("Tilgængelig plads i valgt fad: ");
        this.add(lblFadTilgængeligLiter,1,2);
    }

    public void updateControls() {
        Batch batch = cmbBatches.getSelectionModel().getSelectedItem();
        Fad fad = cmbFade.getSelectionModel().getSelectedItem();
        if (batch != null) {
            txaBatchBeskrivelse.setText(Controller.getBatchBeskrivelse(batch));
            lblBatchVæskemængde.setText("Resterende væske i valgt batch: " + batch.getVæskemængde());
        } else if (fad != null) {
            lblFadTilgængeligLiter.setText("Tilgængelig plads i valgt fad: " + fad.getTilgængeligeLiter());
        } else {
            cmbBatches.getItems().setAll(Controller.getFærdigeBatches());
            cmbFade.getItems().setAll(Controller.getFade());
        }
    }
}
