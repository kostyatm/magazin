package com.magazin.magazin;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.geometry.Insets;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane rootPane = new BorderPane();

        TabPane tabPane = new TabPane();

        MenuBar menuBar = new CreateMenuBar(tabPane).menuBar;

//        CreateTabs createTabs = new CreateTabs();
//
//        createTabs.addTab(tabPane, false, "Home page");

//        Button addTabBtn = new Button("New tab");
//        addTabBtn.setOnAction(e->{
//            createTabs.addTab(tabPane, true, "Documents");
//            createTabs.addTab(tabPane, true, "Counter");
//        });

        rootPane.setTop(menuBar);
//        rootPane.setTop(addTabBtn);
        rootPane.setCenter(tabPane);

        Scene scene = new Scene(rootPane, 500, 350);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TabPane in JavaFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
