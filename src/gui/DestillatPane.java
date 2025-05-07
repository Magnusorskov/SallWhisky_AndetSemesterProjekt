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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DestillatPane extends GridPane {
    private TextArea txaBatchBeskrivelse, txaFadBeskrivelse;
    private TextField txfNavn, txfAntalLiter;
    private Label lblFadTilgængeligLiter, lblBatchVæskemængde, lblError;
    private ComboBox<Batch> cmbBatches;
    private ComboBox<Fad> cmbFade;
    private Button btnTilføj, btnFærdiggørDestillat;

    public DestillatPane() {
        this.setPadding(new Insets(20));
        this.setHgap(30);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        int width = 260;

        Label lblBatches = new Label("Batches");
        lblBatches.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblBatches, 0, 0);

        cmbBatches = new ComboBox<>();
        cmbBatches.getItems().addAll(Controller.getFærdigeBatches());
        this.add(cmbBatches, 0, 1);
        cmbBatches.setPrefWidth(width);

        Label lblBeskrivelse = new Label("Beskrivelse");
        this.add(lblBeskrivelse, 0, 2);

        txaBatchBeskrivelse = new TextArea();
        this.add(txaBatchBeskrivelse, 0, 3);
        txaBatchBeskrivelse.setPrefWidth(width);
        txaBatchBeskrivelse.setEditable(false);

        ChangeListener<Batch> listener = (ov, oldBatch, newBatch) -> this.selectionChangeBatch();
        cmbBatches.getSelectionModel().selectedItemProperty().addListener(listener);

        //kolonne 1
        Label lblPåfyldFad = new Label("Påfyld fad");
        lblPåfyldFad.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblPåfyldFad, 1, 0);

        lblBatchVæskemængde = new Label("Batch rest. væske: ");
        lblBatchVæskemængde.setMinWidth(170);

        lblFadTilgængeligLiter = new Label("Fad ledig plads: ");
        lblFadTilgængeligLiter.setMinWidth(170);
        HBox hbVæskeVærdier = new HBox(lblBatchVæskemængde, lblFadTilgængeligLiter);
        hbVæskeVærdier.setSpacing(20);
        this.add(hbVæskeVærdier, 1, 1);

        Label lblNavn = new Label("Destillat navn");

        txfNavn = new TextField();

        Label lblAntalLiter = new Label("Antal liter");
        this.add(lblAntalLiter, 1, 3);

        txfAntalLiter = new TextField();

        btnTilføj = new Button("Tilføj til fad");
        btnTilføj.setDisable(true);
        btnTilføj.setOnAction(event -> tilføjTilFadAction());

        lblError = new Label();
        lblError.setStyle("-fx-text-fill: red");

        VBox vbDestillatInfo = new VBox(lblNavn, txfNavn, lblAntalLiter, txfAntalLiter, hbVæskeVærdier, btnTilføj, lblError);
        vbDestillatInfo.setSpacing(10);
        this.add(vbDestillatInfo, 1, 2, 1, 5);

        //kolonne 2
        Label lblFade = new Label("Fade");
        lblFade.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblFade, 2, 0);

        cmbFade = new ComboBox<>();
        cmbFade.setPrefWidth(width);
        cmbFade.getItems().setAll(Controller.getFadeUdenFærdigDestillat());
        this.add(cmbFade, 2, 1);

        ChangeListener<Fad> fadListener = (ov, oldFad, newFad) -> this.selectionChangeFad();
        cmbFade.getSelectionModel().selectedItemProperty().addListener(fadListener);

        Label lblBeskrivelse2 = new Label("Beskrivelse");
        this.add(lblBeskrivelse2, 2, 2);

        txaFadBeskrivelse = new TextArea();
        txaFadBeskrivelse.setEditable(false);
        this.add(txaFadBeskrivelse, 2, 3);
        txaFadBeskrivelse.setPrefWidth(width);

        btnFærdiggørDestillat = new Button("Færdiggør destillat");
        this.add(btnFærdiggørDestillat, 2, 5);
        btnFærdiggørDestillat.setOnAction(event -> this.færdiggørAction());
        btnFærdiggørDestillat.setDisable(true);
        GridPane.setHalignment(btnFærdiggørDestillat, HPos.RIGHT);
    }
    // -------------------------------------------------------------------------

    private void færdiggørAction() {
        Fad fad = cmbFade.getSelectionModel().getSelectedItem();

        if (fad.getDestillat() != null) {
            FærdiggørDestillatWindow dia = new FærdiggørDestillatWindow("Færdiggør Destillat", fad.getDestillat());
            dia.showAndWait();

            this.updateControls();
        }
    }

    public void updateControls() {
        cmbBatches.getItems().setAll(Controller.getFærdigeBatchesMedTilgængeligeLiter());
        cmbFade.getItems().setAll(Controller.getFadeUdenFærdigDestillat());
    }


    private void selectionChangeBatch() {
        Batch batch = cmbBatches.getSelectionModel().getSelectedItem();

        if (batch != null) {
            txaBatchBeskrivelse.setText(Controller.getBatchBeskrivelse(batch));
            lblBatchVæskemængde.setText("Batch rest. væske: " + batch.getVæskemængde());
        } else {
            txaBatchBeskrivelse.clear();
            lblBatchVæskemængde.setText("Batch rest. væske: ");
        }
        if (cmbFade.getSelectionModel().getSelectedItem() != null) {
            btnTilføj.setDisable(false);

        } else {
            btnTilføj.setDisable(true);
        }
    }

    private void selectionChangeFad() {
        Fad fad = cmbFade.getSelectionModel().getSelectedItem();
        if (fad != null) {
            Destillat destillat = fad.getDestillat();
            lblFadTilgængeligLiter.setText("Fad ledig plads: " + fad.getTilgængeligeLiter());
            txaFadBeskrivelse.setText(Controller.getFadBeskrivelse(fad));
            if (destillat != null) {
                txfNavn.setText(destillat.getNavn());
                btnFærdiggørDestillat.setDisable(false);
            } else {
                txfNavn.clear();
                btnFærdiggørDestillat.setDisable(true);
            }
            if (cmbBatches.getSelectionModel().getSelectedItem() != null) {
                btnTilføj.setDisable(false);
            } else {
                btnTilføj.setDisable(true);
            }
        } else {
            lblFadTilgængeligLiter.setText("Fad ledig plads: ");
            txaFadBeskrivelse.clear();
            txfNavn.clear();
        }

    }

    private void tilføjTilFadAction() {
        Batch batch = cmbBatches.getSelectionModel().getSelectedItem();
        Fad fad = cmbFade.getSelectionModel().getSelectedItem();
        if (txfNavn.getText().isBlank() || txfAntalLiter.getText().isBlank()) {
            lblError.setText("Alle felter skal være udfyldt");
        } else {
            double antalLiter = Double.parseDouble(txfAntalLiter.getText());
            String navn = txfNavn.getText();

            try {
                Controller.påfyldFad(antalLiter, batch, navn, fad);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fad er påfyldt!");
                alert.setContentText(batch + "\nAntal liter: " + antalLiter);
                alert.showAndWait();
                txfAntalLiter.clear();
                lblFadTilgængeligLiter.setText("Fad ledig plads: " + fad.getTilgængeligeLiter());
                lblBatchVæskemængde.setText("Batch rest. væske: " + batch.getVæskemængde());
                txaFadBeskrivelse.setText(Controller.getFadBeskrivelse(fad));
                lblError.setText("");
                btnFærdiggørDestillat.setDisable(false);
                if (batch.getVæskemængde() == 0) {
                    updateControls();
                    cmbFade.getSelectionModel().select(fad);
                }
            } catch (IllegalArgumentException e) {
                lblError.setText(e.getMessage());
            }
        }
    }


}
