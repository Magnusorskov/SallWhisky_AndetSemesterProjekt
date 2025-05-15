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

public class OmhældningsPane extends GridPane {
    private TextArea txaDestillatBeskrivelse, txaFadBeskrivelse;
    private TextField txfNavn, txfAntalLiter;
    private Label lblFadTilgængeligLiter, lblDestillatVæskemængde, lblError;
    private ComboBox<Destillat> cmbDestillater;
    private ComboBox<Fad> cmbFade;
    private Button btnTilføj, btnFærdiggørDestillat;

    public OmhældningsPane(){
        this.setPadding(new Insets(20));
        this.setHgap(30);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        int width = 260;

        Label lblBatches = new Label("Destillat");
        lblBatches.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblBatches, 0, 0);

        cmbDestillater = new ComboBox<>();
        cmbDestillater.getItems().addAll(Controller.getDestillater());
        this.add(cmbDestillater, 0, 1);
        cmbDestillater.setPrefWidth(width);

        Label lblBeskrivelse = new Label("Beskrivelse");
        this.add(lblBeskrivelse, 0, 2);

        txaDestillatBeskrivelse = new TextArea();
        this.add(txaDestillatBeskrivelse, 0, 3);
        txaDestillatBeskrivelse.setPrefWidth(width);
        txaDestillatBeskrivelse.setEditable(false);

        ChangeListener<Destillat> listener = (ov, oldBatch, newBatch) -> this.selectionChangeBatch();
        cmbDestillater.getSelectionModel().selectedItemProperty().addListener(listener);

        //kolonne 1
        Label lblPåfyldFad = new Label("Omhæld på fad");
        lblPåfyldFad.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblPåfyldFad, 1, 0);

        lblDestillatVæskemængde = new Label("Destillat rest. væske: ");
        lblDestillatVæskemængde.setMinWidth(170);

        lblFadTilgængeligLiter = new Label("Fad ledig plads: ");
        lblFadTilgængeligLiter.setMinWidth(170);

        HBox hbVæskeVærdier = new HBox(lblDestillatVæskemængde, lblFadTilgængeligLiter);
        hbVæskeVærdier.setSpacing(20);
        this.add(hbVæskeVærdier, 1, 1);

        Label lblNavn = new Label("Destillat navn");

        txfNavn = new TextField();
        txfNavn.setDisable(true);

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
        cmbFade.getItems().setAll(Controller.getFadeDerKanOmhældesTil(Controller.getFade()));
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

        if (fad != null && fad.getDestillat() != null) {
            FærdiggørDestillatWindow dia = new FærdiggørDestillatWindow("Færdiggør Destillat", fad.getDestillat());
            dia.showAndWait();

            this.updateControls();
            btnTilføj.setDisable(true);
            btnFærdiggørDestillat.setDisable(true);
        }
    }

    public void updateControls() {
        cmbDestillater.getItems().setAll(Controller.getFærdigeDestillater());
        cmbFade.getItems().setAll(Controller.getFadeUdenFærdigDestillat());
        txfAntalLiter.clear();
    }


    private void selectionChangeBatch() {
        Destillat destillat = cmbDestillater.getSelectionModel().getSelectedItem();

        if (destillat != null) {
            txaDestillatBeskrivelse.setText(Controller.getBeskrivelse(destillat));
            lblDestillatVæskemængde.setText("Batch rest. væske: " + destillat.getAntalLiter());
            if (cmbFade.getSelectionModel().getSelectedItem() != null) {
                btnTilføj.setDisable(false);
            }
        } else {
            txaDestillatBeskrivelse.clear();
            lblDestillatVæskemængde.setText("Batch rest. væske: ");
        }

    }

    private void selectionChangeFad() {
        Fad fad = cmbFade.getSelectionModel().getSelectedItem();
        if (fad != null) {
            Destillat destillat = fad.getDestillat();
            lblFadTilgængeligLiter.setText("Fad ledig plads: " + fad.getTilgængeligeLiter());
            txaFadBeskrivelse.setText(Controller.getBeskrivelse(fad));
            if (destillat != null) {
                txfNavn.setText(destillat.getNavn());
                btnFærdiggørDestillat.setDisable(false);
            } else {
                txfNavn.clear();
                txfNavn.setDisable(false);
                btnFærdiggørDestillat.setDisable(true);
            }
            if (cmbDestillater.getSelectionModel().getSelectedItem() != null) {
                btnTilføj.setDisable(false);
            } else {
                btnTilføj.setDisable(true);
            }
        } else {
            lblFadTilgængeligLiter.setText("Fad ledig plads: ");
            txaFadBeskrivelse.clear();
            txfNavn.clear();
            txfNavn.setDisable(true);
        }
    }

    private void tilføjTilFadAction() {
        Destillat destillat = cmbDestillater.getSelectionModel().getSelectedItem();
        Fad fad = cmbFade.getSelectionModel().getSelectedItem();

        if (txfNavn.getText().isBlank() || txfAntalLiter.getText().isBlank()) {
            lblError.setText("Alle felter skal være udfyldt");
        } else {
            try {
                double antalLiter = Double.parseDouble(txfAntalLiter.getText());
                String navn = txfNavn.getText();

                Controller.omhældDestillat(destillat, antalLiter, fad, navn);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Fad er påfyldt!");
                alert.setContentText(destillat + "\nAntal liter: " + antalLiter);
                alert.showAndWait();

                txfAntalLiter.clear();
                lblFadTilgængeligLiter.setText("Fad ledig plads: " + fad.getTilgængeligeLiter());
                lblDestillatVæskemængde.setText("Batch rest. væske: " + destillat.getAntalLiter());
                txaFadBeskrivelse.setText(Controller.getBeskrivelse(fad));
                lblError.setText("");
                btnFærdiggørDestillat.setDisable(false);
                cmbFade.getItems().setAll(Controller.getFadeUdenFærdigDestillat());
                cmbFade.getSelectionModel().select(fad);

                if (destillat.getAntalLiter() == 0) {
                    updateControls();
                    cmbFade.getSelectionModel().select(fad);
                }

            } catch (NumberFormatException e) {
                lblError.setText("Indtast et gyldigt tal for liter (f.eks. 2.5)");
            } catch (IllegalArgumentException e) {
                lblError.setText(e.getMessage());
            }
        }
    }

}
