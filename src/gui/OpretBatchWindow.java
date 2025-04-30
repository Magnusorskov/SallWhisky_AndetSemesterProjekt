package gui;

import application.controller.Controller;
import application.model.Bygsort;
import application.model.Mark;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class OpretBatchWindow extends Stage {
    private TextField txfBatchNr, txfMark, txfBygsort, txfRygemateriale, txfMaltBatch, txfStartDato, txfKommentar, txfInitialer;
    private Label lblError;
    private ComboBox<Mark> markComboBox;
    private ComboBox<Bygsort> bygsortComboBox;
    private DatePicker startDato;


    public OpretBatchWindow(String title) {
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

        Label lblInitaler = new Label("Initialer");
        pane.add(lblInitaler, 0, 0);

        txfInitialer = new TextField();
        pane.add(txfInitialer, 1, 0);

        Label lblMark = new Label("Mark");
        pane.add(lblMark, 0, 1);

        markComboBox = new ComboBox<>();
        markComboBox.getItems().addAll(Mark.MOSEVANG, Mark.STADSGAARD);
        pane.add(markComboBox, 1, 1);


        Label lblBygsort = new Label("Bygsort");
        pane.add(lblBygsort, 0, 2);

        bygsortComboBox = new ComboBox<>();
        bygsortComboBox.getItems().addAll(Bygsort.EVERGREEN, Bygsort.IRINA, Bygsort.STAIRWAY);
        pane.add(bygsortComboBox, 1, 2);


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

        startDato = new DatePicker(LocalDate.now());
        pane.add(startDato, 3, 1);

        Label lblKommentar = new Label("Kommentar");
        pane.add(lblKommentar, 2, 2);

        txfKommentar = new TextField();
        pane.add(txfKommentar, 3, 2);

        Button btnOpret = new Button("Opret batch");
        btnOpret.setOnAction(event -> this.opretAction());
        pane.add(btnOpret, 3, 5);
        GridPane.setHalignment(btnOpret, HPos.RIGHT);

        Button btnLuk = new Button("Luk");
        pane.add(btnLuk, 0, 5);
        GridPane.setHalignment(btnLuk, HPos.LEFT);
        btnLuk.setOnAction(event -> lukAction());

        lblError = new Label();
        pane.add(lblError, 0, 6);
        lblError.setStyle("-fx-text-fill: red");
        lblError.setPrefWidth(100);

    }

    public void opretAction() {
        Mark mark = markComboBox.getSelectionModel().getSelectedItem();
        if (mark == null) {
            lblError.setText("Vælg en mark");
        }

        Bygsort bygsort = bygsortComboBox.getSelectionModel().getSelectedItem();
        if (bygsort == null) {
            lblError.setText("Vælg en bygsort");
        }

        String rygemateriale = txfRygemateriale.getText().trim();
        String initialer = txfInitialer.getText().trim();
        if (initialer.isEmpty()) {
            lblError.setText("Initialer skal udfyldes");
        }

        String maltBatch = txfMaltBatch.getText().trim();
        if (maltBatch.isEmpty()) {
            lblError.setText("MaltBatch skal udfyldes");
        }

        String kommentar = txfKommentar.getText().trim();
        LocalDate dato = startDato.getValue();
        if (dato == null) {
            lblError.setText("Dato skal udfyldes");
        } else {
            Controller.createBatch(bygsort, mark, initialer, rygemateriale, maltBatch, dato, kommentar);
            hide();
        }

    }

    public void lukAction() {
        hide();
    }


}
