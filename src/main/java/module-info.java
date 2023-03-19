module com.example.project3fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires junit;


    opens com.example.project3fx to javafx.fxml;
    exports com.example.project3fx;
}