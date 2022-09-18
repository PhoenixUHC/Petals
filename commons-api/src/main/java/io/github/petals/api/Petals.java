package io.github.petals.api;

import java.util.Set;

import io.github.petals.api.structures.PetalsGame;

/**
 * The Petals plugin API.
 */
public interface Petals {
    /** @return a set of games registered on the database. */
    Set<PetalsGame> games();

    /**
     * Creates a Petals game from the given type and returns it.
     *
     * @return the created game.
     */
    PetalsGame createGame();
}

