package io.github.petals.structures;

import groovy.transform.CompileStatic;

import redis.clients.jedis.JedisPooled;

@CompileStatic
class PetalsPlayerImpl extends PetalsBaseImpl {
    PetalsPlayerImpl(String uniqueId, JedisPooled pooled) {
        super(uniqueId, pooled);
    }

    void delete() {
        def gameId = pooled.hget(this.uniqueId(), "game");

        pooled.srem("$gameId:players", this.uniqueId());
        pooled.srem("players", this.uniqueId());
        super.delete();
    }
}

