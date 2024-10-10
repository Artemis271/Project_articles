module com.example.my_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.my_project to javafx.fxml;
    exports com.example.my_project;
    exports com.example.my_project.controllers;
    opens com.example.my_project.controllers to javafx.fxml;
    exports com.example.my_project.models;
    opens com.example.my_project.models to javafx.fxml;
}