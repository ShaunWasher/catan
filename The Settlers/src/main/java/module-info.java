module com.example.thesettlers {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    //requires com.almasb.fxgl.all;
    requires com.opencsv;

    opens com.example.thesettlers to javafx.fxml;
    exports com.example.thesettlers;
    exports com.example.thesettlers.enums;
    opens com.example.thesettlers.enums to javafx.fxml;
}