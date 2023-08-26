package com.magazin.magazin;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.TablePosition;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import javafx.util.Callback;

import javafx.beans.property.SimpleStringProperty;

public class CreateTableView {

    private TableView table = new TableView();;
    private ObservableList<ObservableList> data;

    public CreateTableView(String nameTab, PostgreSQLConnection conDB) {

        ResultSet resultSet = conDB.getSQLQuery(nameTab);
        if (resultSet != null){

            try {
                data = FXCollections.observableArrayList();
                for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                    //We are using non property style for making dynamic table
                    final int j = i;
                    TableColumn col = new TableColumn(resultSet.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                    });

                    table.getColumns().addAll(col);
                }

                while(resultSet.next()){
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for(int i=1 ; i<=resultSet.getMetaData().getColumnCount(); i++){
                        //Iterate Column
                        row.add(resultSet.getString(i));
                    }
                    data.add(row);
                }

                table.setItems(this.data);

            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Error on Building Data");
            }

        }

    }

    public TableView getTable() {

        table.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (e.getClickCount() == 2) {
                TablePosition pos = (TablePosition) table.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();

                TableColumn col = (TableColumn) table.getColumns().get(0);
                String str = col.getCellObservableValue(row).getValue().toString();

                System.out.println("Selected Value " + str);
            }
        });

        /*
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if(table.getSelectionModel().getSelectedItem() != null)
                {*/
                    /*
                    TableViewSelectionModel selectionModel = table.getSelectionModel();
                    ObservableList selectedCells = selectionModel.getSelectedCells();
                    TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                    Object val = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                    System.out.println("Selected Value " + val);
                     */
 /*
                    TablePosition pos = (TablePosition) table.getSelectionModel().getSelectedCells().get(0);
                    int row = pos.getRow();

                    TableColumn col = (TableColumn) table.getColumns().get(0);
                    String str = col.getCellObservableValue(row).getValue().toString();

                    System.out.println("Selected Value " + str);
                }
            }
        });*/

        return table;
    }

}
