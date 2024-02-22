module com.example.java2048fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.java2048fx to javafx.fxml;
    exports com.example.java2048fx;
}