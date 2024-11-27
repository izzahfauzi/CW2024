module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.actors to javafx.fxml;
    opens levels to javafx.fxml;
    opens projectiles to javafx.fxml;
    opens ui to javafx.fxml;
}