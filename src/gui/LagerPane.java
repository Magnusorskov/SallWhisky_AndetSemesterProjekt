package gui;

import application.model.Søgning;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class LagerPane extends GridPane {
    private Søgning søgning;

    public LagerPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Button btnOpretFad = new Button("Opret Fad");
        btnOpretFad.setMinWidth(100);
        btnOpretFad.setOnAction(event -> this.opretFadAction());
        this.add(btnOpretFad, 0, 0);

        Button btnOpretLager = new Button("Opret Lager");
        btnOpretLager.setMinWidth(100);
        btnOpretLager.setOnAction(event -> this.opretLagerAction());
        this.add(btnOpretLager, 0, 1);
    }

    // -------------------------------------------------------------------------

    private void opretFadAction() {

        OpretFadWindow dia = new OpretFadWindow("Opret fad");
        dia.showAndWait();

        // Wait for the modal dialog to close
        updateControls();
    }

    private void opretLagerAction() {

        OpretLagerWindow dia = new OpretLagerWindow("Opret Lager");
        dia.showAndWait();

        updateControls();

    }

    public void updateControls() {

    }



}
