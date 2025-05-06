package gui;

import application.model.Whisky;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FærdiggørWhiskyWindow extends Stage {
    private Whisky whisky;

    public FærdiggørWhiskyWindow(String title, Whisky whisky) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);

        this.whisky = whisky;

        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);

        Scene scene = new Scene(pane, 300, 300);
        setScene(scene);
        setMinWidth(450);
        setMinHeight(400);
    }

    //-------------------------------------------------------

    public void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);


    }
}
