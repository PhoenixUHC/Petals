package io.github.petals.api.bukkit.structures;

import java.util.Set;

import io.github.petals.api.structures.PetalsBase;

/**
 * A Petals game representation in a Bukkit server.
 */
public interface PetalsGame extends PetalsBase {
    /** @return whether the game is running. */
    boolean running();
    /** @return a set of players participating in the game. */
    Set<PetalsPlayer> players();
    /**
     * Adds the given player to the game.
     *
     * @param uniqueId The unique identifier of the new player.
     * @return The new player.
     */
    PetalsPlayer addPlayer(String uniqueId);
}


