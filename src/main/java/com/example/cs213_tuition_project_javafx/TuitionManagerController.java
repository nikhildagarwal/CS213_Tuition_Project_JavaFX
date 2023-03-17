package com.example.cs213_tuition_project_javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TuitionManagerController {
    @FXML
    private Label firstTabText;

    @FXML
    public void onAddButtonClick(){
        firstTabText.setText("Hi");
    }

    @FXML
    public void removeFirstTabText(){
        firstTabText.setText("");
    }



}