package io.github.petals.structures;

import io.github.petals.api.structures.*;

import redis.clients.jedis.JedisPooled;

class PetalsBaseImpl implements PetalsBase {
    private String uniqueId;
    protected JedisPooled pooled;

    PetalsBaseImpl(uniqueId, pooled) {
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

    def getProperty(String name) {
        if (name.equals("uniqueId")) return this.uniqueId;
        if (name.equals("pooled")) return this.pooled;

        String value = this.pooled.hget(this.uniqueId, name);
        if (value.equals("true")) return true;
        if (value.equals("false")) return false;

        if (value.isNumber()) {
            if (value.contains(".")) return value.toDouble();
            else return value.toLong();
        }
        return value;
    }

    void setProperty(String name, value) {
        if (name.equals("uniqueId") || name.equals("pooled")) return;

        this.pooled.hset(this.uniqueId, name, String.valueOf(value));
    }
}

