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

/**
 * Controlador de la pantalla inicial del juego Wordle.
 * Gestiona la entrada del nombre del usuario y las animaciones visuales
 * como el logo saltando y el fondo en degradado animado.
 */
public class InicioController {

    // Campo de texto donde el usuario introduce su nombre
    @FXML
    private TextField nombreUsuarioField;

    // Botón para comenzar la partida
    @FXML
    private Button botonComenzar;

    // Logo de Wordle en formato imagen
    @FXML
    private ImageView logoImage;

    // Contenedor principal para aplicar la animación de fondo
    @FXML
    private VBox rootVBox;

    /**
     * Metodo que se ejecuta automáticamente al cargar el FXML.
     * Inicializa el logo, el fondo animado y asigna los eventos a los controles.
     */
    @FXML
    public void initialize() {
        // Iniciar animaciones de fondo y logo
        animateBackground();
        animateLogo();

        // Cargar el logo desde el recurso correspondiente
        Image logo = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/org/example/ejercicioexamenwordle/images/logowordle.png")
        ));
        logoImage.setImage(logo);

        // Asignar funcionalidad al botón de comenzar
        botonComenzar.setOnAction(e -> comenzarPartida());
    }

    /**
     * Metodo que se ejecuta cuando el usuario pulsa "Comenzar Partida".
     * Valida que haya introducido su nombre y cambia de escena.
     */
    private void comenzarPartida() {
        String nombre = nombreUsuarioField.getText().trim();

        // Validación del campo obligatorio
        if (nombre.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campo obligatorio");
            alert.setHeaderText(null);
            alert.setContentText("Debes introducir tu nombre para comenzar.");
            alert.showAndWait();
            return;
        }

        try {
            // Cambia a la escena del juego y le pasa el nombre del usuario
            Main.changeToJuegoScene("/org/example/ejercicioexamenwordle/juego.fxml", nombre);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Aplica una animación de "saltito" al logo, en bucle.
     */
    private void animateLogo() {
        TranslateTransition jump = new TranslateTransition(Duration.seconds(1), logoImage);
        jump.setFromY(0);
        jump.setToY(-15);
        jump.setCycleCount(Animation.INDEFINITE);
        jump.setAutoReverse(true);
        jump.play();
    }

    /**
     * Crea un fondo animado que cambia entre degradados suaves (lila, azul, verde, rosa).
     * Se repite de forma infinita para dar dinamismo a la pantalla inicial.
     */
    private void animateBackground() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(rootVBox.styleProperty(),
                                "-fx-background-color: linear-gradient(to bottom, #b39ddb, #90caf9);",
                                Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(6),
                        new KeyValue(rootVBox.styleProperty(),
                                "-fx-background-color: linear-gradient(to bottom, #90caf9, #a5d6a7);",
                                Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(12),
                        new KeyValue(rootVBox.styleProperty(),
                                "-fx-background-color: linear-gradient(to bottom, #a5d6a7, #ce93d8);",
                                Interpolator.EASE_BOTH)
                ),
                new KeyFrame(Duration.seconds(18),
                        new KeyValue(rootVBox.styleProperty(),
                                "-fx-background-color: linear-gradient(to bottom, #ce93d8, #b39ddb);",
                                Interpolator.EASE_BOTH)
                )
        );

        timeline.setCycleCount(Animation.INDEFINITE); // Repetir infinitamente
        timeline.play();
    }
}
