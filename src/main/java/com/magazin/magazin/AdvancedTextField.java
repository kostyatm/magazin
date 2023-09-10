package com.magazin.magazin;

import javafx.scene.control.TextField;
public class AdvancedTextField extends TextField{

    TextField textField;
    String lableText;
    String idText;
    String valueText;
    int lengthLable = 0;
    int lengthValue = 0;
    boolean visible = true;
    public AdvancedTextField(String lable, String id, String value){
        textField = new TextField();
        lableText = lable;
        idText = id;
        valueText = value;

        lengthLable = lable.length() * 7;
        lengthValue = value.length() * 7;

        textField.setText(valueText);
        textField.setMinWidth(250);

        if (lable.equalsIgnoreCase("_id") == true) {
            visible = false;
        }
    }

    public TextField getField() {
        return textField;
    }

    public int getLengthLable(){
        return lengthLable;
    }

    public int getLengthValue(){
        return lengthValue;
    }

    public String getLableText(){
        return lableText;
    }

    public boolean getVisible(){
        return visible;
    }
}
