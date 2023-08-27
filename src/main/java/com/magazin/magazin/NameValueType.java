package com.magazin.magazin;

import java.sql.Types;
public class NameValueType {

    private String name = "";
    private String value = "";
    private int type = 0;

    public void setName(String s){
        name = s;
    }

    public void setValue(String s){
        value = s;
    }

    public void setTypes(int t){
        type = t;
    }

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }

    public int getTypes(){
        return type;
    }
}
