package org.example.ejercicioexamenwordle;

import java.util.List;
import java.util.Random;

/**
 * Clase que gestiona el estado de la partida de Wordle.
 * Contiene la lista de palabras posibles, la palabra secreta actual,
 * el control de intentos y la validación de respuestas del jugador.
 */
public class GestorPartida {

    // Lista fija de 10 palabras válidas (todas de 5 letras) con sus respectivas pistas
    private final List<Palabra> palabras = List.of(
            new Palabra("perro", "Animal doméstico"),
            new Palabra("nieve", "Cae en invierno"),
            new Palabra("manos", "Parte del cuerpo"),
            new Palabra("ratón", "Accesorio de ordenador"),
            new Palabra("silla", "Sirve para sentarse"),
            new Palabra("flaco", "Lo opuesto a gordo"),
            new Palabra("verde", "Color de muchas plantas"),
            new Palabra("lente", "Cristal de aumento"),
            new Palabra("dolar", "Moneda de EE.UU."),
            new Palabra("plomo", "Metal pesado")
    );

    // Palabra seleccionada aleatoriamente para la partida actual
    private Palabra palabraSeleccionada;

    // Número de intentos que le quedan al jugador (máximo 5)
    private int intentosRestantes = 5;

    /**
     * Constructor de la clase.
     * Al instanciarla, selecciona automáticamente una palabra aleatoria.
     */
    public GestorPartida() {
        seleccionarPalabraAleatoria();
    }

    /**
     * Selecciona una palabra aleatoria de la lista y reinicia los intentos a 5.
     */
    public void seleccionarPalabraAleatoria() {
        palabraSeleccionada = palabras.get(new Random().nextInt(palabras.size()));
        intentosRestantes = 5;
    }

    /**
     * Devuelve la pista asociada a la palabra secreta.
     *
     * @return Pista en formato String.
     */
    public String getPista() {
        return palabraSeleccionada.getPista();
    }

    /**
     * Devuelve la palabra secreta actual.
     *
     * @return Palabra a adivinar (en minúsculas).
     */
    public String getPalabraSecreta() {
        return palabraSeleccionada.getTexto();
    }

    /**
     * Devuelve cuántos intentos le quedan al jugador.
     *
     * @return Número de intentos restantes.
     */
    public int getIntentosRestantes() {
        return intentosRestantes;
    }

    /**
     * Resta uno a los intentos disponibles. Se debe llamar tras cada intento fallido.
     */
    public void restarIntento() {
        intentosRestantes--;
    }

    /**
     * Comprueba si el intento del jugador coincide exactamente con la palabra secreta.
     *
     * @param intento Palabra introducida por el usuario.
     * @return true si ha acertado la palabra completa, false si no.
     */
    public boolean haAcertado(String intento) {
        return palabraSeleccionada.getTexto().equalsIgnoreCase(intento);
    }

    /**
     * Comprueba si se han agotado todos los intentos posibles.
     *
     * @return true si no quedan intentos, false si aún se puede seguir jugando.
     */
    public boolean sinIntentos() {
        return intentosRestantes <= 0;
    }
}
