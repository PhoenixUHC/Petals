package io.github.petals.api.structures;

import java.util.Set;

/**
 * Petals game representation.
 */
public interface PetalsGame extends PetalsBase {
    /** @return amount of millis elapsed since the game started. */
    long time();
    /** @return whether the game is running. */
    boolean running();
}

