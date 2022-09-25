package io.github.petals.bukkit.structures;

import groovy.transform.CompileStatic;

import redis.clients.jedis.JedisPooled;

@CompileStatic
class PetalsGameImpl extends io.github.petals.structures.PetalsGameImpl implements io.github.petals.api.bukkit.structures.PetalsGame {
    PetalsGameImpl(String uniqueId, JedisPooled pooled) {
        super(uniqueId, pooled);
    }

    Set<PetalsPlayerImpl> players() {
        new HashSet(
            pooled.smembers("${this.uniqueId()}:players").collect { new PetalsPlayerImpl(it, pooled) }
        );
    }
}

