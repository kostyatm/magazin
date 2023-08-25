package com.magazin.magazin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class CreateMenuBar {

    MenuBar menuBar = new MenuBar();

    public CreateMenuBar() {
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        fileMenu.getItems().addAll(exitItem);
        menuBar.getMenus().addAll(fileMenu);
    }

}
