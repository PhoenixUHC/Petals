package io.github.petals.api.velocity;

import java.util.Set;

import com.velocitypowered.api.proxy.server.RegisteredServer;

import io.github.petals.api.velocity.structures.PetalsGame;

/**
 * The Petals Velocity plugin.
 */
public interface PetalsPlugin {
    /** @return each game currently stored in the database. */
    Set<PetalsGame> games();
    /**
     * Creates a Petals game and returns it.
     *
     * @param server the server to create the game for.
     * @return the new game.
     */
    PetalsGame createGame(RegisteredServer server);
}

