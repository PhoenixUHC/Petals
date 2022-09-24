package io.github.petals.velocity.structures;

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
        super.addPlayer(uniqueId);
        new PetalsPlayerImpl(uniqueId, this.plugin);
    }
}
