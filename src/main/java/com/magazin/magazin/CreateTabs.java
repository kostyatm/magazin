package com.magazin.magazin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

public class CreateTabs {

    public void addTab(TabPane tabPane, boolean setClosable, String nameTab, String zagolowokTab, PostgreSQLConnection conDB){
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

            TableView tableView = new CreateTableView(nameTab, conDB).getTable();

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
        tabPane.getSelectionModel().select(tab);
    }

}

