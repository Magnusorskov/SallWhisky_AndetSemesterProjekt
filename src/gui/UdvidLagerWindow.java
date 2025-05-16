package gui;

import application.controller.Controller;
import application.model.Lager;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UdvidLagerWindow extends Stage {

    private ComboBox<Lager> cbbLager;
    private TextField txfAntalReoler, txfAntalHylder;
    private Label lblAntalReoler, lblAntalHylder, lblError;
    private Button btnUdvid, btnLuk;

    public UdvidLagerWindow(String title) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane, 300, 200);
        setScene(scene);
        setMinWidth(350);
        setMinHeight(300);
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        Label lblLager = new Label("Lager:");
        pane.add(lblLager, 0, 0);

        cbbLager = new ComboBox<>();
        cbbLager.getItems().addAll(Controller.getLagre());
        pane.add(cbbLager, 0, 1);
        cbbLager.setOnAction(event -> handleCbbLagerAction());

        lblAntalReoler = new Label("Antal Reoler");
        pane.add(lblAntalReoler, 0, 2);

        txfAntalReoler = new TextField();
        pane.add(txfAntalReoler, 0, 3);

        lblAntalHylder = new Label("Antal Hylder");
        pane.add(lblAntalHylder, 0, 4);

        txfAntalHylder = new TextField();
        pane.add(txfAntalHylder, 0, 5);

        btnLuk = new Button("Luk");
        btnLuk.setOnAction(event -> lukAction());
        pane.add(btnLuk, 0, 6);
        GridPane.setHalignment(btnLuk, HPos.LEFT);

        btnUdvid = new Button("Udvid Lager");
        btnUdvid.setOnAction(event -> udvidLagerAction());
        pane.add(btnUdvid, 0, 6);
        GridPane.setHalignment(btnUdvid, HPos.RIGHT);
        GridPane.setValignment(btnUdvid, VPos.BOTTOM);

        lblError = new Label();
        pane.add(lblError, 0, 7);
        lblError.setStyle("-fx-text-fill: red;");
        lblError.setMinWidth(250);
    }

    private void handleCbbLagerAction() {
        Lager valgtLager = cbbLager.getValue();
        if (valgtLager != null) {
            lblAntalReoler.setText("Antal Reoler (Nuv. antal: " + valgtLager.getAntalReoler() + ")");
            lblAntalHylder.setText("Antal Hylder (Nuv. antal: " + valgtLager.getAntalHylder() + ")");
        } else {
            lblAntalReoler.setText("Antal Reoler: ");
            lblAntalHylder.setText("Antal Hylder: ");
        }
    }

    private void udvidLagerAction() {
        Lager lager = cbbLager.getSelectionModel().getSelectedItem();
        String reolerText = txfAntalReoler.getText();
        String hylderText = txfAntalHylder.getText();

        if (lager == null) {
            lblError.setText("Vælg et lager.");
        } else if (reolerText.isEmpty() || hylderText.isEmpty()) {
            lblError.setText("Indtast antal reoler og hylder.");
        } else {
            try {
                int antalReoler = Integer.parseInt(reolerText);
                int antalHylder = Integer.parseInt(hylderText);

                if (antalReoler <= 0 || antalHylder <= 0) {
                    lblError.setText("Antal reoler og hylder skal være positive tal.");
                } else if (antalReoler < lager.getAntalReoler() || antalHylder < lager.getAntalHylder()) {
                    lblError.setText("Antal reoler og hylder skal være større end de var før.");
                } else {

                    int ledigePladserFør = Controller.beregnAntalLedigePladserPåLager(lager); // Antal ledige pladser før udvidelse
                    Controller.udvidLager(lager, antalReoler, antalHylder);
                    int ledigePladserEfter = Controller.beregnAntalLedigePladserPåLager(lager); // Antal ledige pladser efter udvidelse

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initOwner(this);
                    alert.setTitle("Lager udvidet");
                    alert.setHeaderText(null);
                    alert.setContentText("Lageret er udvidet.\n\nLedige pladser før: " + ledigePladserFør + "\nLedige pladser efter: " + ledigePladserEfter);
                    alert.showAndWait();
                    this.close();
                }
            } catch (NumberFormatException e) {
                lblError.setText("Ugyldige tal. Indtast venligst hele tal.");
            }
        }
    }

    private void lukAction() {
        this.close();
    }
}
