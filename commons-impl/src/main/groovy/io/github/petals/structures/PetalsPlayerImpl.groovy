package io.github.petals.structures;

import redis.clients.jedis.JedisPooled;

class PetalsPlayerImpl extends PetalsBaseImpl {
    PetalsPlayerImpl(uniqueId, JedisPooled pooled) {
        super(uniqueId, pooled);
    }

    void delete() {
        def gameId = pooled.hget(uniqueId, "game");

        pooled.srem("$gameId:players", uniqueId);
        pooled.srem("players", uniqueId);
        super.delete();
    }
}

