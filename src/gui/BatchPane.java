package gui;

import application.controller.Controller;
import application.model.Batch;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BatchPane extends GridPane {
    private ListView<Batch> lvwIgangværendeBatches;
    private TextArea txaBatchInfo;

    public BatchPane() {
        this.setPadding(new Insets(20));
        this.setHgap(30);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblBatch = new Label("Igangværende Batches");
        lblBatch.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblBatch, 0, 0);

        lvwIgangværendeBatches = new ListView<>();
        this.add(lvwIgangværendeBatches, 0, 1);
        lvwIgangværendeBatches.setMinWidth(200);
        lvwIgangværendeBatches.setMinHeight(150);
        lvwIgangværendeBatches.getItems().setAll(Controller.getIgangværendeBatches());
        ChangeListener<Batch> listener = (ov, oldBatch, newBatch) -> this.selectedBatchChanged();
        lvwIgangværendeBatches.getSelectionModel().selectedItemProperty().addListener(listener);


        Label lblBatchInfo = new Label("Batch Info:");
        lblBatchInfo.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblBatchInfo, 1, 0);

        txaBatchInfo = new TextArea();
        this.add(txaBatchInfo, 1, 1);
        txaBatchInfo.setEditable(false);
        txaBatchInfo.setMinHeight(150);
        txaBatchInfo.setMaxWidth(300);

        Button btnFjernBatch = new Button("Fjern batch");
        btnFjernBatch.setOnAction(event -> removeAction(lvwIgangværendeBatches.getSelectionModel().getSelectedItem()));
        this.add(btnFjernBatch, 0, 3);
        GridPane.setHalignment(btnFjernBatch, HPos.LEFT);

        Button btnFærdig = new Button("Færdiggør Batch");
        btnFærdig.setOnAction(event -> this.færdiggørAction(lvwIgangværendeBatches.getSelectionModel().getSelectedItem()));
        this.add(btnFærdig, 0, 3);
        GridPane.setHalignment(btnFærdig, HPos.RIGHT);

        Button btnOpret = new Button("Opret ny batch");
        btnOpret.setOnAction(event -> this.opretAction());
        this.add(btnOpret, 1, 3);
        GridPane.setHalignment(btnOpret, HPos.RIGHT);
    }

    // -------------------------------------------------------------------------
    private void opretAction() {
        OpretBatchWindow dia = new OpretBatchWindow("Opret Batch");
        dia.showAndWait();

        lvwIgangværendeBatches.getItems().setAll(Controller.getIgangværendeBatches());
        this.updateControls();
    }

    public void updateControls() {
        Batch batch = lvwIgangværendeBatches.getSelectionModel().getSelectedItem();
        if (batch != null) {
            txaBatchInfo.setText(Controller.getBeskrivelse(batch));
        } else {
            txaBatchInfo.clear();
        }

    }

    private void selectedBatchChanged() {
        this.updateControls();
    }

    private void removeAction(Batch batch) {
        Controller.removeBatch(batch);
        this.updateControls();
        lvwIgangværendeBatches.getItems().setAll(Controller.getIgangværendeBatches());

    }

    private void færdiggørAction(Batch batch) {
        if (batch != null) {
            FærdiggørBatchWindow dia = new FærdiggørBatchWindow("Færdiggør Batch", batch);
            dia.showAndWait();
            lvwIgangværendeBatches.getItems().setAll(Controller.getIgangværendeBatches());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ingen batch valgt");
            alert.setContentText("Du skal vælge en batch at færdiggøre");
            alert.showAndWait();
        }
    }


}
