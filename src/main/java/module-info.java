module pattki.firstscreen {
    requires javafx.controls;
    requires javafx.fxml;


    opens pattki.firstscreen to javafx.fxml;
    opens pattki.firstscreen.Model to javafx.base;
    exports pattki.firstscreen;
}