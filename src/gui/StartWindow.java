package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StartWindow extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("SALL Whisky");
        BorderPane pane = new BorderPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void initContent(BorderPane pane) {
        TabPane tabPane = new TabPane();
        this.initTabPane(tabPane);
        pane.setCenter(tabPane);
    }

    private void initTabPane(TabPane tabPane) {
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab tabBatch = new Tab("Batch");
        tabPane.getTabs().add(tabBatch);

        BatchPane batchPane = new BatchPane();
        tabBatch.setContent(batchPane);
        tabBatch.setOnSelectionChanged(event -> batchPane.updateControls());

        // ---------------------------------------------------------------------

        Tab tabPåfyldning = new Tab("Påfyldning");
        tabPane.getTabs().add(tabPåfyldning);

        PåfyldningsPane påfyldningsPane = new PåfyldningsPane();
        tabPåfyldning.setContent(påfyldningsPane);
        tabPåfyldning.setOnSelectionChanged(event -> påfyldningsPane.updateControls());

        // ---------------------------------------------------------------------

        Tab tabOmhældning = new Tab("Omhældning");
        tabPane.getTabs().add(tabOmhældning);

        OmhældningsPane omhældningsPane = new OmhældningsPane();
        tabOmhældning.setContent(omhældningsPane);
        tabOmhældning.setOnSelectionChanged(event -> omhældningsPane.updateControls());

        // ---------------------------------------------------------------------

        Tab tabTapning = new Tab("Tapning");
        tabPane.getTabs().add(tabTapning);

        TapningsPane tapningsPane = new TapningsPane();
        tabTapning.setContent(tapningsPane);
        tabTapning.setOnSelectionChanged(event -> tapningsPane.updateControls());



        // ---------------------------------------------------------------------

        Tab tabLager = new Tab("Lager");
        tabPane.getTabs().add(tabLager);

        LagerPane lagerPane = new LagerPane();
        tabLager.setContent(lagerPane);
        tabLager.setOnSelectionChanged(event -> lagerPane.updateControls());

        // ---------------------------------------------------------------------

        Tab tabOversigt = new Tab("Oversigter");
        tabPane.getTabs().add(tabOversigt);

        OversigtsPane oversigtsPane = new OversigtsPane();
        tabOversigt.setContent(oversigtsPane);
        tabOversigt.setOnSelectionChanged(event -> oversigtsPane.updateControls());

    }

}
