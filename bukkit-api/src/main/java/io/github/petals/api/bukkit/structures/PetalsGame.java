package io.github.petals.api.bukkit.structures;

import java.util.Set;

/**
 * A Petals game representation in a Bukkit server.
 */
public interface PetalsGame extends io.github.petals.api.structures.PetalsGame {
    /** @return a set of players participating in the game. */
    Set<PetalsPlayer> players();
}


