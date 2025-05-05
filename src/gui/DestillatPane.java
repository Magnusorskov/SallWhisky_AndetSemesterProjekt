package gui;

import application.controller.Controller;
import application.model.Batch;
import application.model.Destillat;
import application.model.Fad;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DestillatPane extends GridPane {
    private TextArea txaBatchBeskrivelse, txaFadBeskrivelse;
    private TextField txfNavn, txfAntalLiter;
    private Label lblFadTilgængeligLiter, lblBatchVæskemængde;
    private ComboBox<Batch> cmbBatches;
    private ComboBox<Fad> cmbFade;

    public DestillatPane() {
        this.setPadding(new Insets(20));
        this.setHgap(30);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        int width = 260;
        Label lblBatches = new Label("Batches");
        this.add(lblBatches, 0, 0);

        cmbBatches = new ComboBox<>();
        cmbBatches.getItems().addAll(Controller.getFærdigeBatches());
        this.add(cmbBatches, 0, 1);
        cmbBatches.setPrefWidth(width);

        Label lblBeskrivelse = new Label("Beskrivelse");
        this.add(lblBeskrivelse, 0, 2);

        Batch batch = cmbBatches.getSelectionModel().getSelectedItem();
        txaBatchBeskrivelse = new TextArea(Controller.getBatchBeskrivelse(batch));
        this.add(txaBatchBeskrivelse, 0, 3);
        txaBatchBeskrivelse.setPrefWidth(width);

        ChangeListener<Batch> listener = (ov, oldBatch, newBatch) -> this.updateControls();
        cmbBatches.getSelectionModel().selectedItemProperty().addListener(listener);

        Label lblPåfyldFad = new Label("Påfyld fad");
        this.add(lblPåfyldFad, 1, 0);

        Label lblFade = new Label("Fade");
        lblFade.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblFade, 3, 0);

        cmbFade = new ComboBox<>();
        cmbFade.setPrefWidth(width);
        cmbFade.getItems().setAll(Controller.getFade());
        ChangeListener<Fad> fadListener = (ov, oldFad, newFad) -> this.updateControls();
        cmbFade.getSelectionModel().selectedItemProperty().addListener(fadListener);
        this.add(cmbFade, 3, 1);

        Label lblBeskrivelse2 = new Label("Beskrivelse");
        this.add(lblBeskrivelse2, 3, 2);

        txaFadBeskrivelse = new TextArea();
        this.add(txaFadBeskrivelse, 3, 3);
        txaFadBeskrivelse.setPrefWidth(width);

        lblBatchVæskemængde = new Label("Batch rest. væske: ");
        ;

        lblFadTilgængeligLiter = new Label("Fad ledig plads: ");


        HBox hbVæskeVærdier = new HBox(lblBatchVæskemængde, lblFadTilgængeligLiter);
        hbVæskeVærdier.setSpacing(20);
        this.add(hbVæskeVærdier, 1, 1);

        Label lblNavn = new Label("Navn");
        this.add(lblNavn, 1, 2);

        txfNavn = new TextField();
        if (cmbFade.getSelectionModel().getSelectedItem() != null) {
            txfNavn.setText(cmbFade.getSelectionModel().getSelectedItem().getDestillat().getNavn());
        }

        Label lblAntalLiter = new Label("Antal liter");
        this.add(lblAntalLiter, 1, 3);

        txfAntalLiter = new TextField();
        this.add(txfAntalLiter, 1, 4);

        Button btnTilføj = new Button("Tilføj til fad");
        this.add(btnTilføj, 1, 5);

        Button btnFærdiggørDestillat = new Button("Færdiggør destillat");
        this.add(btnFærdiggørDestillat, 3, 5);
        btnFærdiggørDestillat.setOnAction(event -> this.færdiggørAction());
        GridPane.setHalignment(btnFærdiggørDestillat, HPos.RIGHT);
    }
    // -------------------------------------------------------------------------

    private void færdiggørAction() {
        Destillat destillat = cmbFade.getSelectionModel().getSelectedItem().getDestillat();

        if (destillat != null){
            FærdiggørDestillatWindow dia = new FærdiggørDestillatWindow("Færdiggør Destillat", destillat);
            dia.showAndWait();

            this.updateControls();
        }
    }

    public void updateControls() {
        Batch batch = cmbBatches.getSelectionModel().getSelectedItem();
        Fad fad = cmbFade.getSelectionModel().getSelectedItem();
        if (batch != null) {
            txaBatchBeskrivelse.setText(Controller.getBatchBeskrivelse(batch));
            lblBatchVæskemængde.setText("Batch rest. væske: " + batch.getVæskemængde());
        } else if (fad != null) {
            lblFadTilgængeligLiter.setText("Fad ledig plads: " + fad.getTilgængeligeLiter());
        } else {
            cmbBatches.getItems().setAll(Controller.getFærdigeBatches());
            cmbFade.getItems().setAll(Controller.getFade());
        }
    }
}
