package gui;

import application.controller.Controller;
import application.model.Lager;
import application.model.Lagervare;
import javafx.beans.value.ChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VælgPlaceringWindow extends Stage {
    private Lagervare lagervare;
    private ComboBox<Lager> cbbLager;
    private Label lblError, lblReol, lblHylde;
    private Button btnFindPlads;
    private TextField txfReol, txfHylde;

    public VælgPlaceringWindow(String title, Lagervare lagervare) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        this.lagervare = lagervare;

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane, 300, 300);
        setScene(scene);
        setMinWidth(450);
        setMinHeight(300);
    }

    //-------------------------------------------------------

    public void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblLager = new Label("Lager");
        pane.add(lblLager, 0, 1);

        cbbLager = new ComboBox<>();
        if (lagervare.getLager() != null) {
            cbbLager.getSelectionModel().select(lagervare.getLager());
        }
        cbbLager.getItems().addAll(Controller.getLagre());
        pane.add(cbbLager, 0, 2);
        cbbLager.setOnAction(event -> lagerAction());

        ChangeListener<Lager> listener = (ov, oldBatch, newBatch) -> this.selectionChangeLager();
        cbbLager.getSelectionModel().selectedItemProperty().addListener(listener);

        lblReol = new Label("Reol nummer: ");
        pane.add(lblReol, 0, 3);
        //TODO fix så txfReol og txfHylde får current placering hvis der er en
        txfReol = new TextField("0");
        pane.add(txfReol, 0, 4);
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
        pane.add(lblHylde, 0, 5);

        txfHylde = new TextField("0");
        pane.add(txfHylde, 0, 6);

        int hylde = lagervare.getHylde();
        int reol = lagervare.getReol();
        if (hylde != -1) {
            txfHylde.setText("" + hylde);
            txfReol.setText("" + reol);
        }

        btnFindPlads = new Button("Find plads");
        btnFindPlads.setOnAction(event -> findPladsAction());
        btnFindPlads.setDisable(true);
        pane.add(btnFindPlads, 0, 7);
        GridPane.setHalignment(btnFindPlads, HPos.RIGHT);

        Button btnFærdiggør = new Button("Færdiggør");
        btnFærdiggør.setOnAction(event -> færdiggørAction());
        pane.add(btnFærdiggør, 0, 9);
        GridPane.setHalignment(btnFærdiggør, HPos.RIGHT);
        GridPane.setValignment(btnFærdiggør, VPos.BOTTOM);

        lblError = new Label();
        pane.add(lblError, 0, 10);
        lblError.setStyle("-fx-text-fill: red");
        lblError.setMinWidth(250);

    }

    private void færdiggørAction() {
        Lager lager = cbbLager.getSelectionModel().getSelectedItem();
        int maksReol = lager.getAntalReoler();
        int maksHylde = lager.getAntalHylder();
        int reol = Integer.parseInt(txfReol.getText());
        int hylde = Integer.parseInt(txfHylde.getText());


        if (lager == null) {
            lblError.setText("Vælg et lager");
        } else if (reol < 1 || reol > maksReol) {
            lblError.setText("Skriv et reol nummer");
        } else if (hylde < 1 || hylde > maksHylde) {
            lblError.setText("Skriv et hylde nummer");
        } else {
            Controller.indsætVarePåLager(lager, reol, hylde, lagervare);
            hide();
        }
    }

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
