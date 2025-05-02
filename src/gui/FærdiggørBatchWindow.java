package gui;

import application.controller.Controller;
import application.model.Batch;
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

import java.time.LocalDate;


public class FærdiggørBatchWindow extends Stage {
    private Batch batch;

    public FærdiggørBatchWindow(String title, Batch batch) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        this.batch = batch;

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane,300,300);
        setScene(scene);
        setMinWidth(450);
        setMinHeight(300);


    }


    //-------------------------------------------------------
    private TextField txfVæskemængde, txfAlkoholProcent;
    private TextArea txaKommentar;
    private DatePicker datePicker;
    private Label lblError;

    public void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);


        Label lblSlutDato = new Label("Slut dato");
        lblSlutDato.setMinWidth(100);
        pane.add(lblSlutDato,0,0);

        datePicker = new DatePicker(LocalDate.now());
        pane.add(datePicker,0,1);

        Label lblAlkoholProcent = new Label("Alkoholprocent");
        pane.add(lblAlkoholProcent,0,2);

        txfAlkoholProcent = new TextField("0");
        pane.add(txfAlkoholProcent, 0,3);

        Label lblVæskemængde = new Label("Væskemængde");
        pane.add(lblVæskemængde,0,4);

        txfVæskemængde = new TextField("0");
        pane.add(txfVæskemængde,0,5);
        GridPane.setValignment(txfVæskemængde, VPos.TOP);

        Label lblKommentar = new Label("Kommentar");
        pane.add(lblKommentar,1,0);
        txaKommentar = new TextArea(batch.getKommentar());
        pane.add(txaKommentar,1,1,1,5);

        Button btnLuk = new Button("Luk");
        btnLuk.setOnAction(event -> lukAction());
        pane.add(btnLuk,0,6);
        GridPane.setHalignment(btnLuk, HPos.LEFT);
        GridPane.setValignment(btnLuk,VPos.BOTTOM);

        Button btnFærdiggør = new Button("Færdiggør");
        btnFærdiggør.setOnAction(event -> færdiggørAction());
        pane.add(btnFærdiggør,1,6);
        GridPane.setHalignment(btnFærdiggør,HPos.RIGHT);
        GridPane.setValignment(btnFærdiggør,VPos.BOTTOM);

        lblError = new Label();
        pane.add(lblError, 0, 7);
        lblError.setStyle("-fx-text-fill: red");
        lblError.setMinWidth(200);
        lblError.setWrapText(true);
    }

    // -------------------------------------------------------------------------

    private void færdiggørAction() {
        LocalDate slutDato = datePicker.getValue();
        double væskemængde = Double.parseDouble(txfVæskemængde.getText());
        double alkoholprocent = Double.parseDouble(txfAlkoholProcent.getText());

        if (slutDato.isBefore(batch.getStartDato())) {
            lblError.setText("Slutdato skal være efter startdato");
        } else if (væskemængde <= 0) {
            lblError.setText("Væskemængde skal være større end 0");
        } else if (alkoholprocent <= 0) {
            lblError.setText("Alkoholprocent skal være større end 0");
        } else {
            Controller.færdiggørBatch(batch,slutDato,alkoholprocent,væskemængde,txaKommentar.getText());
            lukAction();
        }
    }
    private void lukAction() {
        hide();
    }
}
