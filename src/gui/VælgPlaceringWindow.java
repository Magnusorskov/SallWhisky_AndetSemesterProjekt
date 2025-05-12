package gui;

import application.model.Fad;
import application.model.Lagervare;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class VælgPlaceringWindow extends Stage {
    private Lagervare lagervare;

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

    }
}
