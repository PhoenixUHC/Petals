package io.github.petals.api.bukkit.structures;

import java.util.Optional;

import org.bukkit.entity.Player;

import io.github.petals.api.structures.PetalsBase;

/**
 * A Petals player representation in a Bukkit server.
 */
public interface PetalsPlayer extends PetalsBase {
    /** @return a Bukkit player object for this player. */
    Optional<Player> player();
}

