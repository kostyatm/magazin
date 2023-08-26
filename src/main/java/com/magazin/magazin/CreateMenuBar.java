package com.magazin.magazin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;

public class CreateMenuBar {

    MenuBar menuBar = new MenuBar();

    public CreateMenuBar(TabPane tabPane, PostgreSQLConnection conDB) {
        Menu fileMenu = new Menu("Файл");
        MenuItem exitItem = new MenuItem("Выход");
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        fileMenu.getItems().addAll(exitItem);

        Menu documentsMenu = new Menu("Документы");

        Menu ZapasyZakupkiItem = new Menu("Запасы и закупки");
        MenuItem PostuplenieItem = new MenuItem("Поступление товаров");
        PostuplenieItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CreateTabs createTabs = new CreateTabs();

                createTabs.addTab(tabPane, true, "doc_PostuplenieTowarow","Поступление товаров", conDB);
            }
        });
        ZapasyZakupkiItem.getItems().addAll(PostuplenieItem);

        Menu ProdazhiItem = new Menu("Продажи");
        MenuItem RealizaciyaItem = new MenuItem("Реализация товаров");
        RealizaciyaItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CreateTabs createTabs = new CreateTabs();

                createTabs.addTab(tabPane, true, "doc_RealizaciyaTowarow","Реализация товаров", conDB);
            }
        });

        ProdazhiItem.getItems().addAll(RealizaciyaItem);

        documentsMenu.getItems().addAll(ZapasyZakupkiItem, ProdazhiItem);

        menuBar.getMenus().addAll(fileMenu, documentsMenu);

        CreateTabs createTabs = new CreateTabs();

        createTabs.addTab(tabPane, false, "HomePage", "Начальная страница", conDB);

    }

}
