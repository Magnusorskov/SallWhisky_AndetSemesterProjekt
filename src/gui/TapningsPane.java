package gui;

import application.controller.Controller;
import application.model.Batch;
import application.model.Destillat;
import application.model.Whisky;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Optional;


public class TapningsPane extends GridPane {
    private TextArea txaDestillatBeskrivelse, txaWhiskeyBeskrivelse;
    private TextField txfLiter;
    private Label lblDestillatBeskrivelse,lblWhiskyBeskrivelse,lblTapning, lblWhisky, lblLiter, lblDestillater, lblError, lblDestillatVæskemængde;
    private ComboBox<Destillat> cmbDestillater;
    private ComboBox<Whisky> cmbWhisky;
    private Button btnTap, btnOpret, btnFærdiggør,btnTøm;

    public TapningsPane(){
        this.setPadding(new Insets(20));
        this.setHgap(30);
        this.setVgap(10);
        this.setGridLinesVisible(false);
//        this.setStyle("-fx-background-color: #adddff;");

        int width = 260;

        lblDestillater = new Label("Destillater");
        lblDestillater.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblDestillater,0,0);

        cmbDestillater = new ComboBox<>();
        cmbDestillater.getItems().addAll(Controller.getFærdigmodnedeDestillater());
        this.add(cmbDestillater,0,1);
        cmbDestillater.setPrefWidth(width);

        lblDestillatBeskrivelse = new Label("Beskrivelse");
        this.add(lblDestillatBeskrivelse, 0, 2);

        Destillat destillat = cmbDestillater.getSelectionModel().getSelectedItem();
        txaDestillatBeskrivelse = new TextArea();
        this.add(txaDestillatBeskrivelse,0,3);
        txaDestillatBeskrivelse.setPrefWidth(width);
        txaDestillatBeskrivelse.setEditable(false);
        txaDestillatBeskrivelse.setStyle("-fx-border-color: #B36A5E; " + "-fx-border-radius: 4; " + "-fx-border-width: 3px;");

        ChangeListener<Destillat> listener = (ov, oldDestillat, newDestillat) -> this.selectionChangeDestillat();
        cmbDestillater.getSelectionModel().selectedItemProperty().addListener(listener);
        cmbDestillater.setStyle("-fx-border-color: #B36A5E; " + "-fx-border-radius: 4; " + "-fx-border-width: 3px;");


        btnTøm = new Button("Tøm destillat");
        this.add(btnTøm,0,4);
        btnTøm.setDisable(true);
        btnTøm.setOnAction(event -> this.tømAction());

        //kolonne 1

        lblTapning = new Label("Tapning");
        lblTapning.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblTapning,1,0);

        lblLiter = new Label("Liter");
        this.add(lblLiter,1,2);

        txfLiter = new TextField();

        lblDestillatVæskemængde = new Label("Destillat rest. væske: ");
        lblDestillatVæskemængde.setMinWidth(360);

        btnTap = new Button("Tap destillat til whisky");
        btnTap.setDisable(false);
        btnTap.setOnAction(event -> tapTilWhiskyAction());

        lblError = new Label();
        lblError.setStyle("-fx-text-fill: red");
        lblError.setMinWidth(width);

        VBox vBox = new VBox(txfLiter,lblDestillatVæskemængde,btnTap,lblError);
        vBox.setSpacing(10);
        this.add(vBox,1,3);

        // Kolonne 2

        lblWhisky = new Label("Whisky");
        lblWhisky.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblWhisky,2,0);

        cmbWhisky = new ComboBox<>();
        cmbWhisky.setPrefWidth(width);
        cmbWhisky.getItems().setAll(Controller.getIgangværendeWhisky());
        this.add(cmbWhisky,2,1);

        ChangeListener<Whisky> whiskeyListener = (ov, oldWhisky, newWhisky) -> this.selectionChangeWhisky();
        cmbWhisky.getSelectionModel().selectedItemProperty().addListener(whiskeyListener);
        cmbWhisky.setStyle("-fx-border-color: #D4A849; " + "-fx-border-radius: 4; " + "-fx-border-width: 3px;");

        lblWhiskyBeskrivelse = new Label("Beskrivelse");
        this.add(lblWhiskyBeskrivelse,2,2);


        txaWhiskeyBeskrivelse = new TextArea();
        this.add(txaWhiskeyBeskrivelse,2,3);
        txaWhiskeyBeskrivelse.setPrefWidth(width);
        txaWhiskeyBeskrivelse.setEditable(false);
        txaWhiskeyBeskrivelse.setStyle("-fx-border-color: #D4A849; " + "-fx-border-radius: 4; " + "-fx-border-width: 3px;");

        btnOpret = new Button("Opret whiskey");
        btnOpret.setOnAction(event -> this.opretWhiskeyAction());
        this.add(btnOpret, 2,5);
        GridPane.setHalignment(btnOpret, HPos.RIGHT);

        btnFærdiggør = new Button("Færdiggør whisky");
        btnFærdiggør.setOnAction(event -> this.færdiggørWhiskyAction());
        btnFærdiggør.setDisable(true);
        this.add(btnFærdiggør, 2,5);
        GridPane.setHalignment(btnFærdiggør, HPos.LEFT);

    }

    // ---------------------------------------------------------------------

    public void updateControls(){
        cmbWhisky.getItems().setAll(Controller.getIgangværendeWhisky());
        cmbDestillater.getItems().setAll(Controller.getFærdigmodnedeDestillater());
        btnTap.setDisable(true);
        btnFærdiggør.setDisable(true);
        txfLiter.clear();
        lblError.setText("");

    }

    private void selectionChangeDestillat(){
        Destillat destillat = cmbDestillater.getSelectionModel().getSelectedItem();

        if (destillat != null){
            btnTøm.setDisable(false);
            txaDestillatBeskrivelse.setText(Controller.getBeskrivelse(destillat));
            lblDestillatVæskemængde.setText("Destillat rest. væske: " + destillat.getAntalLiter());
        } else {
            txaDestillatBeskrivelse.clear();
            lblDestillatVæskemængde.setText("Destillat rest. væske: ");
        }
        if (cmbWhisky.getSelectionModel().getSelectedItem() != null){
            btnTap.setDisable(false);
        } else {
            btnTap.setDisable(true);
        }
    }

    private void selectionChangeWhisky(){
        Whisky whisky = cmbWhisky.getSelectionModel().getSelectedItem();

        if (whisky != null){
            txaWhiskeyBeskrivelse.setText(Controller.getBeskrivelse(whisky));
            btnFærdiggør.setDisable(false);
            btnTap.setDisable(false);
        } else {
            txaWhiskeyBeskrivelse.clear();
            btnFærdiggør.setDisable(true);
        }
    }

    private void tapTilWhiskyAction(){
        Whisky whisky = cmbWhisky.getSelectionModel().getSelectedItem();
        Destillat destillat = cmbDestillater.getSelectionModel().getSelectedItem();

        if (txfLiter.getText().isBlank()){
            lblError.setText("Antal liter skal udfyldes");
        } else if (whisky == null){
            lblError.setText("Vælg en whisky");
        } else if (destillat == null){
            lblError.setText("Vælg et destillat at tappe fra");
        } else {
            try {
                double liter = Double.parseDouble(txfLiter.getText());
                if (liter <= destillat.getAntalLiter()){
                    Controller.tapningAfDestillat(liter, destillat, whisky);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Tapning er gennemført");
                    alert.setContentText(whisky.getNavn() + "\n Antal liter: " + liter);
                    alert.showAndWait();

                    this.updateControls();
                    lblDestillatVæskemængde.setText("Destillat rest. væske: ");
                    txaWhiskeyBeskrivelse.setText(Controller.getBeskrivelse(whisky));
                    cmbWhisky.getSelectionModel().select(whisky);
                } else {
                    lblError.setText("Der er ikke nok liter i destillatet at tappe af");
                }


            } catch (NumberFormatException e) {
                lblError.setText("Indtast et gyldigt tal for liter (f.eks. 2.5)");
            }

        }

    }
    private void tømAction(){
        Destillat destillat = cmbDestillater.getSelectionModel().getSelectedItem();
        if (destillat != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Bekræft handling");
            alert.setHeaderText("Er du sikker på, at du vil tømme destillatet?");
            alert.setContentText("Der er " + destillat.getAntalLiter() + " liter tilbage. Denne handling kan ikke fortrydes.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Controller.tømDestillat(destillat);
                updateControls();
            }
        }
    }

    private void færdiggørWhiskyAction(){
        Whisky whisky = cmbWhisky.getSelectionModel().getSelectedItem();

        if(whisky != null){
            FærdiggørWhiskyWindow dia = new FærdiggørWhiskyWindow("Færdiggør Whiskey",whisky);
            dia.showAndWait();

            this.updateControls();
        }
    }

    private void opretWhiskeyAction(){
        OpretWhiskyWindow dia = new OpretWhiskyWindow("Opret Whisky");
        dia.showAndWait();
        this.updateControls();
    }




}
