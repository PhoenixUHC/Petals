package io.github.petals.api.structures;

/**
 * Petals game representation.
 */
public interface PetalsGame {
    /** @return time in millis that ellapsed since the game started. */
    long time();
    /** @return whether the game is running. */
    boolean running();
}

