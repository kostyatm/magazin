package com.magazin.magazin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.List;

public class CreateTabs {

    public void addTab(TabPane tabPane, boolean setClosable, String nameTab, String zagolowokTab){
        Tab tab = new Tab(nameTab);
        tab.setText(zagolowokTab);
        if (nameTab == "doc_PostuplenieTowarow")
        {
            Button buttonAdd = new Button();
            buttonAdd.setText("Add");
            buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });

            List listColumn = new ArrayList();

            listColumn.add("element 1");
            listColumn.add("element 2");
            listColumn.add("element 3");

            TableView tableView = new CreateTableView(nameTab, listColumn).getTable();

            VBox root = new VBox(5);
            root.setSpacing(5);
            root.setFillWidth(true);

            StackPane rootStack = new StackPane();
            rootStack.getChildren().add(tableView);

            VBox.setVgrow(rootStack, Priority.ALWAYS);

            root.getChildren().add(buttonAdd);
            root.getChildren().add(rootStack);

            tab.setContent(root);
        }
        else{
            tab.setContent(new Label("This is a label inside the tab " + zagolowokTab));
        }
        tab.setClosable(setClosable);
        tabPane.getTabs().add(tab);
    }

}

