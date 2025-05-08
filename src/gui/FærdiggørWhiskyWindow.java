package gui;

import application.controller.Controller;
import application.model.Whisky;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
        setMinWidth(930);
        setMinHeight(470);
    }

    //-------------------------------------------------------
    private TextField txfNavn, txfVand, txfAlkohol;
    private TextArea txaHistorie, txaLabel;
    private Label lblLiter, lblAntalFlasker, lblError;

    public void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblNavn = new Label("Whisky navn");
        pane.add(lblNavn, 0, 0);

        txfNavn = new TextField(whisky.getNavn());
        pane.add(txfNavn, 0, 1);

        lblLiter = new Label("Total liter: " + Controller.getAntalLiterWhisky(whisky));
        pane.add(lblLiter, 0, 2);

        lblAntalFlasker = new Label("Antal flasker: " + Controller.getAntalFlasker(whisky));

        Label lblVand = new Label("Påfyld vand");

        txfVand = new TextField("0");

        Button btnPåfyldVand = new Button("Påfyld Vand");
        btnPåfyldVand.setOnAction(event -> påfyldVandAction());

        VBox vBox = new VBox(lblAntalFlasker ,lblVand ,txfVand ,btnPåfyldVand);
        vBox.setSpacing(10);
        pane.add(vBox, 0, 3);

        Button btnLuk = new Button("Luk");
        btnLuk.setOnAction(event -> hide());
        pane.add(btnLuk, 0, 7);
        GridPane.setHalignment(btnLuk, HPos.LEFT);
        GridPane.setValignment(btnLuk, VPos.BOTTOM);

        lblError = new Label();
        pane.add(lblError, 0, 8);
        lblError.setStyle("-fx-text-fill: red");
        lblError.setMinWidth(250);

        //kolonne 1
        Label lblAlkoholProcent = new Label("AlkoholProcent");
        pane.add(lblAlkoholProcent, 1, 0);

        txfAlkohol = new TextField("0");
        pane.add(txfAlkohol, 1, 1);

        Label lblHistorie = new Label("Historie");
        pane.add(lblHistorie, 1, 2);

        txaHistorie = new TextArea(Controller.getWhiskeyBeskrivelse(whisky));
        txaHistorie.setMaxWidth(250);
        txaHistorie.setMinHeight(200);
        pane.add(txaHistorie, 1, 3);

        //kolonne 2
        Label lblLabel = new Label("Label");
        pane.add(lblLabel, 2, 0);

        txaLabel = new TextArea();
        txaLabel.setMaxWidth(350);
        pane.add(txaLabel, 2, 1, 1, 3);

        Button btnGenereLabel = new Button("Genere Label");
        btnGenereLabel.setOnAction(event -> genereLabelAction());
        pane.add(btnGenereLabel, 2, 4);
        GridPane.setHalignment(btnGenereLabel, HPos.RIGHT);

        Button btnFærdiggør = new Button("Færdiggør");
        btnFærdiggør.setOnAction(event -> færdiggørAction());
        pane.add(btnFærdiggør, 2, 7);
        GridPane.setHalignment(btnFærdiggør, HPos.RIGHT);
        GridPane.setValignment(btnLuk, VPos.BOTTOM);

    }

    // -------------------------------------------------------------------------

    private void påfyldVandAction() {
        int vand = Integer.parseInt(txfVand.getText().trim());
        if (vand != 0){
            Controller.påfyldVand(vand, whisky);
        }
        updateControls();
    }

    private void genereLabelAction() {
        txaLabel.setText(Controller.genereLabel(whisky, txfAlkohol.getText().trim()));
        txaLabel.setWrapText(true);
        updateControls();
    }

    private void færdiggørAction(){
        String label = txaLabel.getText().trim();
        double alkohol = Double.parseDouble(txfAlkohol.getText().trim());

        if (label.isBlank()){
            lblError.setText("Genere et label");
        } else if (alkohol <= 0){
            lblError.setText("Skriv en alkohol procent");
        } else {
            Controller.færdiggørWhisky(whisky, label, alkohol);
            hide();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Whiskyen er færdig!");
            alert.setHeaderText("Her er dens label:");
            alert.setContentText(whisky.getLabel());
            alert.showAndWait();
        }
    }

    private void updateControls(){
        txfNavn.setText(whisky.getNavn());
        lblLiter.setText("Total liter: " + Controller.getAntalLiterWhisky(whisky));
        lblAntalFlasker.setText("Antal flasker: " + Controller.getAntalFlasker(whisky));
        txfVand.clear();
        txaHistorie.setText(Controller.getWhiskeyBeskrivelse(whisky));
    }



}
