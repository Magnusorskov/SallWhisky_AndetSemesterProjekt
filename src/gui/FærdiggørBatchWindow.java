package gui;

import application.model.Batch;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
        setMinWidth(400);
        setMinHeight(400);


    }


    //-------------------------------------------------------

    public void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);


        Label lblSlutDato = new Label("Slut dato");
        pane.add(lblSlutDato,0,0);

        lblSlutDato.setMinWidth(100);
        DatePicker datePicker = new DatePicker(LocalDate.now());
        pane.add(datePicker,0,1);

        Label lblAlkoholProcent = new Label("Alkoholprocent");
        pane.add(lblAlkoholProcent,0,2);

        TextField txfAlkoholProcent = new TextField();
        pane.add(txfAlkoholProcent, 0,3);

        Label lblVæskemængde = new Label("Væskemængde");
        pane.add(lblVæskemængde,0,4);

        TextField txfVæskemængde = new TextField();
        txfVæskemængde.setPromptText("Liter");
        pane.add(txfVæskemængde,0,5);
        GridPane.setValignment(txfVæskemængde, VPos.TOP);

        Label lblKommentar = new Label("Kommentar");
        pane.add(lblKommentar,1,0);
        TextArea txaKommentar = new TextArea(batch.getKommentar());
        pane.add(txaKommentar,1,1,1,5);

    }
}
