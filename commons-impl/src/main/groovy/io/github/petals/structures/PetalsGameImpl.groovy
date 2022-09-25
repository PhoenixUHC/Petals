package io.github.petals.structures;

import groovy.transform.CompileStatic;

import io.github.petals.api.structures.*;

import redis.clients.jedis.JedisPooled;

@CompileStatic
class PetalsGameImpl extends PetalsBaseImpl implements PetalsGame {
    static Set<PetalsGameImpl> games(JedisPooled pooled) {
        new HashSet(
            pooled.smembers("games").collect { new PetalsGameImpl(it, pooled) }
        )
    }

    PetalsGameImpl(String uniqueId, JedisPooled pooled) {
        super(uniqueId, pooled);
    }

    long time() {
        System.currentTimeMillis() - Long.parseLong(this.pooled.hget(this.uniqueId(), "start"));
    }

    boolean running() {
        Long.parseLong(this.pooled.hget(this.uniqueId(), "start")) > -1;
    }

    void delete() {
        players().each { it.delete() } // Remove players
        pooled.srem("games", this.uniqueId());

        super.delete();
    }

    Set<PetalsPlayerImpl> players() {
        new HashSet(
            pooled.smembers("${this.uniqueId()}:players").collect { new PetalsPlayerImpl(uniqueId(), this.pooled) }
        );
    }
}

