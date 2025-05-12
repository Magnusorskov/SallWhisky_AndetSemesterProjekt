package gui;

import application.controller.Controller;
import application.model.Fad;
import application.model.Fadtype;
import application.model.Land;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class LagerPane extends GridPane {

    private ComboBox<Object> cbbLagre, cbbFadtype, cbbFills, cbbLand;
    private TextField txfMåneder, txfStørrelse;
    private RadioButton rbTom, rbFyldt, rbAlle;
    private ListView<Fad> lvwFad;
    private TextArea txaBeskrivelse;


    public LagerPane() {
        this.setPadding(new Insets(20));
        this.setHgap(10);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        int width = 180;



        Label lblLagre = new Label("Lagre");
        this.add(lblLagre,0,0);

        cbbLagre = new ComboBox<>();
        cbbLagre.setMinWidth(width);
        cbbLagre.getItems().add(null);
        cbbLagre.getItems().add("På lager");
        cbbLagre.getItems().add("Ikke på lager");
        cbbLagre.getItems().addAll(Controller.getLagre());
        this.add(cbbLagre, 0, 1);

        Label lblFadtype = new Label("Fadtype");
        this.add(lblFadtype,0,2);

        cbbFadtype = new ComboBox<>();
        cbbFadtype.setMinWidth(width);
        cbbFadtype.getItems().add(null);
        cbbFadtype.getItems().addAll(Fadtype.NEW,Fadtype.EXSHERRY,Fadtype.EXOLOROSO,Fadtype.EXBOURBON);
        this.add(cbbFadtype, 0, 3);

        Label lblFils = new Label("Fils");
        this.add(lblFils,0,4);

        cbbFills = new ComboBox<>();
        cbbFills.setMinWidth(width);
        cbbFills.getItems().add(null);
        cbbFills.getItems().addAll(0,1,2,3);
        this.add(cbbFills, 0, 5);

        Label lblLand = new Label("Land");
        this.add(lblLand,0,6);

        cbbLand = new ComboBox<>();
        cbbLand.setMinWidth(width);
        cbbLand.getItems().add(null);
        cbbLand.getItems().addAll(Land.SPANIEN,Land.PORTUGAL,Land.USA);
        this.add(cbbLand, 0, 7);

        Label lblMåneder = new Label("Alder i måneder");
        this.add(lblMåneder,0,8);

        txfMåneder = new TextField();
        this.add(txfMåneder,0,9);

        Label lblStørrelse = new Label("Størrelse");
        this.add(lblStørrelse,0,10);

        txfStørrelse = new TextField();
        this.add(txfStørrelse,0,11);

        ToggleGroup group = new ToggleGroup();
        HBox hBox = new HBox();
        hBox.setSpacing(7);
        this.add(hBox,0,12);

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
        GridPane.setHalignment(btnSøg, HPos.LEFT);
        this.add(btnSøg, 0, 13);

        //kolonne 1
        Label lblFade = new Label("Fade");
        this.add(lblFade,1,0);

        lvwFad = new ListView<>();
        this.add(lvwFad, 1, 1,1,13);
        lvwFad.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ChangeListener<Fad> fadListener = (ov, oldFad, newFad) -> this.selectionChangeFad();
        lvwFad.getSelectionModel().selectedItemProperty().addListener(fadListener);


        HBox hBoxKnapper = new HBox();
        hBoxKnapper.setSpacing(150);
        this.add(hBoxKnapper,1,14);

        Button btnFjern = new Button("Fjern");
        btnFjern.setOnAction(event -> this.fjernAction());
        hBoxKnapper.getChildren().add(btnFjern);

        Button btnFindPlads = new Button("Find plads");
        btnFindPlads.setOnAction(event -> this.findPladsAction());
        hBoxKnapper.getChildren().add(btnFindPlads);

        //kolonne 2
        Label lblBeskrivelse = new Label("Beskrivelse");
        this.add(lblBeskrivelse,2,0);

        txaBeskrivelse = new TextArea();
        txaBeskrivelse.setMaxWidth(250);
        txaBeskrivelse.setMinHeight(200);
        this.add(txaBeskrivelse, 2,1,1,6);

        Button btnÆndrePlacering = new Button("Ændre placering");
        btnÆndrePlacering.setOnAction(event -> this.ændreAction());
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
    }



    // -------------------------------------------------------------------------

    private void selectionChangeFad() {
        Fad fad = lvwFad.getSelectionModel().getSelectedItem();
        if (fad != null){
            txaBeskrivelse.setText(Controller.getFadBeskrivelse(fad));
        }
    }

    private void søgAction() {

    }

    private void fjernAction() {
        List<Fad> fade = new ArrayList<>(lvwFad.getItems());
        for (Fad f : fade){

        }
    }

    private void findPladsAction() {
        List<Fad> fade = new ArrayList<>(lvwFad.getItems());
        for (Fad f : fade){

        }
    }

    private void ændreAction(){
        Fad fad = lvwFad.getSelectionModel().getSelectedItem();

        ÆndrePlaceringWindow dia = new ÆndrePlaceringWindow("Ændre placering", fad);
        dia.showAndWait();

        updateControls();
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
