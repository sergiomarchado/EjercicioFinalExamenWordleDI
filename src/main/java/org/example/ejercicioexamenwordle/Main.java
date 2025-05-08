package org.example.ejercicioexamenwordle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;

        // Ruta del FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/ejercicioexamenwordle/inicio.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Wordle FX");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Permite cambiar de escena desde cualquier controlador.
     * @param fxmlPath ruta relativa al resource root, por ejemplo: "/org/example/ejercicioexamenwordle/juego.fxml"
     */
    public static void changeScene(String fxmlPath) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxmlPath));
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}