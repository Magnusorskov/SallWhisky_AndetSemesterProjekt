package gui;

import application.controller.Controller;
import application.model.Batch;
import application.model.Historik;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OversigtsPane extends GridPane {
    private ComboBox<String> cbbOversigt;
    private ListView<Historik> lvwOversigt;
    private TextArea txaForhåndsvisning;

    public OversigtsPane(){
        this.setPadding(new Insets(20));
        this.setHgap(30);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Label lblType = new Label("Type");
        lblType.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblType, 0, 0);

        cbbOversigt = new ComboBox<>();
        cbbOversigt.getItems().add(null);
        cbbOversigt.getItems().add("Batches");
        cbbOversigt.getItems().add("Destillater");
        cbbOversigt.getItems().add("Whiskyer");
        cbbOversigt.getItems().add("Fade");
        this.add(cbbOversigt, 0, 1);
        GridPane.setValignment(cbbOversigt, VPos.TOP);

        ChangeListener<String> oversigtListener = (ov, oldOversigt, newOversigt) -> this.selectionChangeOversigt();
        cbbOversigt.getSelectionModel().selectedItemProperty().addListener(oversigtListener);

        //kolonne 1
        Label lblOversigt = new Label("Oversigt");
        lblOversigt.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblOversigt, 1, 0);

        lvwOversigt = new ListView<>();
        lvwOversigt.setMinWidth(300);
        this.add(lvwOversigt, 1, 1);
        lvwOversigt.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ChangeListener<Historik> forhåndsvisningListener = (ov, oldForhåndsvisning, newForhåndsvisning) -> this.selectionChangeForhåndsvisning();
        lvwOversigt.getSelectionModel().selectedItemProperty().addListener(forhåndsvisningListener);

        //kolonne 2
        Label lblForhåndsvisning = new Label("Forhåndsvisning");
        lblForhåndsvisning.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblForhåndsvisning, 2, 0);

        txaForhåndsvisning = new TextArea();
        txaForhåndsvisning.setMaxWidth(250);
        txaForhåndsvisning.setMinHeight(200);
        this.add(txaForhåndsvisning, 2, 1);

        Button btnUdtrækTilFil = new Button("Udtræk til fil");
        btnUdtrækTilFil.setOnAction(event -> this.udtrækAction());
        GridPane.setHalignment(btnUdtrækTilFil, HPos.RIGHT);
        this.add(btnUdtrækTilFil, 2, 2);

    }

    // ---------------------------------------------------------------------

    private void selectionChangeOversigt() {

        if (cbbOversigt.getSelectionModel().getSelectedItem() == null) {
            lvwOversigt.getItems().setAll();
        } else {
            String type = cbbOversigt.getSelectionModel().getSelectedItem();

            if (type.equals("Batches")){
                List<Batch> batches = new ArrayList<>(Controller.getFærdigeBatches());
                batches.addAll(Controller.getIgangværendeBatches());

                lvwOversigt.getItems().setAll(batches);
            } else if (type.equals("Destillater")){
                lvwOversigt.getItems().setAll(Controller.getDestillater());
            } else if (type.equals("Whiskyer")){
                lvwOversigt.getItems().setAll(Controller.getWhiskyer());
            } else {
                lvwOversigt.getItems().setAll(Controller.getFade());
            }
        }
    }

    private void selectionChangeForhåndsvisning() {
        List<Historik> valgte = lvwOversigt.getSelectionModel().getSelectedItems();

        if (valgte != null){
            txaForhåndsvisning.setText(Controller.kombinerBeskrivelser(valgte));
        }
    }

    private void udtrækAction(){
        String forhåndsvisning = txaForhåndsvisning.getText().trim();

        if (forhåndsvisning.isEmpty()){
            File file = Controller.udtrækTilFilPåList(forhåndsvisning);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oversigten er gemt!");
            alert.setContentText("Oversigten er gemt som " + file.getName());
            alert.showAndWait();
        }

    }

    public void updateControls(){
        lvwOversigt.getItems().setAll();
        txaForhåndsvisning.clear();
        cbbOversigt.getSelectionModel().selectFirst();
    }
}
