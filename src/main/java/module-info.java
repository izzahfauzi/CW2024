module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.actors to javafx.fxml;
    opens com.example.demo.levels to javafx.fxml;
    opens com.example.demo.projectiles to javafx.fxml;
    opens com.example.demo.ui to javafx.fxml;
}