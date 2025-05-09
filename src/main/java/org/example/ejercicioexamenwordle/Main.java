package org.example.ejercicioexamenwordle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal que lanza la aplicación JavaFX del juego Wordle.
 * Controla la carga inicial del primer FXML (pantalla de inicio)
 * y permite cambiar de escena reutilizando el mismo Stage.
 */
public class Main extends Application {

    // Referencia estática al Stage principal (único en toda la app)
    private static Stage primaryStage;

    /**
     * Metodo principal de arranque de JavaFX.
     * Se ejecuta automáticamente al lanzar la aplicación.
     *
     * @param stage El escenario principal proporcionado por JavaFX.
     * @throws Exception si ocurre un error al cargar el FXML.
     */
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        // Cargamos la pantalla de inicio desde el archivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/ejercicioexamenwordle/inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Wordle FX");
        stage.setScene(scene);
        stage.show(); // Mostramos el escenario
    }

    /**
     * Metodo auxiliar para cambiar la escena activa del Stage principal,
     * manteniendo la misma ventana sin abrir una nueva.
     *
     * @param fxmlPath Ruta del archivo FXML de la nueva escena (completa desde /resources).
     * @throws Exception si ocurre un error al cargar el FXML.
     */
    public static void changeScene(String fxmlPath) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
    }

    /**
     * Metodo para cambiar a la escena del juego y pasar el nombre del usuario al controlador.
     *
     * @param fxmlPath      Ruta del FXML del juego.
     * @param nombreUsuario Nombre del usuario introducido en la pantalla de inicio.
     * @throws Exception si ocurre un error al cargar la escena o acceder al controlador.
     */
    public static void changeToJuegoScene(String fxmlPath, String nombreUsuario) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlPath));
        Scene scene = new Scene(loader.load());

        // Inyectamos el nombre de usuario en el controlador del juego
        JuegoController controller = loader.getController();
        controller.setNombreUsuario(nombreUsuario);

        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(); // Lanza la aplicación JavaFX
    }
}
