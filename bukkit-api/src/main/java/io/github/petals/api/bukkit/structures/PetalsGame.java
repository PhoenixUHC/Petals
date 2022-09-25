package io.github.petals.api.bukkit.structures;

import java.util.Set;

import io.github.petals.api.structures.PetalsBase;

/**
 * A Petals game representation in a Bukkit server.
 */
public interface PetalsGame extends PetalsBase {
    /** @return a set of players participating in the game. */
    Set<PetalsPlayer> players();
}


