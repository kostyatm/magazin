package com.magazin.magazin;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class CreateTableView {

    TableView<String> table = new TableView<String>();

    public CreateTableView(String nameTab, List listColumn) {

        table.setPrefWidth(250);
        table.setPrefHeight(200);

        Iterator iterator = listColumn.iterator();

        while(iterator.hasNext()){

            String stringList = (String)iterator.next();
            TableColumn listNameCol = new TableColumn(stringList);
            table.getColumns().add(listNameCol);

        }

    }

    public TableView getTable() {
        return table;
    }

}
