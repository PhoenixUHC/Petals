package io.github.petals.structures;

import io.github.petals.api.structures.*;

import redis.clients.jedis.JedisPooled;

class PetalsBaseImpl implements PetalsBase {
    private String uniqueId;
    protected JedisPooled pooled;

    PetalsBaseImpl(String uniqueId, JedisPooled pooled) {
        this.uniqueId = uniqueId;
        this.pooled = pooled;
    }

    String uniqueId() {
        return this.uniqueId;
    }

    boolean exists() {
        return this.pooled.hexists(this.uniqueId);
    }

    void delete() {
        this.pooled.del(this.uniqueId);
    }
}

