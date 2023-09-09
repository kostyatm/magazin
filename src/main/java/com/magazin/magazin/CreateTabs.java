package com.magazin.magazin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.geometry.Insets;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateTabs {

    public void addTab(TabPane tabPane,
                       boolean setClosable,
                       String nameTab,
                       String zagolowokTab,
                       PostgreSQLConnection conDB,
                       ArrayList<NameValueType> structure,
                       String id){
        Tab tab = new Tab(nameTab);
        tab.setText(zagolowokTab);
        if (nameTab != "HomePage" && structure.isEmpty())
        {
            CreateTableView createTableView = new CreateTableView(nameTab, conDB);
            TableView tableView = createTableView.getTable();

            tab.setOnSelectionChanged(e -> {
                createTableView.updateView(conDB.getSQLQuery(nameTab));
            });

            tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                if (e.getClickCount() == 2 && tableView.getSelectionModel().getSelectedItem() != null) {
                    TablePosition pos = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
                    int row = pos.getRow();

                    TableColumn col = (TableColumn) tableView.getColumns().get(0);
                    String str = col.getCellObservableValue(row).getValue().toString();

                    System.out.println("Selected Value " + str);

                    ArrayList<NameValueType> StructureTable = conDB.getSQLStructureTable(nameTab, str);

                    CreateTabs newTab = new CreateTabs();

                    newTab.addTab(tabPane, true, nameTab, zagolowokTab, conDB, StructureTable, str);

                }
            });

            Button buttonAdd = new Button();
            buttonAdd.setText("Add");
            buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    ArrayList<NameValueType> StructureTable = conDB.getSQLStructureTable(nameTab, "");

                    CreateTabs newTab = new CreateTabs();

                    newTab.addTab(tabPane, true, nameTab, zagolowokTab, conDB, StructureTable, "");

                }
            });

            VBox root = new VBox(5);
            root.setSpacing(5);
            root.setFillWidth(true);

            HBox topHBox = new HBox(5);
            topHBox.setSpacing(5);
            topHBox.setFillHeight(true);

            topHBox.getChildren().add(buttonAdd);

            VBox.setMargin(topHBox, new Insets(5, 0, 0, 5));

            StackPane rootStack = new StackPane();
            rootStack.getChildren().add(tableView);

            VBox.setVgrow(rootStack, Priority.ALWAYS);

            root.getChildren().add(topHBox);

            VBox.setMargin(rootStack, new Insets(5, 5, 5, 5));

            root.getChildren().add(rootStack);

            tab.setContent(root);
        }
        else if (nameTab != "HomePage" && structure.isEmpty() == false) {

            String cssLayout = "-fx-border-color: red;\n" +
                    "-fx-border-insets: 5;\n" +
                    "-fx-border-width: 3;\n" +
                    "-fx-border-style: dashed;\n";

            ArrayList<AdvancedTextField> arrayTextField = new ArrayList<AdvancedTextField>();

            VBox root = new VBox(5);
            root.setSpacing(5);
            root.setFillWidth(true);

            HBox topHBox = new HBox(5);
            topHBox.setSpacing(5);
            topHBox.setFillHeight(true);

            //////////////////////////////////////////////////////////
//            topHBox.setStyle(cssLayout);
//            root.setStyle(cssLayout);

            Button buttonWriteClose = new Button();
            buttonWriteClose.setText("Записать и закрыть");
            buttonWriteClose.setDefaultButton(true);
            buttonWriteClose.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    int i = 0;
                    for (NameValueType nameValueType : structure){
                        nameValueType.setValue(arrayTextField.get(i).getField().getText());
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
                        nameValueType.setValue(arrayTextField.get(i).getField().getText());
                        i++;
                    }

                    conDB.insertSQLRecord(nameTab, structure);

                }
            });

            Button buttonClose = new Button();
            buttonClose.setText("Закрыть");
            buttonClose.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex());

                }
            });

            topHBox.getChildren().add(buttonWriteClose);
            topHBox.getChildren().add(buttonWrite);
            topHBox.getChildren().add(buttonClose);

            VBox.setMargin(topHBox, new Insets(5, 0, 0, 5));

            root.getChildren().add(topHBox);

            for(NameValueType s : structure) {

                AdvancedTextField advTextField = new AdvancedTextField(s.getName(), id, s.getValue());

                arrayTextField.add(advTextField);

            }

            int maxLengthLable = getMaxLengthLable(arrayTextField);
            int maxLengthValue = getMaxLengthValue(arrayTextField);

            for (AdvancedTextField advTextField : arrayTextField) {

                Label lableField = new Label();
                lableField.setText(advTextField.getLableText());
                lableField.setMinWidth(50);
                lableField.setPrefWidth(maxLengthLable);

                TextField textField = advTextField.getField();
                textField.setPrefWidth(maxLengthValue);

                HBox fieldHBox = new HBox(5);
                fieldHBox.setSpacing(5);
                fieldHBox.setFillHeight(true);

                fieldHBox.getChildren().add(lableField);
                fieldHBox.getChildren().add(textField);

                //////////////////////////////////////////////////////////
//                fieldHBox.setStyle(cssLayout);

                VBox.setMargin(fieldHBox, new Insets(5, 0, 0, 5));

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

    public int getMaxLengthLable(ArrayList<AdvancedTextField> arrayList){
        int returnSize = 50;

        for (AdvancedTextField advTextField : arrayList) {

            if (advTextField.getLengthLable() > returnSize) {
                returnSize = advTextField.getLengthLable();
            }

        }

        return returnSize;
    }

    public int getMaxLengthValue(ArrayList<AdvancedTextField> arrayList){
        int returnSize = 250;

        for (AdvancedTextField advTextField : arrayList) {

            if (advTextField.getLengthValue() > returnSize) {
                returnSize = advTextField.getLengthValue();
            }

        }

        return returnSize;
    }

}

