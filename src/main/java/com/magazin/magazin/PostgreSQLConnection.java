package com.magazin.magazin;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import java.util.ArrayList;
import java.util.UUID;

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

            String select = "select * from public." + nameTab + " AS tt ORDER BY tt._id;";
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

    public ArrayList<NameValueType> getSQLStructureTable(String nameTab, String id){

        ResultSet resultSet = null;
        ArrayList<NameValueType> result = new ArrayList<NameValueType>();

        if (connection != null) {

            String where = "";
            if (id.isEmpty() == false){
                where = "WHERE tt._id = '" + id + "'";
            }
            else{
                where = "limit 0";
            }

            String select = "select * from public." + nameTab + " AS tt " + where + ";";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(select,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                resultSet = preparedStatement.executeQuery();
                int count = resultSetCount(resultSet);
//                if (count != 0) {
//                    resultSet.first();
//                    resultSet.next();
//                }
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    NameValueType nameValueType = new NameValueType();
                    nameValueType.setName(resultSet.getMetaData().getColumnName(i));
                    if (count == 0){
                        nameValueType.setValue("");
                    }
                    else {
                        nameValueType.setValue(resultSet.getString(i));
                    }
                    nameValueType.setTypes(resultSet.getMetaData().getColumnType(i));
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
                if (s.getName().equalsIgnoreCase("_id") == true){
                    preparedStatement.setObject(i + 1, UUID.randomUUID().toString(), s.getTypes());
                }
                else {
                    preparedStatement.setObject(i + 1, s.getValue(), s.getTypes());
                }
//                preparedStatement.setString(i + 1, structureMap.get(s));
                i++;
            }

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Query Failed");
            e.printStackTrace();
        }


    }

    public void updateSQLRecord(String nameTab, ArrayList<NameValueType> structureMap, String id){

        String SQLText = "UPDATE public." + nameTab + " SET ";
        String SQLParametrs = "";
        boolean first = true;
        for (NameValueType s : structureMap){
            if (first != true) {
                SQLText += ", ";
            }
            SQLText += s.getName() + " = ?";
            first = false;
        }

        SQLText += " WHERE _id = ?;";

        System.out.println(SQLText);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLText);
            int i = 1;
            int idPosition = 0;
            String nameId = "_id";
            for (NameValueType s : structureMap){
                if (s.getName().equalsIgnoreCase("_id") == true){ idPosition = i - 1; }
                preparedStatement.setObject(i, s.getValue(), s.getTypes());
                i++;
            }

            preparedStatement.setObject(i,
                    structureMap.get(idPosition).getValue(),
                    structureMap.get(idPosition).getTypes());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Query Failed");
            e.printStackTrace();
        }
    }

    public void deleteSQLRecord(String nameTab, String id){

        String SQLText = "DELETE FROM public." + nameTab + " WHERE _id = ?;";

        System.out.println(SQLText);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLText);

            preparedStatement.setObject(1,
                    id,
                    Types.OTHER);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Query Failed");
            e.printStackTrace();
        }
    }

    private int resultSetCount(ResultSet resultSet) throws SQLException{
        try{
            int i = 0;
            resultSet.last();
            i = resultSet.getRow();
            resultSet.first();
            return i;
        } catch (Exception e){
            return 0;
        }
//        return 0;
    }
}
