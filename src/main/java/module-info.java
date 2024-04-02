module com.example.java2048fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.lyus.java2048fx to javafx.fxml;
    exports me.lyus.java2048fx;
    exports model;
    opens model to javafx.fxml;
}