package com.example.cs213_tuition_project_javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TuitionManagerMain {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}