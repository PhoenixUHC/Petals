package io.github.petals.api.velocity;

import java.util.Optional;
import java.util.Set;

import com.velocitypowered.api.proxy.server.RegisteredServer;

import io.github.petals.api.velocity.structures.PetalsGame;
import io.github.petals.api.velocity.structures.PetalsPlayer;

/**
 * The Petals Velocity plugin.
 */
public interface PetalsPlugin {
    /** @return each game currently stored in the database. */
    Set<PetalsGame> games();
    /**
     * @param uniqueId the unique ID to search.
     * @return the game associated with the given ID.
     */
    Optional<PetalsGame> game(String uniqueId);
    /**
     * Creates a Petals game and returns it.
     *
     * @param server the server to create the game for.
     * @return the new game.
     */
    PetalsGame createGame(RegisteredServer server);
    /**
     * @param uniqueId The unique ID to search.
     * @return the player associated with the given uniqueId.
     */
    Optional<PetalsPlayer> player(String uniqueId);
}

