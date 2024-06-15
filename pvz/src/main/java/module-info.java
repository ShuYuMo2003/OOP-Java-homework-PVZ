module homework {
    requires javafx.controls;
    requires javafx.fxml;

    opens homework to javafx.fxml;
    exports homework;
}
