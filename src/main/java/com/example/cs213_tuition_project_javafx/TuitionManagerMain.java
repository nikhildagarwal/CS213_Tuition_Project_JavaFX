package com.example.cs213_tuition_project_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Driver class for JavaFX GUI
 * launches the JavaFX application
 * @author Nikhil Agarwal, Hyeon Oh
 */
public class TuitionManagerMain extends Application {

    /**
     * Creates instance of class (NOT USED)
     */
    public TuitionManagerMain(){}

    /**
     * Bridge method between java backend and JavaFX front end.
     * Specifies display Name for app and FXML file for structure of application.
     * @param stage Stage object that our app will be displayed on
     * @throws IOException Exception Handler for app Input/Output
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TuitionManagerMain.class.getResource("TuitionManagerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
        stage.setTitle("Project 3 - Tuition_Project_JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Driver method
     * Press run to launch JavaFX app
     * @param args command line inputs (NOT USED)
     */
    public static void main(String[] args) {
        launch();
    }
}