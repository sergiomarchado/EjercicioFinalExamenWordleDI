package org.example.ejercicioexamenwordle;

/**
 * Clase modelo que representa una palabra secreta del juego junto con su pista.
 * Se utiliza para asociar una palabra de 5 letras con una descripción o pista
 * que ayuda al usuario a adivinarla.
 */
public class Palabra {

    // La palabra en sí (siempre en minúsculas para normalizar la comparación)
    private final String texto;

    // La pista asociada a la palabra (visible en la pantalla del juego)
    private final String pista;

    /**
     * Constructor que inicializa una palabra y su pista.
     *
     * @param texto Palabra de 5 letras que el usuario debe adivinar.
     * @param pista Texto descriptivo que sirve de ayuda.
     */
    public Palabra(String texto, String pista) {
        this.texto = texto.toLowerCase(); // Aseguramos que esté en minúsculas
        this.pista = pista;
    }

    /**
     * Devuelve la palabra secreta (en minúsculas).
     *
     * @return Palabra a adivinar.
     */
    public String getTexto() {
        return texto;
    }

    /**
     * Devuelve la pista asociada a la palabra.
     *
     * @return Texto descriptivo de ayuda.
     */
    public String getPista() {
        return pista;
    }
}
