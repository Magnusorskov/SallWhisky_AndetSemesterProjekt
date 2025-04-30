package gui;

import application.controller.Controller;
import application.model.Fadtype;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class OpretFadWindow extends Stage {

    public OpretFadWindow(String title) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane);
        setScene(scene);

        setMinWidth(400);
        setMinHeight(400);
    }

    // ---------------------------------------------------------------------
    private TextField txfLand, txfStørrelse;
    private ComboBox<Fadtype> cbbType;
    private Label lblError;

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblLand = new Label("Oprindelses Land");
        pane.add(lblLand, 0, 0);

        txfLand = new TextField();
        pane.add(txfLand, 0,1);

        Label lblType = new Label("Fadtype");
        pane.add(lblType, 0, 2);

        cbbType = new ComboBox<>();
        cbbType.getItems().addAll(Fadtype.EXBOURBON,Fadtype.EXOLOROSO,Fadtype.EXSHERRY,Fadtype.NEW);
        pane.add(cbbType, 0,3);

        Label lblStørrelse = new Label("Antal liter");
        pane.add(lblStørrelse, 0, 4);

        txfStørrelse = new TextField();
        pane.add(txfStørrelse, 0,5);

        HBox box = new HBox();
        pane.add(box,0,6);
        box.setSpacing(70);

        Button btnLuk = new Button("Luk");
        box.getChildren().add(btnLuk);
        GridPane.setHalignment(btnLuk, HPos.LEFT);
        btnLuk.setOnAction(event -> lukAction());

        Button btnOpret = new Button("Opret");
        box.getChildren().add(btnOpret);
        GridPane.setHalignment(btnOpret, HPos.RIGHT);
        btnOpret.setOnAction(event -> opretAction());

        lblError = new Label();
        pane.add(lblError, 0, 7);
        lblError.setStyle("-fx-text-fill: red");
    }

    // -------------------------------------------------------------------------


    private void lukAction() {
        hide();
    }

    private void opretAction() {
        String land = txfLand.getText();
        if (land.isBlank()){
            lblError.setText("Skriv et oprindelses land");
        }

        String s = txfStørrelse.getText();
        if(s.isBlank()){
            lblError.setText("Skriv en fad størrelse");
        }

        Fadtype type = cbbType.getSelectionModel().getSelectedItem();
        if(type == null){
            lblError.setText("Vælg en fad type");
        }

        if (!land.isBlank() && !s.isBlank() && type != null){
            double størrelse = Double.parseDouble(s);

            Controller.createFad(land, type, størrelse);
            hide();
        }
    }

}
