package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class LagerPane extends GridPane {

    public LagerPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        Button btnOpretFad = new Button("Opret Fad");
        btnOpretFad.setOnAction(event -> this.opretAction());
        this.add(btnOpretFad,0,0);
    }

    // -------------------------------------------------------------------------

    private void opretAction() {

        OpretFadWindow dia = new OpretFadWindow ("Opret fad");
        dia.showAndWait();

        // Wait for the modal dialog to close
//        updateControls();
    }

    private void updateControls(){

    }

}
