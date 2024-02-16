module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;


    opens com.example.jewelryworkshop to javafx.fxml;
    exports com.example.jewelryworkshop;
}