package gui;

import application.controller.Controller;
import application.model.Destillat;
import application.model.Fad;
import application.model.Lager;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

public class FærdiggørDestillatWindow extends Stage {
    private Destillat destillat;
    //-------------------------------------------------------
    private DatePicker påfyldDato;
    private ComboBox<Lager> cbbLager;
    private Label lblError, lblReol, lblHylde;
    private Button btnFindPlads;
    private TextField txfNavn, txfLiter, txfAlkoholProcent, txfReol, txfHylde;
    public FærdiggørDestillatWindow(String title, Destillat destillat) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        this.destillat = destillat;

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane, 300, 300);
        setScene(scene);
        setMinWidth(600);
        setMinHeight(400);

    }

    public void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblDestillat = new Label("Destillat");
        lblDestillat.setFont(Font.font("System", FontWeight.BOLD, 14));
        pane.add(lblDestillat, 0, 0);

        Label lblNavn = new Label("Destillat navn");
        pane.add(lblNavn, 0, 1);

        txfNavn = new TextField(destillat.getNavn());
        pane.add(txfNavn, 0, 2);

        Label lblLiter = new Label("Liter");
        pane.add(lblLiter, 0, 3);

        txfLiter = new TextField("" + destillat.getAntalLiter());
        pane.add(txfLiter, 0, 4);
        txfLiter.setEditable(false);

        Label lblAlkoholProcent = new Label("Alkoholprocent");
        pane.add(lblAlkoholProcent, 0, 5);

        txfAlkoholProcent = new TextField("0");
        pane.add(txfAlkoholProcent, 0, 6);
        txfAlkoholProcent.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    int value = Integer.parseInt(newValue);
                    if (value < 0 || value > 100) {
                        txfAlkoholProcent.setText(oldValue);
                    }
                } catch (NumberFormatException e) {
                    txfAlkoholProcent.setText(oldValue);
                }
            }
        });

        Label lblDato = new Label("Påfyldnings dato");
        pane.add(lblDato, 0, 7);

        påfyldDato = new DatePicker(LocalDate.now());

        pane.add(påfyldDato, 0, 8);
        påfyldDato = new DatePicker(LocalDate.now());
        pane.add(påfyldDato, 0, 8);

        Button btnLuk = new Button("Luk");
        btnLuk.setOnAction(event -> lukAction());
        pane.add(btnLuk, 0, 9);
        GridPane.setHalignment(btnLuk, HPos.LEFT);
        GridPane.setValignment(btnLuk, VPos.BOTTOM);

        //kolonne 1
        Label lblPlacering = new Label("Placering");
        lblPlacering.setFont(Font.font("System", FontWeight.BOLD, 14));
        pane.add(lblPlacering, 1, 0);

        Label lblLager = new Label("Lager");
        pane.add(lblLager, 1, 1);

        cbbLager = new ComboBox<>();
        cbbLager.getItems().addAll(Controller.getLagre());
        pane.add(cbbLager, 1, 2);
        cbbLager.setOnAction(event -> lagerAction());

        ChangeListener<Lager> listener = (ov, oldBatch, newBatch) -> this.selectionChangeLager();
        cbbLager.getSelectionModel().selectedItemProperty().addListener(listener);

        lblReol = new Label("Reol nummer: ");
        pane.add(lblReol, 1, 3);
        txfReol = new TextField("0");
        pane.add(txfReol, 1, 4);
        txfReol.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    int value = Integer.parseInt(newValue);
                    if (value < 0) {
                        txfReol.setText(oldValue);
                    }
                } catch (NumberFormatException e) {
                    txfReol.setText(oldValue);
                }
            }
        });
        lblHylde = new Label("Hylde nummer: ");
        pane.add(lblHylde, 1, 5);
        lblHylde.setMinWidth(250);

        txfHylde = new TextField("0");
        pane.add(txfHylde, 1, 6);

        Fad fad = destillat.getFad();
        if (fad.getLager() != null) {
            txfHylde.setText("" + fad.getHylde());
            txfReol.setText("" + fad.getReol());
        }

        btnFindPlads = new Button("Find plads");
        btnFindPlads.setOnAction(event -> findPladsAction());
        btnFindPlads.setDisable(true);
        pane.add(btnFindPlads, 1, 7);
        GridPane.setHalignment(btnFindPlads, HPos.RIGHT);

        Button btnFærdiggør = new Button("Færdiggør");
        btnFærdiggør.setOnAction(event -> færdiggørAction());
        pane.add(btnFærdiggør, 1, 9);
        GridPane.setHalignment(btnFærdiggør, HPos.RIGHT);
        GridPane.setValignment(btnFærdiggør, VPos.BOTTOM);

        lblError = new Label();
        pane.add(lblError, 0, 10);
        lblError.setStyle("-fx-text-fill: red");
        lblError.setMinWidth(250);

    }

    // -------------------------------------------------------------------------


    private void lagerAction() {
        Lager lager = cbbLager.getSelectionModel().getSelectedItem();
        if (lager != null) {
            btnFindPlads.setDisable(false);
        } else {
            btnFindPlads.setDisable(true);
        }
    }

    private void lukAction() {
        hide();
    }

    private void færdiggørAction() {
        String navn = txfNavn.getText();
        LocalDate påfyldDato = this.påfyldDato.getValue();
        double alkohol = Double.parseDouble(txfAlkoholProcent.getText());
        Lager lager = cbbLager.getSelectionModel().getSelectedItem();
        int maksReol = -1;
        int maksHylde = -1;
        if (lager != null) {
            maksReol = lager.getAntalReoler();
            maksHylde = lager.getAntalHylder();
        }
        int reol = Integer.parseInt(txfReol.getText());
        int hylde = Integer.parseInt(txfHylde.getText());

        if (navn.isBlank()) {
            lblError.setText("Skriv et navn for destillatet");
        } else if (alkohol <= 0) {
            lblError.setText("Alkoholprocent skal være større end 0");
        } else if (påfyldDato == null) {
            lblError.setText("Vælg en dato for påfyldning");
        } else if (lager == null) {
            lblError.setText("Vælg et lager");
        } else if (reol < 1 || reol > maksReol) {
            lblError.setText("Skriv et reol nummer");
        } else if (hylde < 1 || hylde > maksHylde) {
            lblError.setText("Skriv et hylde nummer");
        } else {
            if (!navn.equals(destillat.getNavn())) {
                destillat.setNavn(navn);
            }

            Controller.færdiggørDestillat(alkohol, påfyldDato, destillat);
            Controller.indsætVarePåLager(lager,reol,hylde,destillat.getFad());
            hide();
        }


    }

    private void findPladsAction() {
        int[] plads = cbbLager.getSelectionModel().getSelectedItem().getNæsteLedigPlads();
        txfReol.setText("" + plads[0]);
        txfHylde.setText("" + plads[1]);
    }

    private void selectionChangeLager() {
        Lager lager = cbbLager.getSelectionModel().getSelectedItem();
        if (lager == null) {
            lblHylde.setText("Hylde nummer: ");
            lblReol.setText("Reol nummer: ");
        } else {

            lblHylde.setText("Hylde nummer: 1-" + lager.getAntalHylder());
            lblReol.setText("Reol nummer: 1-" + lager.getAntalReoler());
        }
    }


}
