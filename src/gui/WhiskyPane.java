package gui;

import application.controller.Controller;
import application.model.Destillat;
import application.model.DestillatMængde;
import application.model.Whisky;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.event.ChangeListener;


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

//        Destillat destillat = cmbDestillater.getSelectionModel().getSelectedItem();
//        txaDestillatBeskrivelse = new TextArea(Controller.getDestillatBeskrivelse(destillat));
        this.add(txaDestillatBeskrivelse,0,3);
        txaDestillatBeskrivelse.setPrefWidth(width);
        txaDestillatBeskrivelse.setEditable(false);

//        ChangeListener<Destillat> listener = (ov,oldDestillat,newDestillat) -> this.selectionChangeDestillat();
//        cmbDestillater.getSelectionModel().selectedItemProperty().addListener(listener);


        //kolonne 1

        lblTapning = new Label("Tapning");
        lblTapning.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblTapning,1,0);

        lblLiter = new Label("Liter");
        this.add(lblLiter,1,1);

        txfLiter = new TextField();
        this.add(txfLiter,1,2);


        lblDestillatVæskemængde = new Label("Destillat rest. væske: ");
        lblDestillatVæskemængde.setMinWidth(170);
        this.add(lblDestillatVæskemængde,1,3);

        btnTap = new Button("Tap");
        btnTap.setDisable(true);
        btnTap.setOnAction(event -> tapTilWhiskyAction());

        lblError = new Label();
        lblError.setStyle("-fx-text-fill: red");
        this.add(lblError,1,4);
        lblError.setMinWidth(width);

        // Kolonne 2

        lblWhisky = new Label("Whisky");
        lblWhisky.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblWhisky,2,0);

        cmbWhisky = new ComboBox<>();
        cmbWhisky.setPrefWidth(width);
//        cmbWhisky.getItems().setAll(Controller.getIgangværendeWhisky());
        this.add(cmbWhisky,2,1);

//        ChangeListener<Whisky> whiskeyListener = (ov, oldWhisky, newWhisky) -> this.selectionChangeWhisky();
//        cmbWhisky.getSelectionModel().selectedItemProperty().addListener(whiskeyListener);

        lblWhiskyBeskrivelse = new Label("Beskrivelse");
        this.add(lblWhiskyBeskrivelse,2,2);

        txaWhiskeyBeskrivelse = new TextArea();
        txaWhiskeyBeskrivelse.setEditable(false);
        this.add(txaWhiskeyBeskrivelse,2,3);
        txaWhiskeyBeskrivelse.setPrefWidth(width);

        HBox hbBtns = new HBox(btnOpret,btnFærdiggør);
        hbBtns.setSpacing(10);
        this.add(hbBtns,2,4);

//        btnOpret.setOnAction(event -> this.opretWhiskeyAction());

        btnFærdiggør.setOnAction(event -> this.færdiggørWhiskyAction());
        btnFærdiggør.setDisable(true);

    }

    public void updateControls(){
        cmbWhisky.getItems().setAll(Controller.getIgangværendeWhisky());
        cmbDestillater.getItems().setAll(Controller.getFærdigeDestillater());
        btnTap.setDisable(true);
        btnFærdiggør.setDisable(true);

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
            lblError.setText("Vælg en whiskey");
        } else if (destillat == null){
            lblError.setText("Vælg et destillat at tappe fra");
        } else {
            double liter = Integer.parseInt(txfLiter.getText());
            Controller.tapningAfDestillat(liter,destillat,whisky);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Tapning er gennemført");
            alert.setContentText(whisky.getNavn() + "\n Antal liter: " + liter);
            alert.showAndWait();
            txfLiter.clear();
            lblDestillatVæskemængde.setText("Destillat rest. væske: " + destillat.getAntalLiter());
            txaWhiskeyBeskrivelse.setText(Controller.getWhiskeyBeskrivelse(whisky));
            lblError.setText("");
        }

    }

    private void færdiggørWhiskyAction(){
        Whisky whisky = cmbWhisky.getSelectionModel().getSelectedItem();

        if(whisky != null){
            FærdiggørWhiskeyWindow dia = new FærdiggørWhiskeyWindow("Færdiggør Whiskey",whisky);
            dia.showAndWait();

            this.updateControls();
        }
    }

    private void opretWhiskeyAction(){
        OpretWhiskyWindow dia = new OpretWhiskyWindow("Opret Whisky");
    }




}
