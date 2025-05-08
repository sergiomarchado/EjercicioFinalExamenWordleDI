package org.example.ejercicioexamenwordle;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.Objects;

public class InicioController {

    @FXML
    private TextField nombreUsuarioField;

    @FXML
    private Button botonComenzar;

    @FXML
    private ImageView logoImage;

    @FXML
    private VBox rootVBox; // Necesario para animar el fondo

    @FXML
    public void initialize() {
        // Animar el fondo del VBox
        animateBackground();

        // Animar el logo (saltito)
        animateLogo();

        // Cargar imagen del logo
        Image logo = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/org/example/ejercicioexamenwordle/images/logowordle.png")
        ));
        logoImage.setImage(logo);
    }

    @FXML
    public void comenzarPartida() {
        String nombre = nombreUsuarioField.getText().trim();

        if (nombre.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campo obligatorio");
            alert.setHeaderText(null);
            alert.setContentText("Debes introducir tu nombre para comenzar.");
            alert.showAndWait();
            return;
        }

        try {
            Main.changeScene("/org/example/ejercicioexamenwordle/juego.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void animateLogo() {
        TranslateTransition jump = new TranslateTransition(Duration.seconds(1), logoImage);
        jump.setFromY(0);
        jump.setToY(-15);
        jump.setCycleCount(Animation.INDEFINITE);
        jump.setAutoReverse(true);
        jump.play();
    }

    private void animateBackground() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(rootVBox.styleProperty(), "-fx-background-color: linear-gradient(to bottom, #b39ddb, #90caf9);", Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(7),
                        new KeyValue(rootVBox.styleProperty(), "-fx-background-color: linear-gradient(to bottom, #90caf9, #a5d6a7);", Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(14),
                        new KeyValue(rootVBox.styleProperty(), "-fx-background-color: linear-gradient(to bottom, #a5d6a7, #b39ddb);", Interpolator.EASE_BOTH)
                )
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
