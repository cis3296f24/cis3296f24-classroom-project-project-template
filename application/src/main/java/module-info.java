module slaythepasswordfx {
    requires javafx.controls;
    requires javafx.fxml;

    opens slaythepasswordfx to javafx.fxml;
    exports slaythepasswordfx;
}
