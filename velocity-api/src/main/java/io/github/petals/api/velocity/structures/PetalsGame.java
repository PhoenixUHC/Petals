package io.github.petals.api.velocity.structures;

import java.util.Optional;
import java.util.Set;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import io.github.petals.api.structures.PetalsBase;

/**
 * A Petals game representation in a Velocity proxy.
 */
public interface PetalsGame extends PetalsBase {
    /** @return a set of players participating in the game. */
    Set<PetalsPlayer> players();
    /**
     * Adds the given player to the game.
     *
     * @param uniqueId The unique identifier of the new player.
     * @return The new player.
     */
    PetalsPlayer addPlayer(String uniqueId);
    /**
     * @return the server associated with this game.
     */
    Optional<RegisteredServer> server();
}


