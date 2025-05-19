package gui;

import application.controller.Controller;
import application.model.Fad;
import application.model.Fadtype;
import application.model.Lagervare;
import application.model.Land;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LagerPane extends GridPane {

    private ComboBox<Object> cbbLagre, cbbFadtype, cbbFills, cbbLand;
    private TextField txfMåneder, txfStørrelse;
    private RadioButton rbTom, rbFyldt, rbAlle;
    private ListView<Fad> lvwFad;
    private TextArea txaBeskrivelse;

    public LagerPane() {
        this.setPadding(new Insets(20));
        this.setHgap(30);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        int width = 200;

        Label lblSøgeKriterier = new Label("Søgekriterier");
        lblSøgeKriterier.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblSøgeKriterier, 0, 0);

        Label lblLagre = new Label("Lagre");
        this.add(lblLagre, 0, 1);

        cbbLagre = new ComboBox<>();
        cbbLagre.setMaxWidth(width);
        cbbLagre.getItems().add(null);
        cbbLagre.getItems().add("På lager");
        cbbLagre.getItems().add("Ikke på lager");
        cbbLagre.getItems().addAll(Controller.getLagre());
        this.add(cbbLagre, 0, 2);

        Label lblFadtype = new Label("Fadtype");
        this.add(lblFadtype, 0, 3);

        cbbFadtype = new ComboBox<>();
        cbbFadtype.setMinWidth(width);
        cbbFadtype.getItems().add(null);
        cbbFadtype.getItems().addAll(Fadtype.NEW, Fadtype.EXSHERRY, Fadtype.EXOLOROSO, Fadtype.EXBOURBON);
        this.add(cbbFadtype, 0, 4);

        Label lblFils = new Label("Fils");
        this.add(lblFils, 0, 5);

        cbbFills = new ComboBox<>();
        cbbFills.setMinWidth(width);
        cbbFills.getItems().add(null);
        cbbFills.getItems().addAll(0, 1, 2, 3);
        this.add(cbbFills, 0, 6);

        Label lblLand = new Label("Land");
        this.add(lblLand, 0, 7);

        cbbLand = new ComboBox<>();
        cbbLand.setMinWidth(width);
        cbbLand.getItems().add(null);
        cbbLand.getItems().addAll(Land.SPANIEN, Land.PORTUGAL, Land.USA);
        this.add(cbbLand, 0, 8);

        Label lblMåneder = new Label("Alder i måneder");
        this.add(lblMåneder, 0, 9);

        txfMåneder = new TextField();
        this.add(txfMåneder, 0, 10);

        Label lblStørrelse = new Label("Størrelse");
        this.add(lblStørrelse, 0, 11);

        txfStørrelse = new TextField();
        this.add(txfStørrelse, 0, 12);

        ToggleGroup group = new ToggleGroup();
        HBox hBox = new HBox();
        hBox.setSpacing(7);
        this.add(hBox, 0, 13);

        rbTom = new RadioButton("Tomme");
        group.getToggles().add(rbTom);
        hBox.getChildren().add(rbTom);

        rbFyldt = new RadioButton("Fyldte");
        group.getToggles().add(rbFyldt);
        hBox.getChildren().add(rbFyldt);

        rbAlle = new RadioButton("Alle");
        group.getToggles().add(rbAlle);
        hBox.getChildren().add(rbAlle);

        Button btnSøg = new Button("Søg");
        btnSøg.setOnAction(event -> this.søgAction());
        GridPane.setHalignment(btnSøg, HPos.RIGHT);
        this.add(btnSøg, 0, 14);
        btnSøg.setMinWidth(70);

        //kolonne 1
        Label lblFade = new Label("Fade");
        lblFade.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblFade, 1, 0);

        lvwFad = new ListView<>();
        lvwFad.setMinWidth(300);
        this.add(lvwFad, 1, 1, 1, 14);

        ChangeListener<Fad> fadListener = (ov, oldFad, newFad) -> this.selectionChangeFad();
        lvwFad.getSelectionModel().selectedItemProperty().addListener(fadListener);

        //kolonne 2
        Label lblBeskrivelse = new Label("Beskrivelse");
        lblBeskrivelse.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.add(lblBeskrivelse, 2, 0);

        txaBeskrivelse = new TextArea();
        txaBeskrivelse.setMaxWidth(250);
        txaBeskrivelse.setMinHeight(200);
        this.add(txaBeskrivelse, 2, 1, 1, 6);

        Button btnFjern = new Button("Fjern");
        btnFjern.setOnAction(event -> this.fjernAction());
        GridPane.setHalignment(btnFjern, HPos.LEFT);
        this.add(btnFjern, 2, 7);

        Button btnÆndrePlacering = new Button("Vælg placering");
        btnÆndrePlacering.setOnAction(event -> this.placeringAction());
        GridPane.setHalignment(btnÆndrePlacering, HPos.RIGHT);
        this.add(btnÆndrePlacering, 2, 7);

        //kolonne 3
        Button btnOpretLager = new Button("Opret Lager");
        btnOpretLager.setMinWidth(100);
        btnOpretLager.setOnAction(event -> this.opretLagerAction());
        this.add(btnOpretLager, 3, 1);

        Button btnOpretFad = new Button("Opret Fad");
        btnOpretFad.setMinWidth(100);
        btnOpretFad.setOnAction(event -> this.opretFadAction());
        this.add(btnOpretFad, 3, 2);

        Button btnUdvidLager = new Button("Udvid Lager");
        btnUdvidLager.setMinWidth(100);
        btnUdvidLager.setOnAction(event -> this.udvidLagerAction());
        this.add(btnUdvidLager, 3, 3);
    }


    // -------------------------------------------------------------------------

    private void selectionChangeFad() {
        Fad fad = lvwFad.getSelectionModel().getSelectedItem();
        if (fad != null) {
            txaBeskrivelse.setText(Controller.getBeskrivelse(fad));
        }
    }

    private void søgAction() {
        Fadtype fadtype = (Fadtype) cbbFadtype.getSelectionModel().getSelectedItem();

        Integer fills = (Integer) cbbFills.getSelectionModel().getSelectedItem();

        Land land = (Land) cbbLand.getSelectionModel().getSelectedItem();

        Integer alderPåDestillat = null;
        if (!txfMåneder.getText().isBlank()) {
            alderPåDestillat = Integer.valueOf(txfMåneder.getText());
        }
        Integer størrelse = null;
        if (!txfStørrelse.getText().isBlank()) {
            størrelse = Integer.valueOf(txfStørrelse.getText());
        }

        Boolean fyldt = null;
        if (rbFyldt.isSelected()) {
            fyldt = true;
        } else if (rbTom.isSelected()) {
            fyldt = false;
        }

        String lagret = null;
        if (cbbLagre.getSelectionModel().getSelectedItem() != null) {
            lagret = cbbLagre.getSelectionModel().getSelectedItem().toString();
        }

        lvwFad.getItems().setAll(Controller.fadSoegning(fadtype, fills, land, alderPåDestillat, størrelse, fyldt, lagret));
    }

    private void fjernAction() {
        Fad fad = lvwFad.getSelectionModel().getSelectedItem();

        if (fad != null) {
            if (fad.getDestillat() != null) {
                Controller.fjernLagerVare(fad);
                updateControls();
            }
        }
    }

    private void placeringAction() {
        Lagervare lagervare = lvwFad.getSelectionModel().getSelectedItem();

        if (lagervare != null) {
            VælgPlaceringWindow dia = new VælgPlaceringWindow("Ændre placering", lagervare);
            dia.showAndWait();

            updateControls();
        }
    }

    private void opretFadAction() {

        OpretFadWindow dia = new OpretFadWindow("Opret fad");
        dia.showAndWait();

        updateControls();
    }

    private void opretLagerAction() {

        OpretLagerWindow dia = new OpretLagerWindow("Opret Lager");
        dia.showAndWait();

        updateControls();

    }

    private void udvidLagerAction() {
        UdvidLagerWindow dia = new UdvidLagerWindow("Udvid Lager");
        dia.showAndWait();

        updateControls();
    }

    public void updateControls() {
        cbbLagre.getSelectionModel().selectFirst();
        cbbFadtype.getSelectionModel().selectFirst();
        cbbFills.getSelectionModel().selectFirst();
        cbbLand.getSelectionModel().selectFirst();
        txfStørrelse.clear();
        txfStørrelse.clear();
        txaBeskrivelse.clear();
        lvwFad.getItems().setAll(Controller.getFade());
    }


}
