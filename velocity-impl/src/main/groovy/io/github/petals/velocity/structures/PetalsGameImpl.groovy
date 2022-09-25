package io.github.petals.velocity.structures

import com.velocitypowered.api.proxy.server.RegisteredServer;
import groovy.transform.CompileStatic;
import static groovy.transform.TypeCheckingMode.*;

import io.github.petals.velocity.PetalsPluginImpl;

@CompileStatic
class PetalsGameImpl extends io.github.petals.structures.PetalsGameImpl implements io.github.petals.api.velocity.structures.PetalsGame {
    private final PetalsPluginImpl plugin;

    PetalsGameImpl(String uniqueId, PetalsPluginImpl plugin) {
        super(uniqueId, plugin.pooled());
        this.plugin = plugin;
    }

    Set<PetalsPlayerImpl> players() {
        new HashSet(this.plugin.pooled().smembers(String.format("%s:players", this.uniqueId())).collect { id -> new PetalsPlayerImpl(id, this.plugin) })
    }

    PetalsPlayerImpl addPlayer(String uniqueId) {
        pooled.sadd("${this.uniqueId()}:players", uniqueId);

        pooled.hset(uniqueId, "game", this.uniqueId());

        return new PetalsPlayerImpl(uniqueId, this.plugin);
    }

    @CompileStatic(SKIP)
    Optional<RegisteredServer> server() {
        return this.plugin.proxy().getServer(this.server);
    }
}

