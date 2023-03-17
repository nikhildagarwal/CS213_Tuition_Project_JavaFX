module com.example.cs213_tuition_project_javafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cs213_tuition_project_javafx to javafx.fxml;
    exports com.example.cs213_tuition_project_javafx;
}