package com.magazin.magazin;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Map;

public class PostgreSQLConnection {

    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    static final String USER = "postgres";
    static final String PASSWORD = "00Admin00";
    Connection connection = null;

    public PostgreSQLConnection(){

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");

        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASSWORD);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }

    }

    public ResultSet getSQLQuery(String nameTab){

        ResultSet resultSet = null;

        if (connection != null) {

            String select = "select * from public." + nameTab + " AS tt;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(select);
                resultSet = preparedStatement.executeQuery();
                } catch (SQLException e) {
                System.out.println("SQL Query Failed");
                e.printStackTrace();
            }

        }

        return resultSet;

    }

    public ArrayList<NameValueType> getSQLStructureTable(String nameTab){

        ResultSet resultSet = null;
        ArrayList<NameValueType> result = new ArrayList<NameValueType>();

        if (connection != null) {

            String select = "select * from public." + nameTab + " AS tt limit 0;";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(select);
                resultSet = preparedStatement.executeQuery();
                for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                    NameValueType nameValueType = new NameValueType();
                    nameValueType.setName(resultSet.getMetaData().getColumnName(i + 1));
                    nameValueType.setValue("");
                    nameValueType.setTypes(resultSet.getMetaData().getColumnType(i + 1));
                    result.add(nameValueType);
                }
            } catch (SQLException e) {
                System.out.println("SQL Query Failed");
                e.printStackTrace();
            }

        }

        return result;

    }

    public void insertSQLRecord(String nameTab, ArrayList<NameValueType> structureMap){

        String SQLText = "INSERT INTO public." + nameTab + " (";
        String SQLParametrs = " (";
        boolean first = true;
        for (NameValueType s : structureMap){
            if (first != true) {
                SQLText += ", ";
                SQLParametrs += ", ";
            }
            SQLText += s.getName();
            SQLParametrs += "?";
            first = false;
        }

        SQLParametrs += ");";
        SQLText += ") VALUES " + SQLParametrs;

        System.out.println(SQLText);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLText);
            int i = 0;
            for (NameValueType s : structureMap){
                preparedStatement.setObject(i + 1, s.getValue(), s.getTypes());
//                preparedStatement.setString(i + 1, structureMap.get(s));
                i++;
            }

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Query Failed");
            e.printStackTrace();
        }


    }
}
