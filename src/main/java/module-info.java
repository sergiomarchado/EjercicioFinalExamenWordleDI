module org.example.ejercicioexamenwordle {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;

    opens org.example.ejercicioexamenwordle to javafx.fxml;
    exports org.example.ejercicioexamenwordle;
}