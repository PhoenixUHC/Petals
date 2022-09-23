package io.github.petals.structures;

import groovy.transform.CompileStatic;
import static groovy.transform.TypeCheckingMode.*;

import io.github.petals.structures.PetalsGameImpl

import io.github.petals.api.structures.*;

import redis.clients.jedis.JedisPooled;

@CompileStatic
class PetalsGameImpl extends PetalsBaseImpl implements PetalsGame {
    static Set<PetalsGameImpl> games(JedisPooled pooled) {
        new HashSet(
            pooled.smembers("games").collect { new PetalsGameImpl(it, pooled) }
        )
    }

    @CompileStatic(SKIP)
    static PetalsGameImpl create(JedisPooled pooled) {
        def uniqueId = UUID.randomUUID().toString();

        def game = new PetalsGameImpl(uniqueId, pooled);
        game.start = -1

        pooled.sadd("games", uniqueId);

        return game;
    }

    PetalsGameImpl(String uniqueId, JedisPooled pooled) {
        super(uniqueId, pooled);
    }

    @CompileStatic(SKIP)
    long time() {
        System.currentTimeMillis() - this.start;
    }

    @CompileStatic(SKIP)
    boolean running() {
        this.start > -1;
    }

    void delete() {
        players().each { it.delete() } // Remove players
        pooled.srem("games", this.uniqueId());

        super.delete();
    }

    Set<PetalsPlayerImpl> players() {
        new HashSet(
            pooled.smembers("${this.uniqueId()}:players").collect { new PetalsPlayerImpl(uniqueId, this.pooled) }
        );
    }

    PetalsPlayerImpl addPlayer(String uniqueId) {
        pooled.sadd("${this.uniqueId()}:players", uniqueId);
        pooled.sadd("players", uniqueId);

        pooled.hset(uniqueId, "game", this.uniqueId());

        return new PetalsPlayerImpl(uniqueId, pooled);
    }
}

