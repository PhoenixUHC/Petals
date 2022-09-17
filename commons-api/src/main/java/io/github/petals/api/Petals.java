package io.github.petals.api;

import java.util.Set;

import io.github.petals.api.structures.PetalsGame;

/**
 * The Petals plugin API.
 */
public interface Petals {
    /** @return a set of games registered on the database. */
    Set<PetalsGame> games();
    /** @return a set of games registered on the database that match with the given class. */
    <T extends PetalsGame> Set<T> games(Class<T> clazz);
}

