module ui {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.base;
    requires transitive javafx.graphics;
    requires javafx.media;

    opens ui to javafx.fxml;
    exports ui;
}
