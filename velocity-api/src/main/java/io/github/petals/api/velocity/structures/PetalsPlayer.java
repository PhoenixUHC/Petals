package io.github.petals.api.velocity.structures;

import java.util.Optional;

import com.velocitypowered.api.proxy.Player;

import io.github.petals.api.structures.PetalsBase;

/**
 * A Petals player representation in a Velocity proxy.
 */
public interface PetalsPlayer extends PetalsBase {
    /** @return a Velocity player object for this player. */
    Optional<Player> player();
    /** @return the game associated with this player. */
    PetalsGame game();
}


