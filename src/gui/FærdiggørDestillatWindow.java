package gui;

import application.controller.Controller;
import application.model.Batch;
import application.model.Destillat;
import application.model.Lager;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class FærdiggørDestillatWindow extends Stage {
    private Destillat destillat;

    public FærdiggørDestillatWindow(String title, Destillat destillat) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        this.destillat = destillat;

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane,300,300);
        setScene(scene);
        setMinWidth(450);
        setMinHeight(300);


    }

    //-------------------------------------------------------

    private TextField txfNavn, txfLiter, txfAlkoholProcent, txfReol, txfHylde;
    private DatePicker datePicker;
    private ComboBox<Lager> cmbLager;

    public void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblDestillat = new Label("Destillat");
        pane.add(lblDestillat,0,0);

        Label lblNavn = new Label("Destillat");
        pane.add(lblNavn,0,1);

        txfNavn = new TextField(destillat.getNavn());
        pane.add(txfNavn, 0,2);

        Label lblLiter = new Label("Liter");
        pane.add(lblLiter,0,3);

        txfLiter = new TextField("" + destillat.beregnAntalLiter());
        pane.add(txfLiter, 0,4);
        txfLiter.setEditable(false);

        Label lblAlkoholProcent = new Label("Alkoholprocent");
        pane.add(lblAlkoholProcent,0,5);

        txfAlkoholProcent = new TextField();
        pane.add(txfAlkoholProcent, 0,6);

        Label lblDato = new Label("Påfyldningsdato");
        pane.add(lblDato,0,7);

        datePicker = new DatePicker(LocalDate.now());
        pane.add(datePicker,0,8);

        Button btnLuk = new Button("Luk");
        btnLuk.setOnAction(event -> lukAction());
        pane.add(btnLuk,0,9);
        GridPane.setHalignment(btnLuk, HPos.LEFT);
        GridPane.setValignment(btnLuk, VPos.BOTTOM);

        //kolonne 1
        Label lblPlacering = new Label("Placering");
        pane.add(lblPlacering,1,0);

        Label lblLager = new Label("Lager");
        pane.add(lblNavn,1,1);

        cmbLager = new ComboBox<>();
//        cmbLager.getItems().addAll(Controller.getLagre());
        pane.add(cmbLager, 1,2);

        Label lblReol = new Label("Reol nummer");
        pane.add(lblReol,0,3);

        txfReol = new TextField();
        pane.add(txfReol, 0,4);

        Label lblHylde = new Label("Hylde nummer");
        pane.add(lblHylde,0,5);

        txfHylde = new TextField();
        pane.add(txfHylde, 0,6);

        Button btnFærdiggør = new Button("Færdiggør");
        btnFærdiggør.setOnAction(event -> færdiggørAction());
        pane.add(btnFærdiggør,1,9);
        GridPane.setHalignment(btnFærdiggør,HPos.RIGHT);
        GridPane.setValignment(btnFærdiggør,VPos.BOTTOM);
    }

    // -------------------------------------------------------------------------

    private void færdiggørAction() {

    }

    private void lukAction() {
        hide();
    }


}
