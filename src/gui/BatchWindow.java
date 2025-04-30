package gui;

import application.model.Bygsort;
import application.model.Mark;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class BatchWindow extends Stage {
    private TextField txfBatchNr, txfMark, txfBygsort, txfRygemateriale, txfMaltBatch, txfStartDato, txfKommentar, txfInitialer;
    private Label lblError;
    private ComboBox<Mark> markComboBox;
    private ComboBox<Bygsort> bygsortComboBox;


    public BatchWindow(String title) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        setScene(scene);

    }


    //-------------------------------------------------------

    public void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblBatchNr = new Label("BatchNr");
        pane.add(lblBatchNr, 0, 0);

        txfBatchNr = new TextField();
        pane.add(txfBatchNr, 1, 0);
        txfBatchNr.setPrefWidth(200);

        Label lblMark = new Label("Mark");
        pane.add(lblMark, 0, 1);

        markComboBox = new ComboBox<>();
        markComboBox.getItems().addAll(Mark.MOSEVANG,Mark.STADSGAARD);
        pane.add(markComboBox, 1,1);


        Label lblBygsort = new Label("Bygsort");
        pane.add(lblBygsort, 0, 2);

        bygsortComboBox = new ComboBox<>();
        bygsortComboBox.getItems().addAll(Bygsort.EVERGREEN,Bygsort.IRINA,Bygsort.STAIRWAY);
        pane.add(bygsortComboBox,1,2);


        Label lblRygemateriale = new Label("Rygemateriale");
        pane.add(lblRygemateriale, 0, 3);

        txfRygemateriale = new TextField();
        pane.add(txfRygemateriale, 1, 3);

        Label lblMaltBatch = new Label("Maltbatch");
        pane.add(lblMaltBatch, 2, 0);

        txfMaltBatch = new TextField();
        pane.add(txfMaltBatch, 3, 0);

        Label lblStartDato = new Label("StartDato");
        pane.add(lblStartDato, 2, 1);

        txfStartDato = new TextField();
        pane.add(txfStartDato, 3, 1);

        Label lblInitaler = new Label("Initialer");
        pane.add(lblInitaler, 2, 2);

        txfInitialer = new TextField();
        pane.add(txfInitialer, 3, 2);

        Label lblKommentar = new Label("Kommentar");
        pane.add(lblKommentar, 2, 3);

        txfKommentar = new TextField();
        pane.add(txfKommentar, 3, 3);

        Button btnOpret = new Button("Opret batch");
        btnOpret.setOnAction(event -> this.opretAction());
        pane.add(btnOpret, 3, 5);
        GridPane.setHalignment(btnOpret, HPos.RIGHT);

        Button btnLuk = new Button("Luk");
        pane.add(btnLuk, 0, 5);
        GridPane.setHalignment(btnLuk, HPos.LEFT);
        btnLuk.setOnAction(event -> lukAction());

    }

        public void opretAction(){
        Mark mark = markComboBox.getSelectionModel().getSelectedItem();
        if (mark == null){
            lblError.setText("Vælg en mark");
        }
        Bygsort bygsort = bygsortComboBox.getSelectionModel().getSelectedItem();
        if (bygsort == null){
            lblError.setText("Vælg en bygsort");
        }
        String rygemateriale = txfRygemateriale.getText().trim();
        String initialer = txfInitialer.getText().trim();
        if(initialer.length() == 0){
            lblError.setText("Initialer skal udfyldes");
        }
        String maltBatch = txfMaltBatch.getText().trim();
        if (maltBatch.length() == 0){
            lblError.setText("MaltBatch skal udfyldes");
        }
        String kommentar = txfKommentar.getText().trim();
        String dato = txfStartDato.getText();
        if (dato.length() == 0){
            lblError.setText("Dato skal udfyldes");
        }
        LocalDate startDato = LocalDate.parse(dato);


    }

    public void lukAction(){

    }


}
