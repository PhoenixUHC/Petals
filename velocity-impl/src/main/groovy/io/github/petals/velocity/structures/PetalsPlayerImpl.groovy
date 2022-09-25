package io.github.petals.velocity.structures;

import groovy.transform.CompileStatic
import io.github.petals.api.velocity.structures.PetalsGame;

import static groovy.transform.TypeCheckingMode.*;

import com.velocitypowered.api.proxy.Player;

import io.github.petals.velocity.PetalsPluginImpl;

@CompileStatic
class PetalsPlayerImpl extends io.github.petals.structures.PetalsPlayerImpl implements io.github.petals.api.velocity.structures.PetalsPlayer {
    private final PetalsPluginImpl plugin;

    PetalsPlayerImpl(String uniqueId, PetalsPluginImpl plugin) {
        super(uniqueId, plugin.pooled());
        this.plugin = plugin;
    }

    Optional<Player> player() {
        this.plugin.proxy().getPlayer(UUID.fromString(this.uniqueId()));
    }

    @Override
    @CompileStatic(SKIP)
    PetalsGame game() {
        return this.plugin.game(this.game);
    }
}

