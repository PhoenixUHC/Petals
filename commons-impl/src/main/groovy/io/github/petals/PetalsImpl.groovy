package io.github.petals;

import io.github.petals.api.Petals;
import io.github.petals.structures.*;

import redis.clients.jedis.JedisPooled;

class PetalsImpl implements Petals {
    JedisPooled pooled;

    PetalsImpl(pooled) {
        this.pooled = pooled;
    }

    Set<PetalsGameImpl> games() {
        return this.pooled
            .smembers("games")
            .collect { new PetalsGameImpl(it, this.pooled) }
    }

    PetalsGameImpl createGame() {
        def uniqueId = UUID.randomUUID().toString();

        def game = new PetalsGameImpl(uniqueId, this.pooled);
        game.start = -1

        this.pooled.sadd("games", uniqueId);

        return game;
    }
}

