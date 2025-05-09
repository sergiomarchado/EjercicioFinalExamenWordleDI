package org.example.ejercicioexamenwordle;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.*;

/**
 * Controlador de la escena de juego del Wordle.
 * Ahora con 5 filas completas para los 5 intentos, al estilo clásico del juego.
 */
public class JuegoController {

    // Elementos visuales
    @FXML private ImageView logoJuego;
    @FXML private Label pistaLabel;
    @FXML private Label intentosLabel;
    @FXML private Button validarButton;
    @FXML private Button reiniciarButton;
    @FXML private VBox rootJuegoVBox;

    // Fila activa actual (de 0 a 4)
    private int filaActual = 0;

    // Nombre del usuario (recibido desde Inicio)
    private String nombreUsuario = "Jugador";

    // Lógica del juego
    private final GestorPartida gestor = new GestorPartida();

    // Map para acceder fácilmente a los campos por fila
    private final Map<Integer, List<TextField>> filas = new HashMap<>();

    /**
     * Este método se llama automáticamente cuando se carga el FXML.
     * Inicializa las animaciones, la lógica del juego y los campos.
     */
    @FXML
    public void initialize() {
        // Cargar logo
        Image logo = new Image(Objects.requireNonNull(
                getClass().getResourceAsStream("/org/example/ejercicioexamenwordle/images/logowordle.png")));
        logoJuego.setImage(logo);

        // Animación del logo
        TranslateTransition jump = new TranslateTransition(Duration.seconds(1), logoJuego);
        jump.setFromY(0);
        jump.setToY(-12);
        jump.setCycleCount(TranslateTransition.INDEFINITE);
        jump.setAutoReverse(true);
        jump.play();

        // Agrupar campos de todas las filas (5x5)
        for (int fila = 1; fila <= 5; fila++) {
            List<TextField> campos = new ArrayList<>();
            for (int col = 1; col <= 5; col++) {
                TextField tf = (TextField) rootJuegoVBox.lookup("#f" + fila + "c" + col);
                tf.setTextFormatter(new TextFormatter<String>(change -> {
                    if (change.getControlNewText().length() > 1 || !change.getText().matches("[a-zA-Z]*"))
                        return null;
                    return change;
                }));
                tf.setDisable(fila != 1); // solo la primera fila habilitada al inicio
                final int next = col < 5 ? col : -1;
                if (next != -1) {
                    // Autoenfocar al siguiente campo
                    int finalFila = fila;
                    tf.textProperty().addListener((obs, oldVal, newVal) -> {
                        if (!newVal.isEmpty()) {
                            TextField nextField = (TextField) rootJuegoVBox.lookup("#f" + finalFila + "c" + (next + 1));
                            if (nextField != null) nextField.requestFocus();
                        }
                    });
                }
                campos.add(tf);
            }
            filas.put(fila - 1, campos);
        }

        // Mostrar pista
        pistaLabel.setText("Pista: " + gestor.getPista());

        // Botones
        validarButton.setOnAction(e -> validarIntento());
        reiniciarButton.setOnAction(e -> reiniciarPartida());

        // Mostrar intentos iniciales
        actualizarLabelIntentos();
    }

    /**
     * Recibe el nombre del usuario desde la pantalla de inicio.
     * @param nombre Nombre introducido.
     */
    public void setNombreUsuario(String nombre) {
        this.nombreUsuario = nombre;
    }

    /**
     * Valida el intento de la fila activa, aplica colores y avanza si corresponde.
     */
    private void validarIntento() {
        List<TextField> fila = filas.get(filaActual);

        String intento = fila.stream()
                .map(tf -> tf.getText().toLowerCase())
                .reduce("", String::concat);

        if (intento.length() != 5 || intento.contains(" ")) {
            mostrarAlerta("Debes completar las 5 letras.");
            return;
        }

        gestor.restarIntento();
        actualizarLabelIntentos();
        pintarResultado(fila, intento);

        if (gestor.haAcertado(intento)) {
            mostrarDialogoFin("¡Enhorabuena " + nombreUsuario + "!", "Has acertado la palabra.");
            return;
        }

        if (gestor.sinIntentos()) {
            mostrarDialogoFin("GAME OVER", "Se han agotado los intentos. La palabra era: " + gestor.getPalabraSecreta());
            return;
        }

        // Avanzar a la siguiente fila
        filaActual++;
        if (filaActual < 5) {
            filas.get(filaActual).forEach(tf -> tf.setDisable(false));
            filas.get(filaActual).get(0).requestFocus();
        }
    }

    /**
     * Aplica estilos de color a los campos según la comparación con la palabra secreta.
     */
    private void pintarResultado(List<TextField> fila, String intento) {
        String palabra = gestor.getPalabraSecreta();

        for (int i = 0; i < 5; i++) {
            String letra = intento.charAt(i) + "";
            TextField campo = fila.get(i);

            if (palabra.charAt(i) == intento.charAt(i)) {
                campo.setStyle("-fx-background-color: #66bb6a;"); // Verde
            } else if (palabra.contains(letra)) {
                campo.setStyle("-fx-background-color: #ffa726;"); // Naranja
            } else {
                campo.setStyle("-fx-background-color: #bdbdbd;"); // Gris
            }

            campo.setDisable(true); // Bloquear tras validar
        }
    }

    /**
     * Reinicia completamente el juego: palabra, intentos, campos.
     */
    private void reiniciarPartida() {
        gestor.seleccionarPalabraAleatoria();
        pistaLabel.setText("Pista: " + gestor.getPista());
        filaActual = 0;
        intentosLabel.setText("Intentos restantes: 5");

        filas.forEach((fila, campos) -> {
            for (int i = 0; i < campos.size(); i++) {
                TextField tf = campos.get(i);
                tf.clear();
                tf.setStyle("");
                tf.setDisable(fila != 0);
            }
        });

        filas.get(0).get(0).requestFocus();
    }

    /**
     * Muestra una alerta simple con mensaje informativo.
     */
    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra un diálogo final al terminar la partida.
     * @param titulo Título del cuadro.
     * @param mensaje Mensaje mostrado.
     */
    private void mostrarDialogoFin(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        ButtonType reiniciar = new ButtonType("Reiniciar");
        ButtonType salir = new ButtonType("Salir");
        alert.getButtonTypes().setAll(reiniciar, salir);

        alert.showAndWait().ifPresent(resp -> {
            if (resp == reiniciar) {
                reiniciarPartida();
            } else {
                System.exit(0);
            }
        });
    }

    /**
     * Actualiza el label de intentos restantes.
     */
    private void actualizarLabelIntentos() {
        intentosLabel.setText("Intentos restantes: " + gestor.getIntentosRestantes());
    }
}
