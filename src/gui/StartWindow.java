package gui;

import application.model.Batch;
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
//        tabBatch.setOnSelectionChanged(event -> batchPane.updateControls());

        // ---------------------------------------------------------------------

//        CompanyPane companyPane = new CompanyPane();
//        tabCompanies.setContent(companyPane);
//        tabCompanies.setOnSelectionChanged(event -> companyPane.updateControls());

        Tab tabDestillat = new Tab("Destillat");
        tabPane.getTabs().add(tabDestillat);

        // ---------------------------------------------------------------------

        Tab tabWhisky = new Tab("Whisky");
        tabPane.getTabs().add(tabWhisky);

        // ---------------------------------------------------------------------

        Tab tabLager = new Tab("Lager");
        tabPane.getTabs().add(tabLager);

        LagerPane lagerPane = new LagerPane();
        tabLager.setContent(lagerPane);
//        tabLager.setOnSelectionChanged(event -> lagerPane.updateControls());

    }

}
