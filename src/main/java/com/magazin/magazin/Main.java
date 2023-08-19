package com.magazin.magazin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.geometry.Insets;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        TabPane tabPane = new TabPane();

        CreateTabs createTabs = new CreateTabs();

        createTabs.addTab(tabPane, false, "Home page");

        Button addTabBtn = new Button("New tab");
        AtomicInteger count = new AtomicInteger(1);
        addTabBtn.setOnAction(e->{
            createTabs.addTab(tabPane, true, "Documents");
            createTabs.addTab(tabPane, true, "Counter");
        });

        BorderPane root = new BorderPane();
        root.setTop(addTabBtn);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 500, 350);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TabPane in JavaFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
