package gui;

import application.controller.Controller;
import application.model.Batch;
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

        lblBatchVæskemængde = new Label();

        lblFadTilgængeligLiter = new Label();
    }

    public void updateControls() {
        Batch batch = cmbBatches.getSelectionModel().getSelectedItem();
        if (batch != null) {
            txaBatchBeskrivelse.setText(Controller.getBatchBeskrivelse(batch));
            lblBatchVæskemængde.setText(batch.getVæskemængde() + "");
        } else {
            cmbBatches.getItems().setAll(Controller.getFærdigeBatches());
        }
    }
}
