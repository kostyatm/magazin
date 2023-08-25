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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane rootPane = new BorderPane();

        try {
            // Создается построитель документа
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            // Создается дерево DOM документа из файла
            Document document = documentBuilder.parse("configuration.xml");

            // Получаем корневой элемент
            Node root = document.getDocumentElement();

            NodeList rootNodes = root.getChildNodes();
            parseXML(rootNodes, true);

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }

        TabPane tabPane = new TabPane();

        CreateTabs createTabs = new CreateTabs();

        createTabs.addTab(tabPane, false, "Home page");

        Button addTabBtn = new Button("New tab");
        addTabBtn.setOnAction(e->{
            createTabs.addTab(tabPane, true, "Documents");
            createTabs.addTab(tabPane, true, "Counter");
        });


        rootPane.setTop(addTabBtn);
        rootPane.setCenter(tabPane);

        Scene scene = new Scene(rootPane, 500, 350);
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TabPane in JavaFX");
        primaryStage.show();
    }

    public void parseXML(NodeList nodes, boolean thisRoot) {

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            NodeList childNodes = node.getChildNodes();
            int lengthChild = childNodes.getLength();

            if (node.getNodeType() == 1) {
                String nameNode = node.getAttributes().getNamedItem("name").getNodeValue();
                String textNode = node.getAttributes().getNamedItem("text").getNodeValue();
                System.out.println(nameNode);
                System.out.println(textNode);
            }

            if (lengthChild != 0) {
                parseXML(childNodes, false);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
