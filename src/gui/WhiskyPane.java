package gui;

import application.controller.Controller;
import application.model.Destillat;
import application.model.DestillatMængde;
import application.model.Whisky;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class WhiskyPane extends GridPane {
    private TextArea txaDestillatBeskrivelse, txaWhiskeyBeskrivelse;
    private TextField txfLiter;
    private Label lblDestillatBeskrivelse,lblWhiskyBeskrivelse,lblTapning, lblWhisky, lblLiter, lblDestillater, lblError, lblDestillatVæskemængde;
    private ComboBox<Destillat> cmbDestillater;
    private ComboBox<Whisky> cmbWhisky;
    private Button btnTap, btnOpret, btnFærdiggør;

    public WhiskyPane(){
        this.setPadding(new Insets(20));
        this.setHgap(30);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        int width = 260;

        lblDestillater = new Label("Destillater");
        lblDestillater.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblDestillater,0,0);

        cmbDestillater = new ComboBox<>();
        cmbDestillater.getItems().addAll(Controller.getFærdigeDestillater());
        this.add(cmbDestillater,0,1);
        cmbDestillater.setPrefWidth(width);

        lblDestillatBeskrivelse = new Label("Beskrivelse");
        this.add(lblDestillatBeskrivelse, 0, 2);

        Destillat destillat = cmbDestillater.getSelectionModel().getSelectedItem();
        txaDestillatBeskrivelse = new TextArea(Controller.getDestillatBeskrivelse(destillat));
        this.add(txaDestillatBeskrivelse,0,3);
        txaDestillatBeskrivelse.setPrefWidth(width);
        txaDestillatBeskrivelse.setEditable(false);

        ChangeListener<Destillat> listener = (ov, oldDestillat, newDestillat) -> this.selectionChangeDestillat();
        cmbDestillater.getSelectionModel().selectedItemProperty().addListener(listener);

        //kolonne 1

        lblTapning = new Label("Tapning");
        lblTapning.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblTapning,1,0);

        lblLiter = new Label("Liter");
        this.add(lblLiter,1,2);

        txfLiter = new TextField();

        lblDestillatVæskemængde = new Label("Destillat rest. væske: ");
        lblDestillatVæskemængde.setMinWidth(170);

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

        lblWhiskyBeskrivelse = new Label("Beskrivelse");
        this.add(lblWhiskyBeskrivelse,2,2);

        Whisky whisky = cmbWhisky.getSelectionModel().getSelectedItem();
        txaWhiskeyBeskrivelse = new TextArea(Controller.getWhiskeyBeskrivelse(whisky));
        this.add(txaWhiskeyBeskrivelse,2,3);
        txaWhiskeyBeskrivelse.setPrefWidth(width);
        txaWhiskeyBeskrivelse.setEditable(false);

        btnFærdiggør = new Button("Færdiggør whisky");
        btnOpret = new Button("Opret whiskey");

        HBox hbBtns = new HBox(btnOpret,btnFærdiggør);
        hbBtns.setSpacing(10);
        this.add(hbBtns,2,4);

        btnOpret.setOnAction(event -> this.opretWhiskeyAction());

        btnFærdiggør.setOnAction(event -> this.færdiggørWhiskyAction());
        btnFærdiggør.setDisable(true);

    }

    public void updateControls(){
        cmbWhisky.getItems().setAll(Controller.getIgangværendeWhisky());
        cmbDestillater.getItems().setAll(Controller.getFærdigeDestillater());
        btnTap.setDisable(true);
        btnFærdiggør.setDisable(true);
        txfLiter.clear();
        lblError.setText("");

    }

    private void selectionChangeDestillat(){
        Destillat destillat = cmbDestillater.getSelectionModel().getSelectedItem();

        if (destillat != null){
            txaDestillatBeskrivelse.setText(Controller.getDestillatBeskrivelse(destillat));
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
            txaWhiskeyBeskrivelse.setText(Controller.getWhiskeyBeskrivelse(whisky));
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
                Controller.tapningAfDestillat(liter, destillat, whisky);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Tapning er gennemført");
                alert.setContentText(whisky.getNavn() + "\n Antal liter: " + liter);
                alert.showAndWait();

                this.updateControls();
                lblDestillatVæskemængde.setText("Destillat rest. væske: ");
                txaWhiskeyBeskrivelse.setText(Controller.getWhiskeyBeskrivelse(whisky));
                cmbWhisky.getSelectionModel().select(whisky);

            } catch (NumberFormatException e) {
                lblError.setText("Indtast et gyldigt tal for liter (f.eks. 2.5)");
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
