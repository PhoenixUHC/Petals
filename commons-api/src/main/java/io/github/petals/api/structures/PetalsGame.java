package io.github.petals.api.structures;

import java.util.Set;

/**
 * Petals game representation.
 */
public interface PetalsGame extends PetalsBase {
    /** @return whether the game is running. */
    boolean running();
    /** @return a set of players participating in the game. */
    Set<PetalsBase> players();
    /**
     * Adds the given player to the game.
     *
     * @param uniqueId The unique identifier of the new player.
     * @return The new player.
     */
    PetalsBase addPlayer(String uniqueId);
}

