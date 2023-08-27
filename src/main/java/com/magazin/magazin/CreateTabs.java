package com.magazin.magazin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateTabs {

    public void addTab(TabPane tabPane,
                       boolean setClosable,
                       String nameTab,
                       String zagolowokTab,
                       PostgreSQLConnection conDB,
                       ArrayList<NameValueType> structure){
        Tab tab = new Tab(nameTab);
        tab.setText(zagolowokTab);
        if (nameTab != "HomePage" && structure.isEmpty())
        {
            CreateTableView createTableView = new CreateTableView(nameTab, conDB);
            TableView tableView = createTableView.getTable();

            tab.setOnSelectionChanged(e -> {
                createTableView.updateView(conDB.getSQLQuery(nameTab));
            });

            Button buttonAdd = new Button();
            buttonAdd.setText("Add");
            buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    ArrayList<NameValueType> StructureTable = conDB.getSQLStructureTable(nameTab);

                    CreateTabs newTab = new CreateTabs();

                    newTab.addTab(tabPane, true, nameTab, zagolowokTab, conDB, StructureTable);

                }
            });

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
        else if (nameTab != "HomePage" && structure.isEmpty() == false) {

            ArrayList<TextField> arrayTextField = new ArrayList<TextField>();

            VBox root = new VBox(5);
            root.setSpacing(5);
            root.setFillWidth(true);

            HBox topHBox = new HBox(5);
            topHBox.setSpacing(5);
            topHBox.setFillHeight(true);

            Button buttonWriteClose = new Button();
            buttonWriteClose.setText("Записать и закрыть");
            buttonWriteClose.setDefaultButton(true);
            buttonWriteClose.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    int i = 0;
                    for (NameValueType nameValueType : structure){
                        nameValueType.setValue(arrayTextField.get(i).getText());
                        i++;
                    }

                    conDB.insertSQLRecord(nameTab, structure);

                    tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex());

                }
            });

            Button buttonWrite = new Button();
            buttonWrite.setText("Записать");
            buttonWrite.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    int i = 0;
                    for (NameValueType nameValueType : structure){
                        nameValueType.setValue(arrayTextField.get(i).getText());
                        i++;
                    }

                    conDB.insertSQLRecord(nameTab, structure);

                }
            });

            topHBox.getChildren().add(buttonWriteClose);
            topHBox.getChildren().add(buttonWrite);

            root.getChildren().add(topHBox);

            for(NameValueType s : structure) {
                Label lableField = new Label();
                lableField.setText(s.getName());
                TextField textField = new TextField();

                arrayTextField.add(textField);

                HBox fieldHBox = new HBox(5);
                fieldHBox.setSpacing(5);
                fieldHBox.setFillHeight(true);

                fieldHBox.getChildren().add(lableField);
                fieldHBox.getChildren().add(textField);

                root.getChildren().add(fieldHBox);
            }

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

