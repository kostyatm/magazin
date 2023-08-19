package com.magazin.magazin;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class CreateTabs {

    public void addTab(TabPane tabPane, boolean setClosable, String nameTab){
        Tab tab = new Tab("Tab " + nameTab);
        tab.setText(nameTab);
        tab.setContent(new Label("This is a label inside the tab " + nameTab));
        tab.setClosable(setClosable);
        tabPane.getTabs().add(tab);
    }

}

