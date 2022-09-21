package io.github.petals.bukkit.structures;

import redis.clients.jedis.JedisPooled;

class PetalsGameImpl extends io.github.petals.structures.PetalsGameImpl implements io.github.petals.api.bukkit.structures.PetalsGame {
    PetalsGameImpl(uniqueId, JedisPooled pooled) {
        super(uniqueId, pooled);
    }

    Set<PetalsPlayerImpl> players() {
        new HashSet(
            pooled.smembers("$uniqueId:players").collect { new PetalsPlayerImpl(it, pooled) }
        );
    }

    PetalsPlayerImpl addPlayer(String uniqueId) {
        super.addPlayer(uniqueId);
        return new PetalsPlayerImpl(uniqueId, pooled);
    }
}

