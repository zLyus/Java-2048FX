module com.example.java2048fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    exports model;
    opens model to javafx.fxml;
    exports view;
    opens view to javafx.fxml;
}